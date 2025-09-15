package adventuregame.monster;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Coin_Bronze;
import adventuregame.objects.OBJ_Crystal;
import adventuregame.objects.OBJ_Heart;

import java.util.Random;

public class MON_Bat extends Entity {
    GamePanel gp;

    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 10;
        defense = 4;
        collision = true;
        exp = 5;
        shotInterval = 0;
        directionInterval = 10;
        distanceToChase = 0; // Distance before chasing
        rate = 100; // 1 = 100% 3 = 33%  5 = 20% 10 = 10%

        solidArea.x = 3; // goes n pixel in from the side
        solidArea.y = 15;// goes n pixel down from the top
        solidArea.width = 42; // the space left, (n + n) - 48  = x
        solidArea.height = 21; // the space left, n - n = x
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        getDyingImages();
    }
    public void getImage(){
        up1 = setup("/images/monster/bat-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/monster/bat-up2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/monster/bat-up2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/monster/bat-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/monster/bat-down2.png", gp.tileSize, gp.tileSize);
        downStill = setup("/images/monster/bat-down2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/monster/bat-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/monster/bat-left2.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/monster/bat-left2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/monster/bat-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/monster/bat-right2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/monster/bat-right2.png", gp.tileSize, gp.tileSize);
        dead = setup("/images/monster/bat-dead.png", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        if (onPath == true){
            checkStopChasingOrNot(gp.player, distanceToChase, rate); // CHECK IF STOP CHASING. Distance to play
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player)); // GOAL TO WALK
        } else {
            checkStartChasingOrNot(gp.player, distanceToChase, rate); // if player a within distance to monster start chasing
            getRandomDirection(directionInterval);  // GET a random direction if not chasing
        }
    }
    public void damageReaction() {
        actionLockCounter = 0; // New pathfinder walk to the player
        onPath = true;
    }
    public void checkDrop(){
        int i = new Random().nextInt(100)+1; // CAST A DIE
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

