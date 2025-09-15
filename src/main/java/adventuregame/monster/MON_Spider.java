package adventuregame.monster;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Coin_Bronze;
import adventuregame.objects.OBJ_Crystal;
import adventuregame.objects.OBJ_Heart;

import java.util.Random;

public class MON_Spider extends Entity {

    public MON_Spider(GamePanel gp) {
        super(gp);
        type = type_monster;
        name = "Bat";
        speed = 3;
        maxLife = 15;
        life = maxLife;
        attack = 12;
        defense = 5;
        collision = true;
        exp = 6;
        shotInterval = 0;
        directionInterval = 10;
        distanceToChase = 5; // Distance before chasing
        rate = 2; // 1 = 100% 3 = 33%  5 = 20% 10 = 10%

        solidArea.x = 4; // goes n pixel in from the side
        solidArea.y = 6;// goes n pixel down from the top
        solidArea.width = 34; // the space left, (n + n) - 48  = x
        solidArea.height = 34; // the space left, n - n = x
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){

        up1 = setup("/images/monster/spider-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/monster/spider-up2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/monster/spider-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/monster/spider-down2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/monster/spider-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/monster/spider-left2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/monster/spider-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/monster/spider-right2.png", gp.tileSize, gp.tileSize);
        dead = setup("/images/monster/spider-dead.png", gp.tileSize, gp.tileSize);
    }
    public void getDyingImages() {
        dead = setup("/images/player/spider-dead.png", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        if (onPath == true){
            // CHECK IF STOP CHASING. Distance to play
            checkStopChasingOrNot(gp.player, distanceToChase, rate);
            // GOAL TO WALK
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
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

