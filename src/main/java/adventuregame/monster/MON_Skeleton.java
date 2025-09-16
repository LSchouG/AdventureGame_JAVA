package adventuregame.monster;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Coin_Bronze;
import adventuregame.objects.OBJ_Coin_Gold;
import adventuregame.objects.OBJ_Crystal;
import adventuregame.objects.OBJ_Heart;

import java.util.Random;

public class MON_Skeleton extends Entity {
    GamePanel gp;
    public static final String objName = "MON_Skeleton";

    public MON_Skeleton(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = objName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 15;
        life = maxLife;
        attack = 10;
        defense = 3;
        knockBackPower = 5;
        collision = true;
        exp = 10;
        shotInterval = 0;
        distanceToChase = 5;     // Distance before chasing
        rate = 2; // 1 = 100% 3 = 33%  5 = 20% 10 = 10%


        solidArea.x = 5; // goes 3 pixel in from the side
        solidArea.y = 4;// goes 3 pixel down from the top
        solidArea.width = 38; // the space left, (3 + 3) - 48 = 42
        solidArea.height = 44; // the space left, 48 - 18 = 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 40;
        attackArea.height = 40;
        motion1_duration = 40;
        motion2_duration = 75;

        getImage();
        getAttackImages();
    }
    public void getImage(){
        downStill = setup("/images/monster/skeleton-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/monster/skeleton-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/monster/skeleton-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/monster/skeleton-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/monster/skeleton-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/monster/skeleton-up2.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/monster/skeleton-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/monster/skeleton-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/monster/skeleton-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/monster/skeleton-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/monster/skeleton-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/monster/skeleton-right2.png", gp.tileSize, gp.tileSize);
        dead = setup("/images/monster/skeleton-dead.png", gp.tileSize, gp.tileSize);
    }
    public void getAttackImages() {
         // IF USING attack-SPECIFIC IMAGES
        attackUp1 = setup("/images/monster/skeleton-up-attack1.png", gp.tileSize, gp.tileSize *2);
        attackUp2 = setup("/images/monster/skeleton-up-attack2.png", gp.tileSize, gp.tileSize *2);
        attackDown1 = setup("/images//monster/skeleton-down-attack1.png", gp.tileSize, gp.tileSize *2);
        attackDown2 = setup("/images//monster/skeleton-down-attack2.png", gp.tileSize, gp.tileSize *2);
        attackRight1 = setup("/images/monster/skeleton-right-attack1.png", gp.tileSize *2, gp.tileSize);
        attackRight2 = setup("/images/monster/skeleton-right-attack2.png", gp.tileSize *2, gp.tileSize);
        attackLeft1 = setup("/images/monster/skeleton-left-attack1.png", gp.tileSize *2, gp.tileSize);
        attackLeft2 = setup("/images/monster/skeleton-left-attack2.png", gp.tileSize *2, gp.tileSize);
    }
    public void getDyingImages() {
        dead = setup("/images/player/skeleton-dead.png", gp.tileSize, gp.tileSize);
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
        if (attacking == false) {
            checkAttackOrNot(rate, (gp.tileSize*2)-(gp.tileSize/2)-10, gp.tileSize);
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
            dropItem(new OBJ_Coin_Gold(gp));
        } else if (i >= 50 && i < 75){
            if (gp.player.maxLife < 16){
                dropItem(new OBJ_Heart(gp));
            } else {
                dropItem(new OBJ_Coin_Gold(gp));
            }
        } else if (i >= 75){
            if(gp.player.maxMana < 16){
                dropItem(new OBJ_Crystal(gp));
            } else {
                dropItem(new OBJ_Coin_Gold(gp));
            }
        }
    }


}
