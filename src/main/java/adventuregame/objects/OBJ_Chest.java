/** ******************************************************************************
 * FileName: OBJ_Chest.java
 * Purpose: Represents a chest object in the game world.
 * Author: IT Student
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Extends SuperObject
 * - Sets image and enables collision detection
 *******************************************************************************/

package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Chest";
        image1 = setup("/images/objects_interactive/chest-closed.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_interactive/chest-open.png", gp.tileSize, gp.tileSize);
        down1 = image1;
        collision = true;
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void setLoot(Entity loot){
        this.loot = loot;
    }
    public void interact(){
        gp.gameState = gp.dialogueState;

        if(opened == false){
            gp.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("you open the chest and find a " + loot.name + "!");

            if (gp.player.canObtainItem(loot) == false){
                sb.append("\n... but you cannot carry any more!");
            }
            else {
                sb.append("\nYou obtain the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        } else{
            gp.ui.currentDialogue = "It's empty";
        }
    }
}
