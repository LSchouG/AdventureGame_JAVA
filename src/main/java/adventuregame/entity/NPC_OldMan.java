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
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        type = type_npc;
        direction = "down"; // default facing direction
        speed = 3;           // slower than player
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

        dialogues[0][0] = "Greetings, young adventurer!";
        dialogues[0][1] = "Dark times have fallen upon us.\nA band of goblins has stolen our children\nand stripped us of all our treasures.";
        dialogues[0][2] = "Once, I was strong enough to defend this village...\nBut now, I am only an old man.";
        dialogues[0][3] = "If you can bring our children home,\nthe treasure is yours to keep.";
        dialogues[0][4] = "Rest in your bed whenever you must.\nIt will restore your strength and save your journey...";
        dialogues[0][5] = "But beware, lad...\nWhen you rest, the monsters regain their strength as well.";
        dialogues[0][6] = "Go now, brave one...\nMay fortune guide your path.";

        dialogues[1][0] = "Listen well, adventurer!";
        dialogues[1][1] = "You cannot reach the wizard across the waters as you are now.";
        dialogues[1][2] = "Seek out the magic boots, hidden deep in the forest.";
        dialogues[1][3] = "They will grant you the power to cross rivers and seas\nand reach the island where the wizard dwells.";
        dialogues[1][4] = "Without them, your journey cannot continue.";
    }
    public void setAction() {

        if (onPath == true){
           // GOAL TO WALK
            //int goalCol = 67; Walk strait to the goal
            //int goalRow = 23; Walk strait to the goal
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize; // player follow
            int goalRow = (gp.player.worldY + gp.player.solidArea.x) / gp.tileSize; // player follow
            searchPath(goalCol,goalRow);
        } else {
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
