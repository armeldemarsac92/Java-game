package GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Exceptions.NoSuchCoordinateKeyException;

public class Castle extends AUnit{

    /*---------- Attributes ---------- */

    private int hp = 500;
    private boolean up = true;
    private String characterSpriteFilePath = ".";
    private Coordinates coordinates = new Coordinates(1, 1);     
    

    /*---------- Constructor ---------- */

    public Castle(){
        super(20, 1, 100, 2, new Coordinates(1, 1));

        System.out.println("----------- Castle instantiated -----------");
        System.out.println("Hp :" + this.getHp());
        System.out.println("Character spriteFilePath: " + this.getCharacterSpriteFilePath());
        System.out.println("Coordinates :" + this.getCastleCoordinates().getCoordinates());
    }


    /*---------- Getters ---------- */

    public int getHp(){
        return this.hp;
    }

    public String getCharacterSpriteFilePath(){
        return this.characterSpriteFilePath;
    }

    public Coordinates getCastleCoordinates(){
        return this.coordinates;
    }

    public boolean isUp(){
        return this.up;
    }


    /*---------- Setters ---------- */
    
    public void setHp(int damage){
        int hpTemp = this.hp;

        if(hpTemp - damage <= 0){
            hpTemp = 0;
            this.setUp(false);
        }
        else{
            hpTemp -= damage;
        }

        this.hp = hpTemp;
    }

    public void setUp(boolean up){
        this.up = up;
    }


    /*---------- Methods ---------- */

    @Override 
    public void computeUnitsInRange(){
        // initialize empty list (serves as setter)
        List<AUnit> unitsInRangeTemp = new ArrayList<AUnit>();
        
        // For each unit in global list
        for(AUnit unit : GlobalUnits.getGlobalUnits()){
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
                    Mob unitToAttack = (Mob) this.unitsInRange.get(i);
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

    public void gameOver(){
        System.out.println("Game over!");
    }
}
