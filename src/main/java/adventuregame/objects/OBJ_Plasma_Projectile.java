package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.entity.Projectile;

import java.awt.*;

public class OBJ_Plasma_Projectile extends Projectile {
    GamePanel gp;

    public static final String objName = "Plasma";

    public OBJ_Plasma_Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 10;
        maxLife = 25;
        life = maxLife;
        attack = 5;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        down1 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/attack/plasma-projectile.png", gp.tileSize, gp.tileSize);

    }
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user){
        //gp.player.mana -= useCost;
    }
    public Color getParticalColor(){
        Color color = new Color(65,50,30);
        return color;
    }
    public int getParticleSize(){
        int size = 8;
        return size;
    }
    public int getParticleSpeed(){
        return this.speed;
    }
    public int getParticleMaxLife(){
        return this.maxLife;
    }
}

