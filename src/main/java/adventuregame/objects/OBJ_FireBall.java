package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.entity.Projectile;

import java.awt.*;

public class OBJ_FireBall extends Projectile {

    GamePanel gp;
    public OBJ_FireBall(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "FireBall";
        speed = 5;
        maxLife = 30;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){

        down1 = setup("/images/attack/fireball-down-1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/attack/fireball-down-1.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/attack/fireball-up-1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/attack/fireball-up-2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/attack/fireball-left-1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/attack/fireball-left-2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/attack/fireball-right-1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/attack/fireball-right-2.png", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Entity user){

        boolean haveResource = false;

        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user){
            gp.player.mana -= useCost;
    }
    public Color getParticalColor(){
        Color color = new Color(0xea481b);
        return color;
    }
    public int getParticalSize(){
        int size = 8;
        return size;
    }
    public int getParticalSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticalMaxLife(){
        int maxLife = 15;
        return maxLife;
    }

}
