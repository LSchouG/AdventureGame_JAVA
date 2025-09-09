package adventuregame.monster;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Coin_Bronze;
import adventuregame.objects.OBJ_Crystal;
import adventuregame.objects.OBJ_Heart;
import adventuregame.objects.OBJ_Rock_Projectile;

import java.util.Random;

public class MON_RedSlime extends Entity {
    public MON_RedSlime(GamePanel gp) {
        super(gp);
        type = type_monster;
        name = "Red Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 5;
        defense = 0;
        collision = true;
        exp = 4;
        projectile = new OBJ_Rock_Projectile(gp);

        solidArea.x = 3; // goes 3 pixel in from the side
        solidArea.y = 18;// goes 3 pixel down from the top
        solidArea.width = 42; // the space left, (3 + 3) - 48 = 42
        solidArea.height = 30; // the space left, 48 - 18 = 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    /**************************************************************************
     * Method: damageReaction()
     * Purpose:
     * Notes:
     ***************************************************************************/
    public void getImage(){

        up1 = setup("/images/monster/red-slime-1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/monster/red-slime-2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/monster/red-slime-1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/monster/red-slime-2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/monster/red-slime-1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/monster/red-slime-2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/monster/red-slime-1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/monster/red-slime-2.png", gp.tileSize, gp.tileSize);
    }
    /**************************************************************************
     * Method: damageReaction()
     * Purpose:
     * Notes:
     ***************************************************************************/
    public void setAction(){
        if (onPath == true){
            // GOAL TO WALK
            //int goalCol = 67; Walk strait to the goal
            //int goalRow = 23; Walk strait to the goal
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize; // player follow
            int goalRow = (gp.player.worldY + gp.player.solidArea.x) / gp.tileSize; // player follow
            // SIMPLE AI BEHAVIOR FOR ATTACK PROJECTILE
            int i = new Random().nextInt(200)+1;
            if (i > 197 && projectile.alive == false && shotAvailableCounter > 30){

                projectile.set(worldX, worldY, direction, true, this);
                //gp.projectileList.add(projectile);
                // ADD PROJECTILES TO LIST
                for (int j = 0; j < gp.projectile[1].length; j++) {
                    if (gp.projectile[gp.currentMap][j] == null) {
                        gp.projectile[gp.currentMap][j] = projectile;
                        break;
                    }
                }
                shotAvailableCounter = 0;
            }
            searchPath(goalCol,goalRow);

        } else {
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
    }
    public void update(){
        super.update();

        // calculate the distance from monster to player
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;

        if (onPath == false && tileDistance < 5){
            // adding a bit random here too
            int i = new Random().nextInt(100)+1;
            if(i > 50)
            {
                onPath = true;
            }
        }
        if(onPath == true && tileDistance > 20){
            onPath = false;
        }
    }
    public void damageReaction() {
        /* Monster moves away from player when attacked
        //direction = gp.player.direction;
        //Monster move towards the player when attacked
        switch (direction){
            case "up":    direction = "down";  break;
            case "down":  direction = "up";    break;
            case "left":  direction = "right"; break;
            case "right": direction = "left";  break;
        }
        */

        // New pathfinder walk to the player
        actionLockCounter = 0;
        onPath = true;
    }
    public void checkDrop(){
        // CAST A DIE
        int i = new Random().nextInt(100)+1;

        // SET THE MONSTER DROP
        if (i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        } else if (i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        } else if (i >= 75){
            dropItem(new OBJ_Crystal(gp));
        }
    }

}
