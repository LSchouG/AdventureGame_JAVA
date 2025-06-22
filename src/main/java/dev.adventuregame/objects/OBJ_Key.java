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
package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;
import dev.adventuregame.entity.Entity;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/images/objects/key.png", gp.tileSize, gp.tileSize);
    }
}
