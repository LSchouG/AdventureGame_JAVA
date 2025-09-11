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
        shotInterval = 30;
        distanceToChase = 5;     // Distance before chasing
        rate = 2; // 1 = 100% 3 = 33%  5 = 20% 10 = 10%

        projectile = new OBJ_Rock_Projectile(gp);
        solidArea.x = 3; // goes 3 pixel in from the side
        solidArea.y = 18;// goes 3 pixel down from the top
        solidArea.width = 42; // the space left, (3 + 3) - 48 = 42
        solidArea.height = 30; // the space left, 48 - 18 = 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
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
    public void setAction(){
        if (onPath == true){
            // CHECK IF STOP CHASING. Distance to play
            checkStopChasingOrNot(gp.player, distanceToChase, rate);

            // GOAL TO WALK
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));

            // SIMPLE AI BEHAVIOR FOR ATTACK PROJECTILE
            checkShootOrNot(rate, shotInterval);
        }
        else
        {
            // if player a within distance to monster start chasing
            checkStartChasingOrNot(gp.player, distanceToChase, rate);

            // GET random direction if not chasing
            getRandomDirection();
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
