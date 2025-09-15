package adventuregame.entity;

import adventuregame.GamePanel;

import java.util.Random;

public class NPC_GirlRed  extends Entity{

    GamePanel gp;

    public static final  String  npcName = "Red Girl";

    public NPC_GirlRed(GamePanel gp) {
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
        downStill = setup("/images/npc/girl-red-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/girl-red-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/girl-red-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/girl-red-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/girl-red-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/girl-red-up2.png", gp.tileSize, gp.tileSize);
        leftStill =setup("/images/npc/girl-red-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/girl-red-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/girl-red-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/girl-red-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/girl-red-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/girl-red-right2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {

        dialogues[0][0] = "Stranger... could it be? You've come for me in this nightmare of stone and shadow.";
        dialogues[0][1] = "The goblins' foul breath and chains bound my spirit as much as my body.";
        dialogues[0][2] = "Their horde lies broken at your feet—freedom tastes sweeter than I dreamed.";
        dialogues[0][3] = "My savior, your valor has shattered my prison of fear and isolation.";
        dialogues[0][4] = "Hand in hand, we escape to the world above.\nOur home calls us back to warmth.";

        dialogues[1][0] = "The village lights welcome us like old friends, hero—my soul sings with joy unspeakable.";
        dialogues[1][1] = "Tears of celebration flow from every face; the terror that gripped us has vanished.";
        dialogues[1][2] = "In rescuing me, you've mended the fractures in our community's heart.";
        dialogues[1][3] = "Go forth, noble protector of the lost.\nLet your deeds echo through the ages like thunder.";
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

