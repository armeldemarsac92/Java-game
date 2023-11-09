package Test;

import Exceptions.*;
import GameLogic.*;

public class GameLogicTest {
    
    public static void main(String[] args) throws NoSuchCoordinateKeyException, MaximumLevelReachedException{
        Tower tower = new Tower(1, 1, 1, 1, new Coordinates(2, 0));
        Barbarian barbarian = new Barbarian(1, 50, 5, 5, 0, 0, new Coordinates(0, 4));
        tower.upgrade();
        for(int i = 0; i < GlobalUnits.getGlobalUnits().size(); i++){
            System.out.println(GlobalUnits.getGlobalUnits().get(i).getUnitCoordinates().getCoordinates());
            System.out.println(GlobalUnits.getGlobalUnits().get(i).getId());
        }

        for(int i = 0; i < 10; i++){
            barbarian.move();   
        }
    }
}
