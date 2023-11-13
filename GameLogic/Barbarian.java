package GameLogic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Barbarian extends Mob {

    public Barbarian(Coordinates coordinates) {
        super(50.0f, 50, 2, 1, 1, 1, 5, coordinates);
        try {
            for (int i = 1; i <= 7; i++) {
                Image frame = ImageIO.read(getClass().getResource("/assets/barbarian/barbarian-" + i + ".png"));
                frames.add(frame);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("----------- Barbarian instantiated -----------");
        System.out.println("Id: " + this.getId());
        System.out.println("Hp: " + this.getHp());
        System.out.println("Speed: " + this.getSpeed());
        System.out.println("Damage: " + this.getDamage());
        System.out.println("Capacity: " + this.getCapacity());
        System.out.println("Damage rate: " + this.getDamageRate());
        System.out.println("Range: " + this.getRange());
        System.out.println("Coordinates: " + this.getUnitCoordinates().getCoordinates());
    }

    @Override
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
}
