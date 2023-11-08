package Test;

import Exceptions.NoSuchCoordinateKeyException;
import GameLogic.*;

public class GameLogicTest {
    
    public static void main(String[] args) throws NoSuchCoordinateKeyException {
        Unit tower = new Tower();
        Coordinates towerCoordinate = tower.getUnitCoordinates();
        // System.out.println("Position on y: " + towerCoordinate.get("z"));

        for(int i = 0; i < 10; i++){
            Coordinates towerCoordinates = tower.getUnitCoordinates();
            System.out.println("Position on x: " + towerCoordinates.get("x"));
            System.out.println("Position on y: " + towerCoordinates.get("y"));
            towerCoordinates.setXPos(i);
        }
    }
}
