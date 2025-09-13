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

    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = objName;
        Price = 10;
        down1 = setup("/images/objects_pickup/boots.png", gp.tileSize, gp.tileSize);
        speed = 1;
        itemTitle = "[" + name + "]";
        itemDescription = "A par of old boots. \nSpeed Value: " + speed ;
    }
}
