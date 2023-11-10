package GameLogic;
import java.util.List;

import javax.imageio.ImageIO;

import Exceptions.NoSuchCoordinateKeyException;

import java.util.ArrayList;
import java.awt.*;
import java.io.IOException;

public class Barbarian extends Mob {
    private List<Image> frames = new ArrayList<>();
    private int currentFrame = 0;


    public Barbarian(int speed, int hp, int damage, int damageRate, int range, int capacity, Coordinates coordinates) {
        super(hp, speed, damage, damageRate, range, capacity, coordinates);
        try {
            for (int i = 1; i <= 7; i++) {
                Image frame = ImageIO.read(getClass().getResource("/assets/barbarian/barbarian-" + i + ".png"));
                frames.add(frame);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAnimation() {
        currentFrame = (currentFrame + 1) % frames.size();
    }

    @Override
    public void draw(Graphics graphics, int blockSize){
        Point blockCoordinates = getBlockCoordinates(blockSize);
        int width = 200; // The width of the tower image
        int height = 200; // The height of the tower image
        
        // Calculate the center for the x coordinate
        int drawX = (blockCoordinates.x * blockSize);
        
        // Calculate the bottom for the y coordinate
        int drawY = (blockCoordinates.y * blockSize);

        Image currentFrameImage = frames.get(currentFrame);
    
        // Draw the image such that its bottom is aligned with the bottom of the grid cell
        graphics.drawImage(currentFrameImage, drawX, drawY, width, height, null);
    }

    @Override
    public void move(){
    try {
        this.getUnitCoordinates().setXPos(this.getUnitCoordinates().get("x") + this.getSpeed());
        System.out.println("Pos on x: " + this.getUnitCoordinates().get("x")); // Debug
        this.updateAnimation();
    } catch (NoSuchCoordinateKeyException e) {
        System.out.println(e.getMessage());
    }
    }

}
