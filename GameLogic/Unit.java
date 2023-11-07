package GameLogic;

import java.util.List;
import java.util.Map;

public interface Unit <T> {
    public int getHp();
    public int getDamage();
    public int getDamageRate();

    public String getName();
    public String getAttackSound();
    public String getDeathSound();
    public String getSprite();

    public Map<String, Integer> getCoordinates();
    
    public List<T> getUnitsInRange(int range, List<T> globalUnitList);

    public void attachUnitsInRange(int capacity, List<T> unitsInRange);
}