package GameLogic;

public class Tanker extends Mob {
    /*---------- Constructor ---------- */
    public Tanker(int speed, int hp, int damage, int damageRate, int range, int capacity, Coordinates coordinates){
        super(hp, speed, damage, damageRate, range, capacity, coordinates);

        System.out.println("----------- Tanker instantiated -----------");
        System.out.println("Id: " + this.getId());
        System.out.println("Hp: " + this.getHp());
        System.out.println("Speed: " + this.getSpeed());
        System.out.println("Damage: " + this.getDamage());
        System.out.println("Capacity: " + this.getCapacity());
        System.out.println("Damage rate: " + this.getDamageRate());
        System.out.println("Range: " + this.getRange());
        System.out.println("Coordinates: " + this.getUnitCoordinates().getCoordinates());
    }
}
