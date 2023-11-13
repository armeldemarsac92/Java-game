package PixelMap;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.ScreenSleepEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;
import Exceptions.*;
import GameLogic.*;

public class GamePanel extends JPanel {
    private Timer waveTimer;
    private int waveInterval = 30000; // 30 seconds between waves
    private int currentWave;
    private int mobSpeed = 1;
    private int hordeSize = 4;

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
            // Handle error...
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
        int minTimer = 1000;
        int maxTimer = 2000;
        int randomTimer = minTimer + random.nextInt(maxTimer - minTimer + 1); 
    
        new Timer().schedule(new TimerTask() {
            private int count = 0;
    
            @Override
            public void run() {
                if (count < hordeSize) {
                    int minY = 550; // Minimum Y-coordinate
                    int maxY = 650; // Maximum Y-coordinate
                    int randomY = minY + random.nextInt(maxY - minY + 1); // Generate a random Y-coordinate within the range

                    new Barbarian(1, mobSpeed, 10, 1, 1, 1, new Coordinates(-10, randomY), GamePanel.this);
                    count++;
                } else {
                    this.cancel(); // Stop the timer once all mobs are spawned
                }
            }
        }, 0, randomTimer); // random delay between each mob spawn
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
