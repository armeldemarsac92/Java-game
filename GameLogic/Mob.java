package GameLogic;

public abstract class Mob extends Unit{

    /*---------- Attributes ---------- */

    protected int speed;
    protected int hp;


    /*---------- Constructor ---------- */



    /*---------- Getters ---------- */
    
    public int getSpeed(){
        return this.speed;
    }

    public int getHp(){
        return this.hp;
    }


    /*---------- Setters ---------- */
    
    public void setHp(int damage){
        int hpTemp = this.hp;

        if(hpTemp - damage < 0){
            hpTemp = 0;
        }
        else{
            hpTemp -= damage;
        }

        this.hp = hpTemp;
    }


    /*---------- Methods ---------- */
    public void Move(){
        // utilize object speed to transform the coordinates (using the setter)
    }


}
