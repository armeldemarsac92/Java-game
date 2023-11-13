package GameLogic;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ArcherTower extends ATower{ // archer tower just shoots at enemies
    public ArcherTower(Coordinates coordinates){
        super(10, 1, 150, 1, coordinates);
        
        try {
            this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
