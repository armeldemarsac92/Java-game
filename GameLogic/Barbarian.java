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


    public Barbarian(Coordinates coordinates) {
        super(2, 50, 2, 1, 1, 1, 5, coordinates);
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

    @Override
    public <T> void attack(T unit) {
        Mob castedUnit = (Mob) unit;
        if(castedUnit.isAlive()){
            castedUnit.setHp(this.damage);
        }
        else{
            castedUnit.killInstance();
        }
    }

    

}
