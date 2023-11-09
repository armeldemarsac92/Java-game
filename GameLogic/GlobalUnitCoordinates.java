package GameLogic;

import java.util.Dictionary;
import java.util.Hashtable;

public class GlobalUnitCoordinates {
    private Dictionary<Unit, Coordinates> globalCoordinates = new Hashtable<Unit, Coordinates>();


    public Dictionary<Unit, Coordinates> getGlobalCoordinates(){
        return this.globalCoordinates;
    }
}
