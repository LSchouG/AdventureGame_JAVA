package adventuregame.monster;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Coin_Bronze;
import adventuregame.objects.OBJ_Crystal;
import adventuregame.objects.OBJ_Heart;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 3;
        life = maxLife;
        attack = 4;
        defense = 0;
        collision = true;
        exp = 2;
        shotInterval = 30;
        distanceToChase = 2; // Distance before chasing
        rate = 10; // 1 = 100% 3 = 33%  5 = 20% 10 = 10%

        solidArea.x = 3; // goes 3 pixel in from the side
        solidArea.y = 18;// goes 3 pixel down from the top
        solidArea.width = 42; // the space left, (3 + 3) - 48 = 42
        solidArea.height = 30; // the space left, 48 - 18 = 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        getDyingImages();
    }
    public void getImage(){

        up1 = setup("/images/monster/green-slime-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/monster/green-slime-up2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/monster/green-slime-up-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/monster/green-slime-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/monster/green-slime-down2.png", gp.tileSize, gp.tileSize);
        downStill = setup("/images/monster/green-slime-down-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/monster/green-slime-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/monster/green-slime-left2.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/monster/green-slime-left-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/monster/green-slime-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/monster/green-slime-right2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/monster/green-slime-right-still.png", gp.tileSize, gp.tileSize);
        dead = setup("/images/monster/green-slime-dead.png", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        if (onPath == true){
            // CHECK IF STOP CHASING. Distance to play
            checkStopChasingOrNot(gp.player, distanceToChase, rate);

            // GOAL TO WALK
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));

            // SIMPLE AI BEHAVIOR FOR ATTACK PROJECTILE
            //checkShootOrNot(chanceOfChasing, shotInterval);
        }
        else
        {
            // if player a within distance to monster start chasing
            checkStartChasingOrNot(gp.player, distanceToChase, rate);

            // GET a random direction if not chasing
            getRandomDirection(directionInterval);
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
           if (gp.player.maxLife < 8){
               dropItem(new OBJ_Heart(gp));
           } else {
               dropItem(new OBJ_Coin_Bronze(gp));
           }
        } else if (i >= 75){
            if(gp.player.maxMana <8){
                dropItem(new OBJ_Crystal(gp));
            } else {
                dropItem(new OBJ_Coin_Bronze(gp));
            }
        }
    }

}
