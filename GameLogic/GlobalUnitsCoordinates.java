package GameLogic;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class GlobalUnitsCoordinates {
    private static List<Dictionary<Unit, Coordinates>> globalUnitsCoordinates = new ArrayList<Dictionary<Unit, Coordinates>>();


    public static List<Dictionary<Unit, Coordinates>> getGlobalUnitsCoordinates(){
        return GlobalUnitsCoordinates.globalUnitsCoordinates;
    }

    public static boolean add(Dictionary<Unit, Coordinates> unitAndItsCoordinates){
        if(GlobalUnitsCoordinates.globalUnitsCoordinates.add(unitAndItsCoordinates)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean remove(Dictionary<Unit, Coordinates> unitAndItsCoordinates){
        if(GlobalUnitsCoordinates.globalUnitsCoordinates.add(unitAndItsCoordinates)){
            return true;
        }
        else{
            return false;
        }
    }

    // public void display(){
    //     for(Dictionary<Unit, Coordinates> unitAndItsCoordinates : GlobalUnitsCoordinates.globalUnitsCoordinates){
    //         System.out.println(unitAndItsCoordinates.get());
    //     }
    // }
}
