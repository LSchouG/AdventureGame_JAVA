/** ******************************************************************************
 * FileName: OBJ_Key.java
 * Purpose: Represents a collectible key object in the game world.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Extends SuperObject
 * - Loads key sprite and scales it to tile size
 * - Typically used to unlock doors or trigger events
 *******************************************************************************/
package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Key";
        type = type_consumable;
        lockKeyType = "common";
        stackable = true;
        down1 = setup("/images/objects_pickup/key.png", gp.tileSize, gp.tileSize);
        itemDescription = "A common key.";
    }
    public boolean use(Entity entity){

        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Door");

        System.out.println("using the key index is " + objIndex );
        if(objIndex != 999){
            gp.ui.currentDialogue = "You use the " + name + " and open the door";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "The key can be used on a door";
            return false;
        }
    }
}
