package GameLogic;

public class Tower extends Unit {

    /*---------- Attributes ---------- */

    private int price;

    // protected int damage;
    // protected int capacity;
    // protected int damageRate;

    // protected float range;

    // protected String name;
    // protected String attackSoundFilePath;
    // protected String deathSoundFilePath;
    // protected String characterSpriteFilePath;

    // protected static List<? extends Unit> globalUnits;
    // protected List<? extends Unit> unitsInRange;

    // protected Map<String, Integer> coordinates;

    /*---------- Constructor ---------- */
    public Tower(){
        this.setDamage(1);
        this.setCapacity(1);
        this.setDamageRate(1);
        this.setRange(2.0f);
        this.setName("Level_1");
        this.setCoordinates(new Coordinates(0, 2));

        System.out.println("----------- Tower instantiated -----------");
        System.out.println("Damage: " + this.getDamage());
        System.out.println("Capacity: " + this.getCapacity());
        System.out.println("Damage rate: " + this.getDamageRate());
        System.out.println("Range: " + this.getRange());
        System.out.println("Name: " + this.getName());
        System.out.println("Coordinates: " + this.getUnitCoordinates().getCoordinates());
    }


    /*---------- Getters ---------- */

    public int getPrice(){
        return this.price;
    }


    /*---------- Setters ---------- */

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setRange(float range){
        this.range = range;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void setDamageRate(int damageRate){
        this.damageRate = damageRate;
    }   

    public void setCharacterSpriteFilePath(String characterSpriteFilePath){
        this.characterSpriteFilePath = characterSpriteFilePath;
    }

    public void setAttackSoundFilePath(String attackSoundFilePath){
        this.attackSoundFilePath = attackSoundFilePath;
    }


    /*---------- Methods ---------- */
    
    public void upgrade(){
        // Set damage * 2 (until tower 2)
        // Set name
        // Set sprite
        // set capacity + 1 (until 2)
        // Set attackSound
        // set damageRate * 2 (until tower 2)
    }
}
