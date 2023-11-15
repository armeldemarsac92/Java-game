package PixelMap;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import GameLogic.*;

public class Game implements Runnable {

    private JFrame frame;
    private GamePanel gamePanel;
    private boolean running = true;
    private static boolean gameOver = false;


    public Game() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gamePanel = new GamePanel(screenSize);
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);


        PointSystem.intializeScoreSystem(gamePanel, screenSize);
        PointSystem.initializeCoinsSystem(gamePanel, screenSize);
        PointSystem.setCoins(50);
    }

    public void run() {
        final int TARGET_FPS = 120; // Target frames per second
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // Optimal time per frame in nanoseconds
    
        long lastLoopTime = System.nanoTime();
        while (running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
    
            this.updateGame(); // Update the game state
            GlobalUnits.cleanup();
    
            // Sleep for the remaining frame time
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
        // Update game logic here
        for(AUnit unit : GlobalUnits.getGlobalUnits()){
            if(unit instanceof AMob && unit.getUnitLabel() != null ){
                ((AMob)unit).move(); // This will update the position of the JLabel in each unit
            }

            for(AUnit tower : GlobalUnits.getGlobalUnits()){
                if(tower instanceof ATower){
                    tower.computeUnitsInRange();
                    tower.attackUnitsInRange();
                }
            }
        }
        PointSystem.updateScoreLabel();
        PointSystem.updateCoinsLabel();
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.frame.setVisible(true);
            new Thread(game).start(); // Start the game loop in a new thread.
        });
    }

    public static boolean isGameOver(){
        return Game.gameOver;
    }

    public static void setGameOver(boolean gameOver){
        Game.gameOver = gameOver;
    }
}
