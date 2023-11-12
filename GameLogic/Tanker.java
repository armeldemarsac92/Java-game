package GameLogic;

public class Tanker extends Mob {
    /*---------- Constructor ---------- */
    public Tanker(Coordinates coordinates){
        super(1, 100, 5, 1, 1, 1, 10, coordinates);

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
