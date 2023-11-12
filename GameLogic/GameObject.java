package GameLogic;

import java.util.List;
import java.awt.*;

public interface GameObject {
    public int getDamage();
    public int getDamageRate();
    public int getCapacity();

    public Image getAttackSoundFilePath();
    public Image getDeathSoundFilePath();
    public Image getCharacterSpriteImage();

    public Coordinates getUnitCoordinates();
    
    public List <Unit> getUnitsInRange();

    public void setCoordinates(Coordinates coordinates);
    public void setUnitsInRange(List<Unit> unitsInRange);
    public void computeUnitsInRange();
    public void attackUnitsInRange();
}