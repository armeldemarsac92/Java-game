package GameLogic;

public class Barbarian extends Mob {
    /*---------- Constructor ---------- */
    public Barbarian(int speed, int hp, int damage, int damageRate, int range, int capacity, String attackSoundFilePath, 
    String deathSoundFilePath, String characterSpriteFilePath, Coordinates coordinates){
        super(hp, speed, damage, damageRate, range, capacity, attackSoundFilePath, deathSoundFilePath, characterSpriteFilePath, coordinates);
    }
}
