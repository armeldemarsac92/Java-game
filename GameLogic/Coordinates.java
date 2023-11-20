package GameLogic;

import java.util.Dictionary;
import java.util.Hashtable;
import Exceptions.*;

public class Coordinates {
    private Dictionary<String, Integer> coordinates = new Hashtable<>();

    public Coordinates(int x, int y){
        this.coordinates.put("x", x);
        this.coordinates.put("y", y);
    }

    public Dictionary<String, Integer> getCoordinates(){
        return this.coordinates;
    }

    public void setCoordinates(int x, int y){
        this.coordinates.put("x", x);
        this.coordinates.put("y", y);
    }

    public void setXPos(int x){
        this.coordinates.put("x", x);
    }

    public void setYPos(int y){
        this.coordinates.put("y", y);
    }

    public Integer get(String key) throws NoSuchCoordinateKeyException{
        if(key != "x" && key != "y"){
            throw new NoSuchCoordinateKeyException("No such key " + "\"" + key + "\" in coordinates");
        }
        else {
            return this.coordinates.get(key);
        }
    }
}
