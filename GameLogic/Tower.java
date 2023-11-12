package GameLogic;

import Exceptions.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Tower extends Unit {

    /*---------- Attributes ---------- */

    private int price;
    private int level = 0;

    /*---------- Constructor ---------- */
    public Tower(Coordinates coordinates){
        super(10, 1, 100, 1, coordinates);
        try {
            this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl0.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
    
    public void upgrade() throws MaximumLevelReachedException{
        System.out.println(this.level);
        if(this.level >= 2){
            throw new MaximumLevelReachedException("Maximim level reached " + "(" + this.level + ")");
        }
        else{
            this.setLevel(this.level + 1); //upgrade level
            this.setDamage(this.getDamage() * 2); // increase damage
            // this.setCharacterSpriteFilePath("Sprite level " + this.level); // change sprite
            // this.setAttackSoundFilePath("Attack level " + this.level); // change sound
            this.setDamageRate(this.getDamageRate() * 2); // increase damage rate
            try {
                this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl1.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
        }
    }

    @Override 
    public void computeUnitsInRange(){
        // initialize empty list (serves as setter)
        List<Unit> unitsInRangeTemp = new ArrayList<Unit>();
        
        // For each unit in global list
        for(Unit unit : GlobalUnits.getGlobalUnits()){
            if(unit instanceof Mob){
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
                        unitsInRangeTemp.add((Mob) unit);
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
    public void attackUnitsInRange(){
        List<Mob> attackableUnits = new ArrayList<Mob>();

        for(Unit unit : this.unitsInRange){
            if(unit instanceof Mob){
                attackableUnits.add((Mob) unit);
            }
        }

        if(attackableUnits.size() > 0){
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
                        else{
                            unitToAttack.killInstance();
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
}
