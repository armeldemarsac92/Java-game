package GameLogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
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
    private JLabel hpLabel;

    /*---------- Constructor ---------- */
    public AMob(float speed, int hp, int damage, int damageRate, int range, int capacity, int coinValue, Coordinates coordinates, GamePanel gamePanel){
        super(damage, damageRate, range, capacity, coordinates, gamePanel);
        this.hp = hp;
        this.speed = speed;
        this.coinValue = coinValue;

        // Initialize text label
        this.hpLabel = new JLabel("HP: " + this.hp);
        this.hpLabel.setFont(new Font("Arial", Font.BOLD, 15));
        this.hpLabel.setForeground(Color.WHITE);
        parentContainer.add(this.hpLabel);
        parentContainer.setComponentZOrder(this.hpLabel, Math.abs(GlobalUnits.getIndex(this) * (-1)) + 1); // Place it above the mob

        this.updateTextLabelPosition(); // Set initial position
    }


    /*---------- Getters ---------- */
    
    public float getSpeed(){
        return this.speed;
    }

    public int getHp(){
        return this.hp;
    }

    public int getCoinsValue(){
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

        SwingUtilities.invokeLater(this::updateTextLabelPosition);
    }

    private void updateTextLabelPosition() {
        try {
            Coordinates coords = getUnitCoordinates();
            
            // Check if hpLabel is not null before using it
            if (this.hpLabel != null) {
                this.hpLabel.setBounds(coords.get("x"), coords.get("y") - 20, 100, 20); // Adjust as needed
                this.hpLabel.setText("HP: " + this.hp); // Set text as needed
            }
        } catch (NoSuchCoordinateKeyException e) {
            e.printStackTrace(); // Handle exception
        }
    }

    @Override
    public void cleanFromView() {
        super.cleanFromView();
        if (this.hpLabel.getParent() != null) {
            this.hpLabel.getParent().remove(this.hpLabel);
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

    public void killInstance() {
        GlobalUnits.remove(this);
        // System.out.println("Instance of " + this.getClass().getSimpleName() + " was killed");
    
        // Set the new animation parameters
        this.setNumberOfFrames(31);
        this.setCoreFilePath("assets/dead_barbarian/Illustration_sans_titre-");
    
        // Trigger the reloading of the animation frames
        this.reloadAnimationFramesAsync();
    
        // Award points for killing the mob
        UserInterface.earnCoins(this.coinValue);

        // Increment the score
        UserInterface.incrementScore(this.coinValue);
        this.hpLabel.remove(parentContainer);
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
