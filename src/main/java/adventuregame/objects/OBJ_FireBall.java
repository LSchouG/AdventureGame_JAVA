package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Projectile;

public class OBJ_FireBall extends Projectile {

    GamePanel gp;
    public OBJ_FireBall(GamePanel gp) {
        super(gp);
/** OLD WAY OF FIREBALL
        name = "FireBall";
        type = type_spell;
        down1 = setup("/images/objects/Book.png", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 50;
        attackArea.height = 50;
        itemDescription = "[" + name + "]\nBook of basic fire magic." +
                "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
 **/

// NEW WAY OF FIREBALL
        name = "FireBall";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        manaUse = 1;
        alive = false;
        getImage();
    }

    public void getImage(){

        down1 = setup("/images/attack/FireBallDown1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/attack/FireBallDown1.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/attack/FireBallUp1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/attack/FireBallUp2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/attack/FireBallLeft1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/attack/FireBallLeft2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/attack/FireBallRight1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/attack/FireBallRight2.png", gp.tileSize, gp.tileSize);
    }
}
