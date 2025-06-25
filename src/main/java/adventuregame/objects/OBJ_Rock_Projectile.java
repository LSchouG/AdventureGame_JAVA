package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Projectile;

public class OBJ_Rock_Projectile extends Projectile {

    GamePanel gp;
    public OBJ_Rock_Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
/** OLD WAY OF FIREBALL
        name = "FireBall";
        type = type_spell;
        down1 = setup("/images/objects/bbook.png", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 50;
        attackArea.height = 50;
        itemDescription = "[" + name + "]\nBook of basic fire magic." +
                "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
 **/

// NEW WAY OF FIREBALL
        name = "Rock";
        speed = 8;
        maxLife = 20;
        life = maxLife;
        attack = 2;
        manaUse = 1;
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
}
