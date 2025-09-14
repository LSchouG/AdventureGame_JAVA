package adventuregame.entity;

import adventuregame.GamePanel;

import java.util.Random;

public class NPC_GirlGreen  extends Entity{

    GamePanel gp;

    public NPC_GirlGreen(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_npc;
        direction = "down"; // default facing direction
        speed = 1;           // slower than player
        solidArea.x = 0;// goes 0 pixel in from the side
        solidArea.y = 16;// goes 16 pixel down from the top
        solidArea.width = 48;// the space left, 48 - 0  = 48
        solidArea.height = 32;// the space left, 48 - 16 = 32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImages();         // load sprite images
        setDialogue();
        dialogueSet = -1;

    }
    public void getImages() {
        downStill = setup("/images/npc/girl-green-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/girl-green-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/girl-green-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/girl-green-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/girl-green-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/girl-green-up2.png", gp.tileSize, gp.tileSize);
        leftStill =setup("/images/npc/girl-green-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/girl-green-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/girl-green-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/girl-green-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/girl-green-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/girl-green-right2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {

        dialogues[0][0] = "Is it truly you, rescuer? This dank abyss has been my torment since the goblins seized me.";
        dialogues[0][1] = "Endless echoes of their guttural threats echoed in my ears, stealing my sleep and sanity.";
        dialogues[0][2] = "The last of them falls before your blade—such valor fills me with renewed vigor.";
        dialogues[0][3] = "I owe you my life, steadfast warrior. You've banished the darkness that nearly consumed me.";
        dialogues[0][4] = "Together, we shall ascend from these depths.\nThe open skies and our kin beckon us home.";


        dialogues[2][0] = "Home's embrace feels like a dream reborn, valiant hero—my thanks overflow like a spring.";
        dialogues[2][1] = "Cheers rose like a chorus as we crossed the threshold; dread's chill has fled our streets.";
        dialogues[2][2] = "Your deed has not only freed me but reignited the flame of security in our village.";
        dialogues[2][3] = "Wear this pendant with pride, a emblem of our profound appreciation.\nIt shall fortify your resolve against future foes.";
        dialogues[2][4] = "Part we must, defender of the vulnerable.\nMay your exploits illuminate the path for all who follow.";
    }
    public void setAction() {

        actionLockCounter++;
        if (actionLockCounter > 90) {
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
    public void speak() {
        facePlayer();

        startDialogue(this, dialogueSet);

        // Change the Dialogue Change later if want different logic
        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;
        }
    }
}

