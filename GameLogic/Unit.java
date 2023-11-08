package GameLogic;

import java.util.List;

public abstract class Unit implements GameObject {

    /*---------- Attributes ---------- */

    protected int damage;
    protected int capacity;
    protected int damageRate;

    protected float range;

    protected String name;
    protected String attackSoundFilePath;
    protected String deathSoundFilePath;
    protected String characterSpriteFilePath;

    protected static List<Unit> globalUnits;
    protected List<? extends Unit> unitsInRange;

    protected Coordinates coordinates;


    /*---------- Getters ---------- */
    public Unit(int damage, int damageRate, int range, int capacity, String attackSoundFilePath, 
    String characterSpriteFilePath, Coordinates coordinates){
        this.damage = damage;
        this.damageRate = damageRate;
        this.capacity = capacity;
        this.attackSoundFilePath = attackSoundFilePath;
        this.characterSpriteFilePath = characterSpriteFilePath;
        this.coordinates = coordinates;
    }


    /*---------- Getters ---------- */

    public int getDamage(){
        return this.damage;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public int getDamageRate(){
        return this.damageRate;
    }

    public float getRange(){
        return this.damage;
    }

    public String getName(){
        return this.name;
    }

    public String getAttackSoundFilePath(){
        return this.attackSoundFilePath;
    }

    public String getDeathSoundFilePath(){
        return this.deathSoundFilePath;
    }

    public String getCharacterSpriteFilePath(){
        return this.characterSpriteFilePath;
    }

    public static List<Unit> getGlobalUnitList(){
        return Unit.globalUnits;
    }

    public List<? extends Unit> getUnitsInRange(int range, List<? extends Unit> globalUnits){
        return this.unitsInRange;
    }

    public Coordinates getUnitCoordinates(){
        return this.coordinates;
    }


    /*---------- Setters ---------- */

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }


    /*---------- Methods ---------- */
    public void attackUnitsInRange(List<? extends Unit> unitsInRange){
        System.out.println(unitsInRange);
        System.out.println(this.getCapacity());
    }

}
