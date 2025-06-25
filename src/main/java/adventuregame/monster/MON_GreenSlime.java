package adventuregame.monster;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 5;
        life = maxLife;
        attack = 4;
        defense = 0;
        collision = true;
        exp = 2;

        solidArea.x = 3; // goes 3 pixel in from the side
        solidArea.y = 18;// goes 3 pixel down from the top
        solidArea.width = 42; // the space left, (3 + 3) - 48 = 42
        solidArea.height = 30; // the space left, 48 - 18 = 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){

        up1 = setup("/images/monster/green-slime-1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/monster/green-slime-2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/monster/green-slime-1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/monster/green-slime-2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/monster/green-slime-1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/monster/green-slime-2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/monster/green-slime-1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/monster/green-slime-2.png", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        actionLockCounter++;
        if (actionLockCounter > 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Generate number between 1–100

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    /**************************************************************************
     * Method: damageReaction()
     * Purpose:
     * Notes:
     ***************************************************************************/
    public void damageReaction() {

        actionLockCounter = 0;

        // Monster moves away from player when attacked
        //direction = gp.player.direction;

        //Monster move towards the player when attacked
        switch (direction){
            case "up":    direction = "down";  break;
            case "down":  direction = "up";    break;
            case "left":  direction = "right"; break;
            case "right": direction = "left";  break;
        }

    }

}
