package PixelMap;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;
import GameLogic.*;

public class GamePanel extends JPanel {
    private Timer waveTimer;
    private int waveInterval = 30000; // 30 seconds between waves
    private int currentWave;
    private int mobSpeed = 1;
    private int hordeSize = 4;
    private int threashold = 10;
    private int maxTanker = 2;

    private JLabel backgroundLabel;

    public GamePanel(Dimension screenSize) {
        setPreferredSize(screenSize); // Set the panel size to the screen size
        setLayout(null); // Continue using null layout for absolute positioning

        // Initialize units and towers as before
        initializeUnits();
        initializeBackground(screenSize); // Pass screenSize to handle background scaling
    }

    private void initializeBackground(Dimension screenSize) {
        try {
            // Load the original image
            Image originalImage = ImageIO.read(new File("assets/background1.png"));
    
            // Scale the image to fit the screen size
            Image scaledImage = originalImage.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
    
            // Set the scaled image as the icon of the JLabel
            ImageIcon backgroundImageIcon = new ImageIcon(scaledImage);
            backgroundLabel = new JLabel(backgroundImageIcon);
            backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);
            add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeUnits() {
        waveTimer = new Timer();
        waveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                spawnWave();
            }
        }, 0, waveInterval);
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
    
            @Override
            public void run() {
                if (count < hordeSize) {
                    int minY = 500; // Minimum Y-coordinate
                    int maxY = 700; // Maximum Y-coordinate
                    int randomY = minY + random.nextInt(maxY - minY + 1); // Generate a random Y-coordinate within the range
                    int minChance = 0;
                    int maxChance = 10;
                    int tankerApparitionChance = random.nextInt(maxChance - minChance + 1);

                    new Barbarian(new Coordinates(-400, randomY), GamePanel.this);
                    count++;

                    if (tankerApparitionChance >= threashold && tankerCount <= maxTanker){
                        new Tanker(new Coordinates(-400, randomY), GamePanel.this);
                        count += 2;
                        tankerCount ++;
                    }
                    
                } else {
                    this.cancel(); // Stop the timer once all mobs are spawned
                }
            }
        }, 0, randomTimer); // random delay between each mob spawn
        threashold --;
        if (currentWave % 5 == 0){
            mobSpeed ++;
            maxTanker ++;
        }
    }

    public GamePanel getInstance(){
        return this;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tower Defense Game");
    
            // Get the screen size
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            GamePanel panel = new GamePanel(screenSize);
    
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize frame before making it visible
            frame.setVisible(true);
    
            // Debugging output
            System.out.println("Frame size: " + frame.getSize());
            System.out.println("Panel preferred size: " + panel.getPreferredSize());
        });
    }
    
}
