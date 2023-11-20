package GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import PixelMap.GamePanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import Exceptions.MaximumLevelReachedException;
import Exceptions.NoSuchCoordinateKeyException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public abstract class AUnit implements IGameObject, MouseListener {

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
    private int numberOfFrames;


    protected int sizeX;
    protected int sizeY;

    ImageCache imageCache = new ImageCache();



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
        this.animationFrames = new ArrayList<>();

        if(this instanceof ATower){
            this.unitLabel.addMouseListener(this);
        }
        
        try {
            unitCoordinates.add(coordinates.get("x"));
            unitCoordinates.add(coordinates.get("y"));
        } catch (NoSuchCoordinateKeyException e) {
            // System.out.println(e.getMessage());
        }

        

        GlobalUnits.add(this);
        GlobalUnitsCoordinates.add(this.getId(), unitCoordinates);
        // System.out.println(this.getClass().getSimpleName());


        if(this.getClass().getSimpleName().equals("Tanker") ){
            this.coreFilePath = "assets/tanker/Tanker-";
            this.sizeX = 100;
            this.sizeY = 100;
            this.numberOfFrames = 7;
        }
        else if (this.getClass().getSimpleName().equals("Barbarian") ){
            this.sizeX = 100;
            this.sizeY = 100;
            this.coreFilePath = "assets/barbarian/Barbarian-";
            this.numberOfFrames = 7;

        }
        else if(this.getClass().getSimpleName().equals("IceTower") ){
            this.sizeX = 200;
            this.sizeY = 200;
            this.coreFilePath = "assets/ice_tower/level_0/IceTower-";
            this.numberOfFrames = 32;

        }
        else if(this.getClass().getSimpleName().equals("ArcherTower") ) {
            this.coreFilePath = "assets/archer_tower/level_0/Tower-";
            this.sizeX = 200;
            this.sizeY = 200;
            this.numberOfFrames = 32;
        }
        else if(this.getClass().getSimpleName().equals("Castle")){
            this.coreFilePath = "assets/castle/level_0/Castle-";
            this.sizeX = 200;
            this.sizeY = 200;
            this.numberOfFrames = 32;

        } else {
            // System.out.println("yop");
        }


        try {
            this.unitLabel.setBounds(this.coordinates.get("x"), this.coordinates.get("y"), this.sizeX, this.sizeY);
        } catch(NoSuchCoordinateKeyException e){
            e.printStackTrace();
        }

        parentContainer.add(this.unitLabel);
        parentContainer.setComponentZOrder(this.unitLabel, Math.abs(GlobalUnits.getIndex(this) * (-1)));

        this.loadAnimationFrames(imageCache);
        this.startAnimation();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked on coordinates " + e.getX() + ";" + e.getY() + " , type: " + this.getClass().getSimpleName());
        try {
            ((ATower)this).upgrade();
        } catch (MaximumLevelReachedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if(this.unitLabel.getText() == ""){
            this.unitLabel.setText("Id: " + this.getId());
        }
        else{
            this.unitLabel.setText("");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.unitLabel.setText("");
    }


    protected void loadAnimationFrames(ImageCache cache) {
        this.animationFrames = new ArrayList<>();
    
        try {
            for (int i = 1; i <= this.numberOfFrames; i++) {
                String imagePath = this.coreFilePath + i + ".png";
    
                Image img = cache.getImage(imagePath);
                if (img == null) {
                    try {
                        // System.out.println("loading new images");
                        img = ImageIO.read(new File(imagePath));
                        // System.out.println(coreFilePath);
                        cache.addImage(imagePath, img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
    
                // Only scale if necessary
                if (img != null && (img.getWidth(null) != this.sizeX || img.getHeight(null) != this.sizeY)) {
                    img = scaleImage(img, this.sizeX, this.sizeY);
                }
                if (img != null) {
                    this.animationFrames.add(new ImageIcon(img));
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    // Improved scaleImage method to handle null images and reduce memory usage
    private Image scaleImage(Image srcImg, int width, int height) {
        if (srcImg == null) {
            return null;
        }
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();

        return resizedImg;
    }

    protected void reloadAnimationFramesAsync() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // System.out.println("Loading new animation frames...");
                loadAnimationFrames(imageCache);
                return null;
            }
    
            @Override
            protected void done() {
                try {
                    synchronized (AUnit.this) {
                        // System.out.println("New frames loaded: " + animationFrames.size());
                        updateAnimationFrames(new ArrayList<>(animationFrames));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private synchronized void updateAnimationFrames(List<ImageIcon> newFrames) {
        // System.out.println("Updating animation frames...");
        if (this.animationTimer != null) {
            this.animationTimer.stop();
            // System.out.println("Animation timer stopped.");
        }
    
        this.animationFrames = newFrames;
        this.currentFrame = 0;
    
        if (!newFrames.isEmpty()) {
            startAnimation();
            // System.out.println("Animation restarted.");
        } else {
            // System.out.println("No new frames to animate.");
        }
    }
    


    protected void startAnimation() {
        this.animationTimer = new Timer(50, e -> updateAnimation());
        this.animationTimer.start();
    }

    protected void updateAnimation() {
        // Ensure there are frames to animate
        if (!animationFrames.isEmpty()) {
            // Set the current frame's icon, ensuring currentFrame is within bounds
            if (this.currentFrame < animationFrames.size()) {
                this.unitLabel.setIcon(animationFrames.get(this.currentFrame));
            }
    
            // Increment the frame index
            this.currentFrame++;

            if (this instanceof AMob && ((AMob)this).isAlive() == false && this.currentFrame >= animationFrames.size()){
                this.animationTimer.stop();
                System.out.println(this.unitLabel.getParent().getComponentZOrder(unitLabel));
                this.unitLabel.getParent().setComponentZOrder(unitLabel,0);
                System.out.println("zindex :"+(-1200+this.getId()));
                new Timer(5000, new ActionListener() { // 5000 milliseconds = 5 seconds
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        unitLabel.setVisible(false); // Hide the unit label after 5 seconds
                        AUnit.this.cleanFromView();
                    }
                }).start();
            
            }
            }
    
            // Loop back to the first frame if necessary
            if (this.currentFrame >= animationFrames.size()) {
                this.currentFrame = 0;
            }
    
            // Additional logic for AMob instances
        }
    
    
    

    public void cleanFromView() {
        if (this.animationTimer != null) {
            this.animationTimer.stop();
        }
        if (this.unitLabel.getParent() != null) {
            // System.out.println("sprite removed");
            // this.unitLabel.setVisible(false);
            this.unitLabel.getParent().remove(this.unitLabel);
        }
    }
    

    /*---------- Getters ---------- */

    public AUnit getType(){
        return this;
    }

    public JLabel getUnitLabel(){
        return this.unitLabel;
    }

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
        return this.range;
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

    public void setNumberOfFrames(int numberOfFrames){
        this.numberOfFrames = numberOfFrames;
    }


    /*---------- Methods ---------- */
    public abstract void attackUnitsInRange();
    public abstract void computeUnitsInRange();

}

