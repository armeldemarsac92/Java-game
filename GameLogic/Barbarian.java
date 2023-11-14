package GameLogic;

import PixelMap.GamePanel;

public class Barbarian extends AMob {

    /*---------- Contructor ---------- */

    public Barbarian(Coordinates coordinates, GamePanel gamePanel) {
        super(50.0f, 50, 2, 1, 1, 1, 5, coordinates, gamePanel);
    }
}



