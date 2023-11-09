package GameLogic;

import java.util.*;

public class GlobalUnits {
    protected static List<Unit> units = new ArrayList<Unit>();

    public static boolean add(Unit unit) {
        if(GlobalUnits.units.add(unit)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean remove(Unit unit){
        if(GlobalUnits.units.remove(unit)){
            return true;
        }
        else{
            return false;
        }
    }

    public static void display() {
        for (Unit unit : GlobalUnits.units) {
            System.out.println(unit.getId());
        }
    }

    public static List<Unit> getGlobalUnits(){
        return GlobalUnits.units;
    }
}
