import Exceptions.*;
import GameLogic.*;
import PixelMap.GamePanel;

public class GameLogicTest {
    public static GamePanel gamePanel = new GamePanel(null);
    
    public static void main(String[] args) throws NoSuchCoordinateKeyException, MaximumLevelReachedException{
        ArcherTower tower = new ArcherTower(new Coordinates(2, 2), GameLogicTest.gamePanel);
        IceTower iceTower = new IceTower(new Coordinates(5, 5), GameLogicTest.gamePanel);
        Barbarian barbarian = new Barbarian(new Coordinates(0, 4), GameLogicTest.gamePanel);
        Barbarian secondBarbarian = new Barbarian(new Coordinates(1000, 1500), GameLogicTest.gamePanel);
        Tanker tanker = new Tanker(new Coordinates(1, 21), GameLogicTest.gamePanel);
        Castle castle = new Castle(GameLogicTest.gamePanel);

        tower.computeUnitsInRange();

        System.out.println("Unit count: " + GlobalUnits.getGlobalUnits().size());


        for(AUnit unit : tower.getUnitsInRange()){
            System.out.println("Unit in range: " + unit.getClass().getSimpleName());
        }
        tower.attackUnitsInRange();
        tower.attackUnitsInRange();
        tower.attackUnitsInRange();
        tower.attackUnitsInRange();
        tower.attackUnitsInRange();
        tower.attackUnitsInRange();
        tower.attackUnitsInRange();

        System.out.println("Unit count: " + GlobalUnits.getGlobalUnits().size());
    }
}
