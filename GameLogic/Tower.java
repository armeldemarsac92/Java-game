package GameLogic;

import Exceptions.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;


public class Tower extends Unit {

    /*---------- Attributes ---------- */

    private int price;
    private int level = 0;

    /*---------- Constructor ---------- */
    public Tower(int damage, int damageRate, int range, int capacity, boolean upgrade, Coordinates coordinates){
        super(damage, damageRate, range, capacity, coordinates);
        try {
            this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl0.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (upgrade){
            try {
                this.upgrade();
            } catch (MaximumLevelReachedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
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
}
