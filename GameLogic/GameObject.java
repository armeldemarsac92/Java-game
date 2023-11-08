package GameLogic;

import java.util.List;

public interface GameObject {
    public int getDamage();
    public int getDamageRate();
    public int getCapacity();

    public String getAttackSoundFilePath();
    public String getDeathSoundFilePath();
    public String getCharacterSpriteFilePath();

    public Coordinates getUnitCoordinates();
    
    public List<? extends Unit> getUnitsInRange(int range, List<? extends Unit> globalUnitList);

    public void setCoordinates(Coordinates coordinates);
    public void attackUnitsInRange(List<? extends Unit> unitsInRange);
}