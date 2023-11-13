package GameLogic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalUnits {
    protected static List<Unit> units = new CopyOnWriteArrayList<>();

    public static boolean add(Unit unit) {
        return units.add(unit);
    }

    public static boolean remove(Unit unit){
        return units.remove(unit);
    }

    public static void display() {
        for (Unit unit : units) {
            System.out.println(unit.getId());
        }
    }

    public static List<Unit> getGlobalUnits(){
        return units;
    }

    public static void cleanup() {
        for (Unit unit : units) {
            if (unit instanceof Barbarian && ((Barbarian)unit).isOutsideMap()){
                units.remove(unit);
                ((Barbarian)unit).cleanup();
                // Perform any additional cleanup required for the unit
            } else {
                if (unit instanceof Tanker && ((Tanker)unit).isOutsideMap()){
                units.remove(unit);
                ((Tanker)unit).cleanup();
                }
            }
        }
    }
}
