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
    
    public List<? extends Unit> getUnitsInRange(int range, List<? extends Unit> globalUnitList);

    public void setCoordinates(Coordinates coordinates);
    public void attackUnitsInRange(List<? extends Unit> unitsInRange);
}