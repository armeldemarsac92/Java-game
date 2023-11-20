package PixelMap;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import GameLogic.*;

public class Game implements Runnable {

    private Timer waveTimer;
    private int waveInterval = 30000; // 30 seconds between waves
    private int currentWave;
    private int hordeSize = 2;
    private int threashold = 10;
    private int maxTanker = 2;
    private int mobSpeed = 1;
    public JFrame frame;
    private GamePanel gamePanel;
    private boolean running = true;
    private static boolean gameOver = false;
    private volatile boolean isPaused = false;
    private long remainingDelayForNextWave = 0;

    public Game(String username) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gamePanel = new GamePanel(screenSize, this);
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        UserInterface.intializeScoreSystem(gamePanel, screenSize);
        UserInterface.initializeCoinsSystem(gamePanel, screenSize);
        UserInterface.initializeUserName(gamePanel, screenSize, username);
        UserInterface.initializeUserHp(gamePanel, screenSize);
        UserInterface.setCoins(30);
    }

    
    public synchronized void pauseGame() {
        isPaused = true;
        if (waveTimer != null) {
            remainingDelayForNextWave = waveInterval - (System.currentTimeMillis() % waveInterval);
            waveTimer.cancel(); // Cancel the wave timer
        }
    }

    public synchronized void resumeGame() {
        isPaused = false;
        notify(); // Resume the game thread

        startWaveTimer(remainingDelayForNextWave); // Restart the wave timer with the remaining delay
    }

    

    private void startWaveTimer(long initialDelay) {
        waveTimer = new Timer();
        waveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                spawnWave();
            }
        }, initialDelay, waveInterval);
    }

    private void spawnWave() {
        currentWave++;
        hordeSize++;
        waveInterval += 10;
        System.out.println("Spawning wave: " + currentWave);
        Random random = new Random();
        int minTimer = 800;
        int maxTimer = 2000;
        int randomTimer = minTimer + random.nextInt(maxTimer - minTimer + 1);

        new Timer().schedule(new TimerTask() {
            private int count = 0;
            int tankerCount = 0;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int standardY = (int) (screenSize.getHeight() / 2) - 100;

            @Override
            public void run() {
                if (count < hordeSize) {
                    int minY = standardY - 50; // Minimum Y-coordinate
                    int maxY = standardY + 50; // Maximum Y-coordinate
                    int randomY = minY + random.nextInt(maxY - minY + 1); // Generate a random Y-coordinate within the
                                                                          // range
                    int minChance = 0;
                    int maxChance = 10;
                    int tankerApparitionChance = random.nextInt(maxChance - minChance + 1);

                    Barbarian barbarian = new Barbarian(new Coordinates(-400, randomY), gamePanel);
                    barbarian.setSpeed(mobSpeed);
                    count++;

                    if (tankerApparitionChance >= threashold && tankerCount <= maxTanker && count > 3){
                        new Tanker(new Coordinates(-400, randomY), gamePanel);
                        count += 2;
                        tankerCount++;
                    }

                } else {
                    this.cancel();
                }
            }
        }, 0, randomTimer); // random delay between each mob spawn
        threashold--;
        if (currentWave % 5 == 0) {
            mobSpeed++;
            maxTanker++;
        }
    }

    public void run() {
        final int TARGET_FPS = 120; // Target frames per second
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // Optimal time per frame in nanoseconds

        long lastLoopTime = System.nanoTime();
        startWaveTimer(remainingDelayForNextWave);
        while (running) {
            synchronized (this) {
                while (isPaused) {
                    try {
                        wait(); // Met le thread en attente si le jeu est en pause
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
    
            this.updateGame(); // Update the game state
            GlobalUnits.cleanup();



            try {
                long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
        }
    }

    private void updateGame() {
        for (AUnit unit : GlobalUnits.getGlobalUnits()) {
            if (unit instanceof AMob && unit.getUnitLabel() != null) {
                ((AMob) unit).move();
            }
    
            if (unit instanceof ATower) {
                unit.computeUnitsInRange();
                // Do not call attackUnitsInRange here as it's handled by the timer
                // Update game logic
                
                UserInterface.updateScoreLabel();
                UserInterface.updateCoinsLabel();
                UserInterface.updatePlayerHpLabel();
            }
        }
    }
    

    public static boolean isGameOver(){
        return Game.gameOver;
    }

    public static void setGameOver(boolean gameOver){
        Game.gameOver = gameOver;
    }
    
}