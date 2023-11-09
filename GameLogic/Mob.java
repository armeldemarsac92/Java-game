package GameLogic;

import Exceptions.NoSuchCoordinateKeyException;

public abstract class Mob extends Unit{

    /*---------- Attributes ---------- */

    protected int speed;
    protected int hp;
    protected String deathSoundFilePath;


    /*---------- Constructor ---------- */
    public Mob(int speed, int hp, int damage, int damageRate, int range, int capacity,Coordinates coordinates){
        super(damage, damageRate, range, capacity, coordinates);
        this.hp = hp;
        this.speed = speed;
    }


    /*---------- Getters ---------- */
    
    public int getSpeed(){
        return this.speed;
    }

    public int getHp(){
        return this.hp;
    }


    /*---------- Setters ---------- */
    
    public void setHp(int damage){
        int hpTemp = this.hp;

        if(hpTemp - damage < 0){
            hpTemp = 0;
        }
        else{
            hpTemp -= damage;
        }

        this.hp = hpTemp;
    }


    /*---------- Methods ---------- */
    public void move(){
        try {
            this.getUnitCoordinates().setXPos(this.getUnitCoordinates().get("x") + this.getSpeed());
            System.out.println("Pos on x: " + this.getUnitCoordinates().get("x")); // Debug
        } catch (NoSuchCoordinateKeyException e) {
            System.out.println(e.getMessage());
        }
    }


}
