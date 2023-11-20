package GameLogic;

import Exceptions.*;
import PixelMap.GamePanel;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public abstract class ATower extends AUnit {

    /*---------- Attributes ---------- */

    private int price;
    private int level = 0;
    private int maxLevel = 2;
    Timer damageTimer = new Timer();


    /*---------- Constructor ---------- */

    public ATower(int damage, int damageRate, int range, int capacity, Coordinates coordinates, GamePanel gamePanel){
        super(damage, damageRate, range, capacity, coordinates, gamePanel);
        try {
            this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl0.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.damageTimer=null;
        this.attackUnitsInRange();
        System.out.println("----------- Tower instantiated -----------");
        System.out.println("Id: " + this.getId());
        System.out.println("Damage: " + this.getDamage());
        System.out.println("Capacity: " + this.getCapacity());
        System.out.println("Damage rate: " + this.getDamageRate());
        System.out.println("Range: " + this.getRange());
        System.out.println("Coordinates: " + this.getUnitCoordinates().getCoordinates());
    }


    /*---------- Getters ---------- */

    public int getPrice(){
        return this.price;
    }

    public int getLevel(){
        return this.level;
    }


    /*---------- Setters ---------- */

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setRange(float range){
        this.range = range;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void setDamageRate(int damageRate){
        this.damageRate = damageRate;
    }   

    public void setCharacterSpriteFilePath(Image characterSpriteFilePath){
        this.characterSpriteImage = characterSpriteFilePath;
    }

    public void setAttackSoundFilePath(Image attackSoundFilePath){
        this.attackSoundFilePath = attackSoundFilePath;
    }

    public void setLevel(int level){
        this.level = level;
    }


    /*---------- Methods ---------- */
    
    public void upgrade() throws MaximumLevelReachedException {
        System.out.println(this.level);
        if (this.level >= this.maxLevel) {
            throw new MaximumLevelReachedException("Maximum level reached (" + this.level + ")");
        } else {
            this.setLevel(this.level + 1); // Upgrade level
            this.setDamage(this.getDamage() * 2); // Increase damage
            this.setDamageRate(this.getDamageRate() * 2); // Double damage rate
            this.setPrice(this.price * 2); // Double tower price
            this.setRange(400);
            PointSystem.spendCoins(this.price);
    
            // Update core file path based on the type of tower and its new level
            if (this instanceof IceTower) {
                this.sizeX = 400;
                this.setCoreFilePath("assets/ice_tower/level_" + this.level + "/Tower-");
            } else if (this instanceof ArcherTower) {
                this.setCoreFilePath("assets/archer_tower/level_" + this.level + "/Tower-");
            }
    
            // Reload animation frames with the new assets
            this.reloadAnimationFramesAsync();
        }
    }
    
    
    

    @Override 
    public void computeUnitsInRange(){
        // initialize empty list (serves as setter)
        List<AUnit> unitsInRangeTemp = new ArrayList <AUnit>();
        
        // For each unit in global list
        for(AUnit unit : GlobalUnits.getGlobalUnits()){
            if(unit instanceof AMob){
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

                    // System.out.println("Distance between " + this.getClass().getSimpleName() + " and " + unit.getClass().getSimpleName() + ": " + hypothenus);

                    // if the unit range >= distance between the two, push to temp list
                    if((double) this.range >= hypothenus && unit instanceof AMob){
                        unitsInRangeTemp.add((AMob) unit);
                        // System.out.println("Unit " + unit.getClass().getSimpleName() + " is therefore in range (" + this.range + " - " + hypothenus + ")");
                    }
                    else{
                        // System.out.println(unit.getClass().getSimpleName() + " not in range" + "(" + this.range + " - " + hypothenus + ")" );
                    }
                } catch(NoSuchCoordinateKeyException e){
                    e.printStackTrace();
                }
            }
        }
        
        // set the value
        this.setUnitsInRange(unitsInRangeTemp);
    }

    @Override
    public void attackUnitsInRange() {
        if (this.damageTimer == null) {
            this.damageTimer = new Timer();
            System.out.println("attack");
            this.damageTimer.scheduleAtFixedRate(new TimerTask() {
                private int currentIndex = 0;
    
                @Override
                public void run() {
                    List<AUnit> unitsInRange = getUnitsInRange();
                    if (unitsInRange != null && !unitsInRange.isEmpty()) {
                        currentIndex = currentIndex % unitsInRange.size(); // Ensure currentIndex is valid
                        AMob mob = (AMob) unitsInRange.get(currentIndex);
                        if (mob.isAlive()) {
                            attack(mob);
                        }
                        currentIndex = (currentIndex + 1) % unitsInRange.size();
                    }
                }
            }, 0, getDamageRate());
        }
    }
    

    
    


    @Override
    public <T> void attack(T unit) {
    AMob castedUnit = (AMob) unit;

    if (castedUnit.isAlive()) {
        castedUnit.setHp(this.getDamage());
        System.out.println(this.getClass().getSimpleName() + " inflicts " + this.getDamage() + " damage to " 
            + castedUnit.getClass().getSimpleName() + "(" + castedUnit.getHp() + " hp left)");
    } else {
        // castedUnit.killInstance();
    }
    }
}