/**
 * *****************************************************************************
 * FileName: NPC_OldMan.java
 * Purpose: Represents an NPC (non-player character) - Old Man.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Inherits from Entity
 * - Loads directional sprites for idle and walking animations
 * - Uses random movement behavior for idle wandering
 *******************************************************************************/

package adventuregame.entity;

import adventuregame.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    /**************************************************************************
     * Constructor: NPC_OldMan(GamePanel gp)
     * Purpose: Initializes the old man NPC with a default direction and speed.
     ***************************************************************************/
    public NPC_OldMan(GamePanel gp) {
        super(gp);
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

    }

    /**************************************************************************
     * Method: getImages()
     * Purpose: Loads all sprite images for this NPC (idle and walking).
     ***************************************************************************/
    public void getImages() {
        downStill = setup("/images/npc/old-man-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/old-man-down-1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/old-man-down-2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/old-man-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/old-man-up-1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/old-man-up-2.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/npc/old-man-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/old-man-left-1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/old-man-left-2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/old-man-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/old-man-right-1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/old-man-right2.png", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Hallo,  lad. ";
        dialogues[1] = "Uhh our villages is DOOMED \nA band of goblins have taken our children \nAnd all our treasures.";
        dialogues[2] = "I used to be strong enough to defend us \nBut now...  \nI'm just a old man";
        dialogues[3] = "If you save our children \nYou can keep the treasure.";
        dialogues[4] = "Well...\nGood luck to you young man.";
    }

    /**************************************************************************
     * Method: setAction()
     * Purpose: Defines simple idle movement using random direction changes.
     * Notes: Called by Entity's update() method.
     ***************************************************************************/
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter > 120) {
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
        // Do this charctar specific stuff here

        super.speak();
    }
}
