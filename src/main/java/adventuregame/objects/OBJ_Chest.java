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

    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
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
    public void setDialogue() {
        dialogues[0][0] = "you open the chest and find a " + loot.name + "!";
        dialogues[0][1] = "... but you cannot carry any more!";

        dialogues[1][0]  = "you open the chest and find a " + loot.name + "!";
        dialogues[1][1]  = "You obtain the " + loot.name + "!";

        dialogues[2][0]  = "It's empty";
    }

    public void setLoot(Entity loot){
        this.loot = loot;
        setDialogue();
    }
    public void interact(){
        if(opened == false){
            gp.playSE(3);
            if (gp.player.canObtainItem(loot) == false){
                startDialogue(this,0);
            }
            else {
                startDialogue(this,1);
                down1 = image2;
                opened = true;
            }
        } else{
            startDialogue(this,2);
        }
    }
}
