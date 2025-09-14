package adventuregame.entity;

import adventuregame.GamePanel;

import java.util.Random;

public class NPC_GirlYellow  extends Entity{

    GamePanel gp;

    public NPC_GirlYellow(GamePanel gp) {
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
        downStill = setup("/images/npc/girl-yellow-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/girl-yellow-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/girl-yellow-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/girl-yellow-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/girl-yellow-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/girl-yellow-up2.png", gp.tileSize, gp.tileSize);
        leftStill =setup("/images/npc/girl-yellow-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/girl-yellow-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/girl-yellow-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/girl-yellow-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/girl-yellow-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/girl-yellow-right2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {

        dialogues[0][0] = "Can it be... the hero I've prayed for? These goblins hauled me into their wretched lair against my will.";
        dialogues[0][1] = "Nights blurred into days of dread, their vicious hisses piercing the silence like daggers.";
        dialogues[0][2] = "Your sword has felled the fiends—liberty surges through me like a long-lost breath.";
        dialogues[0][3] = "Gratitude swells in my chest, valiant rescuer. You've wrested me from oblivion's grasp.";
        dialogues[0][4] = "Side by side, we quit this gloom.\nThe village's hearth fires summon us onward.";

        dialogues[1][0] = "The familiar scents of home revive me, noble hero—my appreciation defies expression.";
        dialogues[1][1] = "A wave of jubilation swept the crowd at our arrival; the specter of peril dissolves into memory.";
        dialogues[1][2] = "Beyond my release, you've rekindled trust and unity among our people.";
        dialogues[1][3] = "Until paths cross anew, sentinel of the helpless.\nLet your renown shine as an eternal beacon.";
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

