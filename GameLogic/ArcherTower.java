package GameLogic;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import PixelMap.GamePanel;

public class ArcherTower extends ATower{ // archer tower just shoots at enemies
    public ArcherTower(Coordinates coordinates, GamePanel gamePanel){
        super(1, 10000, 0, 1, coordinates, gamePanel);
        
        try {
            this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
