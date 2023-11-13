package GameLogic;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class IceTower extends ATower { // Ice tower slows enemies down

    /*---------- Constructor ---------- */

    public IceTower(Coordinates coordinates){
        super(5, 5, 150, 1, coordinates);
        try {
            this.characterSpriteImage = ImageIO.read(new File("assets/tower_lvl0.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override 
    public <T> void attack(T unit){
        Mob castedUnit = (Mob) unit;
        
        if(castedUnit.isAlive()){
            castedUnit.setHp(this.getDamage());
            this.slowMob(castedUnit);
        }
        else{
            castedUnit.killInstance();
        }
    }

    public void slowMob(Mob mob){
        if(mob.getSpeed() > 10){
            try {
                TimeUnit.SECONDS.sleep(this.damageRate);
                mob.setSpeed(mob.getSpeed() / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
