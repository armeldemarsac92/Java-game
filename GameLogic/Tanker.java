package GameLogic;

import javax.swing.*;

import Exceptions.NoSuchCoordinateKeyException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Tanker extends Mob {
    private JLabel tankerLabel;
    private List<ImageIcon> animationFrames;
    private int currentFrame = 0;
    private Timer animationTimer;

    public Tanker(int speed, int hp, int damage, int damageRate, int range, int capacity, Coordinates coordinates, Container parentContainer) {
        super(hp, speed, damage, damageRate, range, capacity, coordinates);

        tankerLabel = new JLabel();
    
        try {
            tankerLabel.setBounds(coordinates.get("x"), coordinates.get("y"), 100, 100);
        } catch (NoSuchCoordinateKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Set initial position and size
        parentContainer.add(tankerLabel);
        parentContainer.setComponentZOrder(tankerLabel, 0);

        loadAnimationFrames();
        startAnimation();
    }

    private void loadAnimationFrames() {
        animationFrames = new ArrayList<>();
        try {
            for (int i = 1; i <= 7; i++) { // numberOfFrames should be the number of images in your sequence
                Image img = ImageIO.read(new File("assets/tanker/Tanker-" + i + ".png"));
                Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale image
                animationFrames.add(new ImageIcon(scaledImg));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JLabel getTankerLabel(){
        return this.tankerLabel;
    }

    private void startAnimation() {
        animationTimer = new Timer(50, e -> updateAnimation()); // Adjust the delay (100ms here) as needed
        animationTimer.start();
    }

    private void updateAnimation() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
        tankerLabel.setIcon(animationFrames.get(currentFrame));
    }

    public void cleanup() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        if (tankerLabel.getParent() != null) {
            tankerLabel.getParent().remove(tankerLabel);
        }
    }

    // Rest of your Tanker class...
    @Override
    public void move() {
        try {
            // Update the position of the JLabel
            Coordinates coords = getUnitCoordinates();
            coords.setXPos(coords.get("x") + getSpeed());
            tankerLabel.setLocation(coords.get("x"), coords.get("y"));
            System.out.println("Pos on x: " + coords.get("x")); // Debug
        } catch (NoSuchCoordinateKeyException e) {
            System.out.println("no coordinates");
            e.printStackTrace(); // Consider more meaningful exception handling
        }
    }
}



