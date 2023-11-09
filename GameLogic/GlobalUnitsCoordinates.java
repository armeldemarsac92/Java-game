package GameLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class GlobalUnitsCoordinates {
    private static Map<Integer, List<Integer>> globalUnitsCoordinates = new HashMap<Integer, List<Integer>>();


    public static Map<Integer, List<Integer>> getGlobalUnitsCoordinates(){
        return GlobalUnitsCoordinates.globalUnitsCoordinates;
    }

    public static void add(Integer unitId, List<Integer> coordinatesXY){
        GlobalUnitsCoordinates.globalUnitsCoordinates.put(unitId, coordinatesXY);
    }

    public static void remove(Unit unit, Coordinates coordinates){
        GlobalUnitsCoordinates.globalUnitsCoordinates.remove(unit, coordinates);
    }
}
