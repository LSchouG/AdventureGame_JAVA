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
        name = "Key";
        type = type_consumable;
        lockKeyType = "common";
        down1 = setup("/images/objects_pickup/key.png", gp.tileSize, gp.tileSize);
        itemDescription = "[" + name + "]\nA common key.";
    }
}
