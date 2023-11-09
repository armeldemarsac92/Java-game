package GameLogic;

public class Castle {
    private int hp = 500;
    private String characterSpriteFilePath;
    private Coordinates coordinates;
    

    public Castle(){

    }

    public int getHp(){
        return this.hp;
    }

    public String getCharacterSpriteFilePath(){
        return this.characterSpriteFilePath;
    }
}
