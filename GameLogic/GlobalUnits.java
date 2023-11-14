package GameLogic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalUnits {
    protected static List<AUnit> units = new CopyOnWriteArrayList<>();

    public static boolean add(AUnit unit) {
        return units.add(unit);
    }

    public static boolean remove(AUnit unit){
        return units.remove(unit);
    }

    public static void display() {
        for (AUnit unit : units) {
            System.out.println(unit.getId());
        }
    }

    public static int getIndex(AUnit unit){
        return GlobalUnits.units.indexOf(unit);
    }

    public static List<AUnit> getGlobalUnits(){
        return units;
    }

    public static void cleanup() {
        for (AUnit unit : units) {
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
