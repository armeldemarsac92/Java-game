package GameLogic;

import java.util.Dictionary;
import java.util.Hashtable;
import Exceptions.*;

public class Coordinates {
    private Dictionary<String, Integer> coordinates = new Hashtable<>();

    public Coordinates(int posAtX, int posAtY){
        this.coordinates.put("x", posAtX);
        this.coordinates.put("y", posAtY);
    }

    public Dictionary<String, Integer> getCoordinates(){
        return this.coordinates;
    }

    public void setCoordinates(int posAtX, int posAtY){
        this.coordinates.put("x", posAtX);
        this.coordinates.put("y", posAtY);
    }

    public void setXPos(int posOnX){
        this.coordinates.put("x", posOnX);
    }

    public void setYPos(int posOnY){
        this.coordinates.put("y", posOnY);
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
