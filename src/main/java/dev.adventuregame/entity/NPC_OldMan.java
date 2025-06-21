/** ******************************************************************************
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

package dev.adventuregame.entity;

import dev.adventuregame.GamePanel;
import java.util.Random;

public class NPC_OldMan extends Entity {

    /**************************************************************************
     * Constructor: NPC_OldMan(GamePanel gp)
     * Purpose: Initializes the old man NPC with a default direction and speed.
     ***************************************************************************/
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down"; // default facing direction
        speed = 1;           // slower than player
        getImages();         // load sprite images
    }

    /**************************************************************************
     * Method: getImages()
     * Purpose: Loads all sprite images for this NPC (idle and walking).
     ***************************************************************************/
    public void getImages() {
        downStill  = setup("/images/npc/oldManDownStill.png");
        down1      = setup("/images/npc/oldManDown1.png");
        down2      = setup("/images/npc/oldManDown2.png");
        upStill    = setup("/images/npc/oldManUpStill.png");
        up1        = setup("/images/npc/oldManUp1.png");
        up2        = setup("/images/npc/oldManUp2.png");
        leftStill  = setup("/images/npc/oldManLeftStill.png");
        left1      = setup("/images/npc/oldManLeft1.png");
        left2      = setup("/images/npc/oldManLeft2.png");
        rightStill = setup("/images/npc/oldManRightStill.png");
        right1     = setup("/images/npc/oldManRight1.png");
        right2     = setup("/images/npc/oldManRight2.png");
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
}
