package GameLogic;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import PixelMap.GamePanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import Exceptions.NoSuchCoordinateKeyException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class AUnit implements IGameObject {

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

    protected List<AUnit> unitsInRange;

    protected Coordinates coordinates;

    protected Container parentContainer;
    private JLabel unitLabel;
    private Timer animationTimer;
    private List<ImageIcon> animationFrames;
    private int currentFrame = 0;
    private String coreFilePath;


    private int sizeX;
    private int sizeY;


    /*---------- Constructor ---------- */
    
    public AUnit(int damage, int damageRate, int range, int capacity, Coordinates coordinates, GamePanel gamePanel){
        List<Integer> unitCoordinates = new ArrayList<Integer>();
        this.parentContainer = gamePanel;
        AUnit.counter++;
        this.id = AUnit.counter;
        this.damage = damage;
        this.damageRate = damageRate;
        this.capacity = capacity;
        this.range = range;
        this.unitLabel = new JLabel();
        this.coordinates = coordinates;
        

        try {
            unitCoordinates.add(coordinates.get("x"));
            unitCoordinates.add(coordinates.get("y"));
        } catch (NoSuchCoordinateKeyException e) {
            System.out.println(e.getMessage());
        }

        

        GlobalUnits.add(this);
        GlobalUnitsCoordinates.add(this.getId(), unitCoordinates);
        System.out.println(this.getClass().getSimpleName());


        if(this.getClass().getSimpleName().equals("Tanker") ){
            this.coreFilePath = "assets/tanker/Tanker-";
            this.sizeX = 100;
            this.sizeY = 100;
        }
        else if (this.getClass().getSimpleName().equals("Barbarian") ){
            this.sizeX = 300;
            this.sizeY = 300;
            this.coreFilePath = "assets/barbarian/Barbarian-";
        }
        else if(this.getClass().getSimpleName().equals("IceTower") ){
            this.sizeX = 300;
            this.sizeY = 300;
            this.coreFilePath = "assets/ice_tower/level_0/IceTower-";
        }
        else if(this.getClass().getSimpleName().equals("ArcherTower") ) {
            this.coreFilePath = "assets/archer_tower/level_0/ArcherTower-";
            this.sizeX = 300;
            this.sizeY = 300;
        }
        else if(this.getClass().getSimpleName().equals("Castle")){
            this.coreFilePath = "assets/castle/level_0/Castle-";
            this.sizeX = 300;
            this.sizeY = 300;
        } else {
            System.out.println("yop");
        }


        try {
            this.unitLabel.setBounds(this.coordinates.get("x"), this.coordinates.get("y"), this.sizeX, this.sizeY);
        } catch(NoSuchCoordinateKeyException e){
            e.printStackTrace();
        }

        parentContainer.add(this.unitLabel);
        parentContainer.setComponentZOrder(this.unitLabel, Math.abs(GlobalUnits.getIndex(this) * (-1)));

        this.loadAnimationFrames();
        this.startAnimation();
    }

    private void loadAnimationFrames() {
        animationFrames = new ArrayList<>();

        try {
            for (int i = 1; i <= 7; i++) { 
                Image img = ImageIO.read(new File(this.coreFilePath + i + ".png"));
                Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
                this.animationFrames.add(new ImageIcon(scaledImg));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JLabel getUnitLabel(){
        return this.unitLabel;
    }

    private void startAnimation() {
        this.animationTimer = new Timer(50, e -> updateAnimation());
        this.animationTimer.start();
    }

    public void updateAnimation() {
        this.currentFrame = (this.currentFrame + 1) % animationFrames.size();
        this.unitLabel.setIcon(animationFrames.get(this.currentFrame));
    }

    public void cleanup() {
        if (this.animationTimer != null) {
            this.animationTimer.stop();
        }
        if (this.unitLabel.getParent() != null) {
            this.unitLabel.getParent().remove(this.unitLabel);
        }
    }

    public AUnit getType(){
        return this;
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
        return AUnit.counter;
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

    public List<AUnit> getUnitsInRange(){
        return this.unitsInRange;
    }

    public Coordinates getUnitCoordinates(){
        return this.coordinates;
    }

    public String getCoreFilePath(){
        return this.coreFilePath;
    }


    /*---------- Setters ---------- */

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setUnitsInRange (List<AUnit> unitsInRange){
        this.unitsInRange = unitsInRange;
    }

    public void setCoreFilePath(String coreFilePath){
        this.coreFilePath = coreFilePath;
    }


    /*---------- Methods ---------- */
    public abstract void attackUnitsInRange();
    public abstract void computeUnitsInRange();

}

