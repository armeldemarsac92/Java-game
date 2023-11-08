package GameLogic;

public class Tanker extends Mob {
    /*---------- Constructor ---------- */
    public Tanker(int speed, int hp, int damage, int damageRate, int range, int capacity, String attackSoundFilePath, 
    String deathSoundFilePath, String characterSpriteFilePath, Coordinates coordinates){
        super(hp, speed, damage, damageRate, range, capacity, attackSoundFilePath, deathSoundFilePath, characterSpriteFilePath, coordinates);
    }
}
