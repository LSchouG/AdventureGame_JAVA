package adventuregame.monster;

import adventuregame.GamePanel;
import adventuregame.data.Progress;
import adventuregame.entity.Entity;
import adventuregame.entity.PlayerDummy;
import adventuregame.objects.*;

import java.util.Random;

public class MON_BOSS extends Entity {
    GamePanel gp;
    public static final String monName = "Boss";

    public MON_BOSS(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        attack = 15;
        defense = 4;
        sleep = true;
        knockBackPower = 5;
        exp = 50;
        distanceToChase = 5;  // Distance before chasing
        rate = 2; // 1 = 100% 3 = 33%  5 = 20% 10 = 10%


        solidArea.x = gp.tileSize; // goes 3 pixel in from the side
        solidArea.y = gp.tileSize;// goes 3 pixel down from the top
        solidArea.width = (gp.tileSize*3); // the space left, (3 + 3) - 48 = 42
        solidArea.height = (gp.tileSize*4); // the space left, 48 - 18 = 30
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 150;
        attackArea.height = 150;
        motion1_duration = 45;
        motion2_duration = 100;

        getImage();
        getAttackImages();
        setDialogue();
    }
    public void setDialogue(){
            dialogues[0][0] = "You... have... finally... arrived... young... hero.\nThe pebbles in my joints a acing for battle...";
            dialogues[0][1] = "No... matter... how... hard... you... try... you... will... never... defeat... me.\nI am rock-solid. Get it? Rock? Solid? Ha... rumble.";
            dialogues[0][2] = "And... save... the... children?\nThey... polish... my... toes... now. With... tiny... brushes. Adorable... laborers.";
            dialogues[0][3] = "Also... the... treasure... is... mine... and... you... cannot... get... it.\nBuried... under... my... left... foot. Smells... like... wet... clay... and... fool's gold.";
            dialogues[0][4] = "You... will... die... here.\nCrushed... like... a... grape... in... a... landslide... of... Rocks...";
            dialogues[0][5] = "COME... AND... MEET... YOUR... DOOM!";

    }
    public void getImage(){

        int size = 5;
        if ( inRage == false){

            downStill = setup("/images/monster/boss-down-still.png", gp.tileSize*size, gp.tileSize*size);
            down1 = setup("/images/monster/boss-down1.png",gp.tileSize*size, gp.tileSize*size);
            down2 = setup("/images/monster/boss-down2.png", gp.tileSize*size, gp.tileSize*size);
            upStill = setup("/images/monster/boss-up-still.png",gp.tileSize*size, gp.tileSize*size);
            up1 = setup("/images/monster/boss-up1.png", gp.tileSize*size, gp.tileSize*size);
            up2 = setup("/images/monster/boss-up2.png", gp.tileSize*size, gp.tileSize*size);
            leftStill = setup("/images/monster/boss-left-still.png",gp.tileSize*size, gp.tileSize*size);
            left1 = setup("/images/monster/boss-left1.png", gp.tileSize*size, gp.tileSize*size);
            left2 = setup("/images/monster/boss-left2.png",gp.tileSize*size, gp.tileSize*size);
            rightStill = setup("/images/monster/boss-right-still.png",gp.tileSize*size, gp.tileSize*size);
            right1 = setup("/images/monster/boss-right1.png",gp.tileSize*size, gp.tileSize*size);
            right2 = setup("/images/monster/boss-right2.png",gp.tileSize*size, gp.tileSize*size);
            //dead = setup("/images/monster/boss-dead.png", gp.tileSize*size, gp.tileSize*size);
        }
        if (inRage == true){
            downStill = setup("/images/monster/boss-rage-down-still.png", gp.tileSize*size, gp.tileSize*size);
            down1 = setup("/images/monster/boss-rage-down1.png",gp.tileSize*size, gp.tileSize*size);
            down2 = setup("/images/monster/boss-rage-down2.png", gp.tileSize*size, gp.tileSize*size);
            upStill = setup("/images/monster/boss-up-still.png",gp.tileSize*size, gp.tileSize*size);
            up1 = setup("/images/monster/boss-up1.png", gp.tileSize*size, gp.tileSize*size);
            up2 = setup("/images/monster/boss-up2.png", gp.tileSize*size, gp.tileSize*size);
            leftStill = setup("/images/monster/boss-rage-left-still.png",gp.tileSize*size, gp.tileSize*size);
            left1 = setup("/images/monster/boss-rage-left1.png", gp.tileSize*size, gp.tileSize*size);
            left2 = setup("/images/monster/boss-rage-left2.png",gp.tileSize*size, gp.tileSize*size);
            rightStill = setup("/images/monster/boss-rage-right-still.png",gp.tileSize*size, gp.tileSize*size);
            right1 = setup("/images/monster/boss-rage-right1.png",gp.tileSize*size, gp.tileSize*size);
            right2 = setup("/images/monster/boss-rage-right2.png",gp.tileSize*size, gp.tileSize*size);
            //dead = setup("/images/monster/boss-dead.png", gp.tileSize*size, gp.tileSize*size);
        }

    }
    public void getAttackImages() {
        int size = 5;
        if ( inRage == false){
            attackUp1 = setup("/images/monster/boss-up-attack1.png", gp.tileSize*size, gp.tileSize*2*size);
            attackUp2 = setup("/images/monster/boss-up-attack2.png",gp.tileSize*size, gp.tileSize*2*size);
            attackDown1 = setup("/images//monster/boss-down-attack1.png",gp.tileSize*size, gp.tileSize*2*size);
            attackDown2 = setup("/images//monster/boss-down-attack2.png",gp.tileSize*size, gp.tileSize*2*size);
            attackRight1 = setup("/images/monster/boss-right-attack1.png", gp.tileSize*2*size, gp.tileSize*size);
            attackRight2 = setup("/images/monster/boss-right-attack2.png", gp.tileSize*2*size, gp.tileSize*size);
            attackLeft1 = setup("/images/monster/boss-left-attack1.png", gp.tileSize*2*size, gp.tileSize*size);
            attackLeft2 = setup("/images/monster/boss-left-attack2.png", gp.tileSize*2*size, gp.tileSize*size);
        }
        if (inRage == true){
            attackUp1 = setup("/images/monster/boss-up-attack1.png", gp.tileSize*size, gp.tileSize*2*size);
            attackUp2 = setup("/images/monster/boss-up-attack2.png",gp.tileSize*size, gp.tileSize*2*size);
            attackDown1 = setup("/images//monster/boss-rage-down-attack1.png",gp.tileSize*size, gp.tileSize*2*size);
            attackDown2 = setup("/images//monster/boss-rage-down-attack2.png",gp.tileSize*size, gp.tileSize*2*size);
            attackRight1 = setup("/images/monster/boss-rage-right-attack1.png", gp.tileSize*2*size, gp.tileSize*size);
            attackRight2 = setup("/images/monster/boss-rage-right-attack2.png", gp.tileSize*2*size, gp.tileSize*size);
            attackLeft1 = setup("/images/monster/boss-rage-left-attack1.png", gp.tileSize*2*size, gp.tileSize*size);
            attackLeft2 = setup("/images/monster/boss-rage-left-attack2.png", gp.tileSize*2*size, gp.tileSize*size);
        }
    }
    public void setAction(){

        if (inRage == false &&life < (maxLife/2)){
            inRage = true;
            getImage();
            getAttackImages();
            defaultSpeed += 2;
            speed = defaultSpeed;
            attack *= 2;
            motion1_duration = motion1_duration - motion1_duration/2;
            motion2_duration = motion2_duration - motion2_duration/2;
        }
        if (getTileDistance(gp.player) < distanceToChase){
            moveTowardPlayer(60);
        }
        else
        {
            getRandomDirection(directionInterval);
        }
        if (attacking == false) {
            checkAttackOrNot(60, gp.tileSize*6, gp.tileSize*3);
        }
    }
    public void damageReaction() {
        // New pathfinder walk to the player
        actionLockCounter = 0;
    }
    public void checkDrop(){

        gp.bossBattleOn = false;
        Progress.bossDeleated = true;

        // Restore the dungeon music
        gp.stopMusic();
        gp.playMusic(20);

        // Search for the doors and delete
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_IronDoor.objName)) {
               gp.playSE(21);
               gp.obj[gp.currentMap][i] = null;
            }
        }

        // CAST A DIE

            dropItem(new OBJ_TownChest(gp));

    }
}

