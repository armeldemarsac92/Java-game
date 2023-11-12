package Test;

import Exceptions.*;
import GameLogic.*;

public class GameLogicTest {
    
    public static void main(String[] args) throws NoSuchCoordinateKeyException, MaximumLevelReachedException{
        Tower tower = new Tower(new Coordinates(2, 2));
        Barbarian barbarian = new Barbarian(new Coordinates(0, 4));
        Tanker tanker = new Tanker(new Coordinates(1, 21));

        tower.computeUnitsInRange();

        // for(int i = 0; i < GlobalUnits.getGlobalUnits().size(); i++){
        //     System.out.println(GlobalUnits.getGlobalUnits().get(i).getUnitCoordinates().getCoordinates());
        //     System.out.println(GlobalUnits.getGlobalUnits().get(i).getId());
        // }

        for(Unit unit : tower.getUnitsInRange()){
            System.out.println("Unit in range: " + unit.getClass().getSimpleName());
        }
           

        // System.out.println(GlobalUnitsCoordinates.getGlobalUnitsCoordinates().get(tower.getId()));

        // for(int i = 0; i < 10; i++){
        //     barbarian.move();   
        // }
    }
}
