package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.entity.Projectile;

import java.awt.*;

public class OBJ_Rock_Projectile extends Projectile {

    GamePanel gp;
    public OBJ_Rock_Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Rock";
        speed = 8;
        maxLife = 20;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){

        down1 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/attack/rock-projectile.png", gp.tileSize, gp.tileSize);

    }
    public boolean haveResource(Entity user){

        boolean haveResource = false;

        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user){
        gp.player.mana -= useCost;
    }
    public Color getParticalColor(){
        Color color = new Color(65,50,30);
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
