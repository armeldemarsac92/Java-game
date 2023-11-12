package GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

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
    protected ImageIcon mobSpriteImage;

    protected List<Unit> unitsInRange;

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

    public List<Unit> getUnitsInRange(){
        return this.unitsInRange;
    }

    public Coordinates getUnitCoordinates(){
        return this.coordinates;
    }


    /*---------- Setters ---------- */

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setUnitsInRange (List<Unit> unitsInRange){
        this.unitsInRange = unitsInRange;
    }


    /*---------- Methods ---------- */
    public void attackUnitsInRange(){
        if(this.unitsInRange.size() > 0){
            try {
                // delay the action
                TimeUnit.SECONDS.sleep(this.damageRate);
                // attack each unit in range if it has enough capacity
                for(int i = 0; i < this.unitsInRange.size(); i++){
                    Mob unitToAttack = (Mob) this.unitsInRange.get(i);
                    if(i <= this.capacity){
                        System.out.println(this.getClass().getSimpleName() + " inflicts " + this.getDamage() + " damage to " 
                        + unitToAttack.getClass().getSimpleName() + "(" + unitToAttack.getHp() + " hp left)");
                        if(unitToAttack.isAlive()){
                            unitToAttack.setHp(this.getDamage());
                        }
                    }
                }
            } catch(InterruptedException e){
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        else{
            System.out.println("No unit in range");
        }
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

    public void computeUnitsInRange(){
        // initialize empty list (serves as setter)
        List<Unit> unitsInRangeTemp = new ArrayList<Unit>();
        
        // For each unit in global list
        for(Unit unit : GlobalUnits.getGlobalUnits()){
            if(unit != this){
                try {
                    // get the absolute distance in x
                    int unitXPos = unit.getUnitCoordinates().get("x");
                    int xPos = this.getUnitCoordinates().get("x");
                    float distanceX = Math.abs(xPos - unitXPos);

                    // get the absolute distance in y
                    int unitYPos = unit.getUnitCoordinates().get("y");
                    int yPos = this.getUnitCoordinates().get("y");
                    float distanceY = Math.abs(yPos - unitYPos);

                    // get the hypothenus between the two
                    double hypothenus = Math.hypot(distanceX, distanceY);

                    System.out.println("Distance x between " + this.getClass().getSimpleName() + " and " + unit.getClass().getSimpleName() + ": " + distanceX);
                    System.out.println("Distance y between " + this.getClass().getSimpleName() + " and " + unit.getClass().getSimpleName() + ": " + distanceY);
                    System.out.println("Distance between " + this.getClass().getSimpleName() + " and " + unit.getClass().getSimpleName() + ": " + hypothenus);

                    // if the unit range >= distance between the two, push to temp list
                    if((double) this.range <= hypothenus){
                        unitsInRangeTemp.add(unit);
                    }
                } catch(NoSuchCoordinateKeyException e){
                    e.printStackTrace();
                }
            }
        }

        // set the value
        this.setUnitsInRange(unitsInRangeTemp);
    }
    

}
