package adventuregame.entity;

import adventuregame.GamePanel;

import java.util.Random;

public class NPC_GirlBlue extends Entity{

    GamePanel gp;

    public static final  String  npcName = "Blue Girl";

    public NPC_GirlBlue(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = npcName;
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
        downStill = setup("/images/npc/girl-blue-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/girl-blue-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/girl-blue-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/girl-blue-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/girl-blue-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/girl-blue-up2.png", gp.tileSize, gp.tileSize);
        leftStill =setup("/images/npc/girl-blue-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/girl-blue-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/girl-blue-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/girl-blue-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/girl-blue-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/girl-blue-right2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {

        dialogues[0][0] = "Hero... is that really you?\nThe goblins... they dragged me into this foul dungeon.";
        dialogues[0][1] = "I thought I'd never see the light again.\nTheir claws and snarls haunted my every moment.";
        dialogues[0][2] = "You've slain them all! My heart races with relief.";
        dialogues[0][3] = "Thank you, brave one. You've pulled me from the brink of despair.";
        dialogues[0][4] = "Let us flee this place together.\nThe village awaits our return.";

        dialogues[1][0] = "Oh, the sweet air of home! Hero, words fail to capture my gratitude.";
        dialogues[1][1] = "The villagers cheered as we entered the gates.\nNo longer do shadows of fear linger over our hearths.";
        dialogues[1][2] = "You've restored not just my freedom, but our entire world's hope.";
        dialogues[1][3] = "Take this amulet as a token of our thanks.\nIt will shield you in battles yet to come.";
        dialogues[1][4] = "Farewell for now, guardian of the innocent.\nMay your legend grow as brightly as the sun.";
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

