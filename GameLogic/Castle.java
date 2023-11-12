package GameLogic;

public class Castle extends Unit{
    private int hp = 500;
    private String characterSpriteFilePath = ".";
    private Coordinates coordinates = new Coordinates(1, 1);     
    
    public Castle(){
        super(20, 1, 100, 2, new Coordinates(1, 1));

        System.out.println("----------- Castle instantiated -----------");
        System.out.println("Hp :" + this.getHp());
        System.out.println("Character spriteFilePath: " + this.getCharacterSpriteFilePath());
        System.out.println("Coordinates :" + this.getCastleCoordinates().getCoordinates());
    }

    public int getHp(){
        return this.hp;
    }

    public String getCharacterSpriteFilePath(){
        return this.characterSpriteFilePath;
    }

    public Coordinates getCastleCoordinates(){
        return this.coordinates;
    }
}
