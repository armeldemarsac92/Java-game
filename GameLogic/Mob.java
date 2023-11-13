package GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Exceptions.NoSuchCoordinateKeyException;

public abstract class Mob extends AUnit{

    /*---------- Attributes ---------- */

    protected int speed;
    protected int hp;
    protected int coinValue;
    protected String deathSoundFilePath;
    private boolean alive = true;


    /*---------- Constructor ---------- */
    public Mob(int speed, int hp, int damage, int damageRate, int range, int capacity, int coinValue, Coordinates coordinates){
        
        super(damage, damageRate, range, capacity, coordinates);
        this.hp = hp;
        this.speed = speed;
        this.coinValue = coinValue;
    }


    /*---------- Getters ---------- */
    
    public int getSpeed(){
        return this.speed;
    }

    public int getHp(){
        return this.hp;
    }

    public int getCointValue(){
        return this.coinValue;
    }

    public boolean isAlive(){
        return this.alive;
    }


    /*---------- Setters ---------- */
    
    public void setHp(int damage){
        int hpTemp = this.hp;

        if(hpTemp - damage <= 0){
            hpTemp = 0;
            this.setAlive(false);
            this.killInstance();
        }
        else{
            hpTemp -= damage;
        }

        this.hp = hpTemp;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
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

    @Override 
    public void computeUnitsInRange(){
        // initialize empty list (serves as setter)
        List<AUnit> unitsInRangeTemp = new ArrayList<AUnit>();
        
        // For each unit in global list
        for(AUnit unit : GlobalUnits.getGlobalUnits()){
            if(unit instanceof Tower){
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

    @Override
    public void attackUnitsInRange(){

        if(this.unitsInRange.size() > 0){
            try {
                // delay the action
                TimeUnit.SECONDS.sleep(this.damageRate);
                // attack each unit in range if it has enough capacity
                for(int i = 0; i < this.unitsInRange.size(); i++){
                    Castle unitToAttack = (Castle) this.unitsInRange.get(i);
                    if(i <= this.capacity){
                        System.out.println(this.getClass().getSimpleName() + " inflicts " + this.getDamage() + " damage to " 
                        + unitToAttack.getClass().getSimpleName() + "(" + unitToAttack.getHp() + " hp left)");
                        this.attack(unitToAttack);
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

    public void killInstance(){
        System.out.println("Instance of " + this.getClass().getSimpleName() + " was killed");
        GlobalUnits.remove(this);
        CoinSystem.earnCoins(this.coinValue);
    }   

    @Override 
    public <T> void attack(T unit){
        Castle castedUnit = (Castle) unit;

        if(castedUnit.isUp()){
            castedUnit.setHp(this.damage);
        }
        else{
            castedUnit.gameOver();
        }
    }


}
