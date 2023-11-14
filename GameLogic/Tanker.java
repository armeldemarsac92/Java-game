package GameLogic;

import PixelMap.GamePanel;

public class Tanker extends AMob {
    /*---------- Constructor ---------- */
    public Tanker(Coordinates coordinates, GamePanel gamePanel){
        super(1.0f, 100, 5, 1, 1, 1, 10, coordinates, gamePanel);
    }
}