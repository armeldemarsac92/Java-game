package GameLogic;

import java.util.ArrayList;
import java.util.List;

import Exceptions.NoSuchCoordinateKeyException;

import java.awt.*;

public abstract class Unit implements GameObject {

    /*---------- Attributes ---------- */

    protected int damage;
    protected int capacity;
    protected int damageRate;
    protected static int counter = 0;
    protected int id;

    protected float range;

    protected Image attackSoundFilePath;
    protected Image deathSoundFilePath;
    protected Image characterSpriteImage;

    protected List<? extends Unit> unitsInRange;

    protected Coordinates coordinates;


    /*---------- Constructor ---------- */
    
    public Unit(int damage, int damageRate, int range, int capacity, Coordinates coordinates){
        List<Integer> unitCoordinates = new ArrayList<Integer>();
        try {
            unitCoordinates.add(coordinates.get("x"));
            unitCoordinates.add(coordinates.get("y"));
        } catch (NoSuchCoordinateKeyException e) {
            System.out.println(e.getMessage());
        }

        Unit.counter++;
        this.id = Unit.counter;
        this.damage = damage;
        this.damageRate = damageRate;
        this.capacity = capacity;
        this.coordinates = coordinates;
        GlobalUnits.add(this);
        GlobalUnitsCoordinates.add(this.getId(), unitCoordinates);
    }


    /*---------- Getters ---------- */

    public int getDamage(){
        return this.damage;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public int getDamageRate(){
        return this.damageRate;
    }

    public int getId(){
        return this.id;
    }

    public static int getUnitsCount(){
        return Unit.counter;
    }

    public float getRange(){
        return this.damage;
    }

    public Image getAttackSoundFilePath(){
        return this.attackSoundFilePath;
    }

    public Image getDeathSoundFilePath(){
        return this.deathSoundFilePath;
    }

    public Image getCharacterSpriteImage(){
        return this.characterSpriteImage;
    }

    public List<? extends Unit> getUnitsInRange(int range, List<? extends Unit> globalUnits){
        return this.unitsInRange;
    }

    public Coordinates getUnitCoordinates(){
        return this.coordinates;
    }


    /*---------- Setters ---------- */

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }


    /*---------- Methods ---------- */
    public void attackUnitsInRange(List<? extends Unit> unitsInRange){
        System.out.println(unitsInRange);
        System.out.println(this.getCapacity());
    }

    public Point getBlockCoordinates(int blockSize){
        try {
            return new Point(this.getUnitCoordinates().get("x") / blockSize, this.getUnitCoordinates().get("y") / blockSize);
        } catch (NoSuchCoordinateKeyException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void draw(Graphics graphics, int blockSize){
        Point blockCoordinates = getBlockCoordinates(blockSize);
        int width = 150; // The width of the tower image
        int height = 100; // The height of the tower image
        
        // Calculate the center for the x coordinate
        int drawX = (blockCoordinates.x * blockSize) + (blockSize / 2) - (width / 2);
        
        // Calculate the bottom for the y coordinate
        int drawY = (blockCoordinates.y * blockSize) + blockSize - height;
    
        // Draw the image such that its bottom is aligned with the bottom of the grid cell
        graphics.drawImage(this.characterSpriteImage, drawX, drawY, width, height, null);
    }
    

}
