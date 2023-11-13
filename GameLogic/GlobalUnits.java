package GameLogic;

import java.util.*;

public class GlobalUnits {
    protected static List<AUnit> units = new ArrayList<AUnit>();

    public static boolean add(AUnit unit) {
        if(GlobalUnits.units.add(unit)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean remove(AUnit unit){
        if(GlobalUnits.units.remove(unit)){
            return true;
        }
        else{
            return false;
        }
    }

    public static void display() {
        for (AUnit unit : GlobalUnits.units) {
            System.out.println(unit.getId());
        }
    }

    public static List<AUnit> getGlobalUnits(){
        return GlobalUnits.units;
    }
}
