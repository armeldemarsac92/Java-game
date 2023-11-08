package Test;

import Exceptions.*;
import GameLogic.*;

public class GameLogicTest {
    
    public static void main(String[] args) throws NoSuchCoordinateKeyException, MaximumLevelReachedException{
        Tower tower = new Tower(1, 1, 1, 1, ".", ".", new Coordinates(2, 0));
        Barbarian barbarian = new Barbarian(1, 50, 5, 5, 0, 0, ".", ".", ".", new Coordinates(0, 4));
        // System.out.println("Position on y: " + towerCoordinate.get("z"));
        tower.upgrade();
        tower.upgrade();
        tower.upgrade();

        // System.out.println("Global list: " + Unit.getGlobalUnitList());

        for(int i = 0; i < 10; i++){
            // Coordinates towerCoordinates = tower.getUnitCoordinates();
            // System.out.println("Position on x: " + towerCoordinates.get("x"));
            // System.out.println("Position on y: " + towerCoordinates.get("y"));
            // towerCoordinates.setXPos(i);
            barbarian.move();   
        }
    }
}
