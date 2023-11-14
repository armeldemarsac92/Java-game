package GameLogic;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import PixelMap.GamePanel;

import Exceptions.NoSuchCoordinateKeyException;

public abstract class AMob extends AUnit{

    /*---------- Attributes ---------- */

    protected float speed;
    protected int hp;
    protected int coinValue;
    protected String deathSoundFilePath;
    private boolean alive = true;
    protected List<Image> frames = new ArrayList<>();
    protected int currentFrame = 0;


    /*---------- Constructor ---------- */
    public AMob(float speed, int hp, int damage, int damageRate, int range, int capacity, int coinValue, Coordinates coordinates, GamePanel gamePanel){
        super(damage, damageRate, range, capacity, coordinates, gamePanel);
        this.hp = hp;
        this.speed = speed;
        this.coinValue = coinValue;
    }


    /*---------- Getters ---------- */
    
    public float getSpeed(){
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

    public void setSpeed(float speed){
        this.speed = speed;
    }


    /*---------- Methods ---------- */

    public void move() {
        try {
            // Update the position of the JLabel
            Coordinates coords = getUnitCoordinates();
            coords.setXPos(coords.get("x") + (int) getSpeed());
            this.getUnitLabel().setLocation(coords.get("x"), coords.get("y"));
            // System.out.println("Pos on x: " + coords.get("x")); // Debug
        } catch (NoSuchCoordinateKeyException e) {
            System.out.println("no coordinates");
            e.printStackTrace(); // Consider more meaningful exception handling
        }
    }

    @Override 
    public void computeUnitsInRange(){
        // initialize empty list (serves as setter)
        List<AUnit> unitsInRangeTemp = new ArrayList<AUnit>();
        
        // For each unit in global list
        for(AUnit unit : GlobalUnits.getGlobalUnits()){
            if(unit instanceof ATower){
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

    public boolean isOutsideMap(){
        try {
            if (this.getUnitCoordinates().get("x")>2500){
                return true;
            } else {
                return false;
            }
        } catch (NoSuchCoordinateKeyException e) {
            return false;
        }
    }


}
