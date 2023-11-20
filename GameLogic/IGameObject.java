package GameLogic;

import java.util.List;
import java.awt.*;

public interface IGameObject {
    public int getDamage();
    public int getDamageRate();
    public int getCapacity();

    public Image getAttackSoundFilePath();
    public Image getDeathSoundFilePath();
    public Image getCharacterSpriteImage();

    public Coordinates getUnitCoordinates();
    
    public List <AUnit> getUnitsInRange();

    public void setCoordinates(Coordinates coordinates);
    public void setUnitsInRange(List<AUnit> unitsInRange);
    public void computeUnitsInRange();
    public void attackUnitsInRange();
    public <T> void attack(T unit);
}