package GameLogic;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;


public class IceTower extends Tower { // Ice tower slows enemies down

    /*---------- Constructor ---------- */

    public IceTower(Coordinates coordinates){
        super(5, 1, 150, 1, coordinates);
        try {
            this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl0.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
