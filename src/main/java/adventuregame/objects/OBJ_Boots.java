/** ******************************************************************************
 * FileName: OBJ_Boots.java
 * Purpose: Represents the "Boots" object in the game world.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Extends SuperObject
 * - Loads and scales the boots image used for rendering
 *******************************************************************************/

package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Boots extends Entity {
    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = "Boots";
        down1 = setup("/images/objects_pickup/boots.png", gp.tileSize, gp.tileSize);
        itemDescription = "[" + name + "]" + " \nA par of old boots.";
    }
}
