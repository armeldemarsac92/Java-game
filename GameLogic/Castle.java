package GameLogic;

public class Castle {
    private int hp = 500;
    private String characterSpriteFilePath;
    private Coordinates coordinates;     
    

    public Castle(){
        this.coordinates.setXPos(30);
        this.coordinates.setYPos(1);
    }

    public int getHp(){
        return this.hp;
    }

    public String getCharacterSpriteFilePath(){
        return this.characterSpriteFilePath;
    }
}
