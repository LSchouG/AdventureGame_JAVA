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
    public static final String objName = "Common Key";

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_consumable;
        lockKeyType = "common";
        stackable = true;
        down1 = setup("/images/objects_pickup/key.png", gp.tileSize, gp.tileSize);
        itemDescription = "A common key.";
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "You use the " + name + " and open the door";

        dialogues[1][0]  = "The key can be used on a door";
    }
    public boolean use(Entity entity){

        int objIndex = getDetected(entity, gp.obj, "Door");

        if(objIndex != 999){
            startDialogue(this,0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            startDialogue(this,1);
            return false;
        }
    }
}
