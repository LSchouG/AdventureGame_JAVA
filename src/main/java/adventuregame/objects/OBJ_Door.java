/** ******************************************************************************
 * FileName: OBJ_Door.java
 * Purpose: Represents a door object in the game world.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Extends SuperObject
 * - Loads door sprite and enables collision detection
 * - Can be used to block or unlock areas
 *******************************************************************************/
package adventuregame.objects;
import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Door extends Entity {
    public OBJ_Door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setup("/images/objects_interactive/door.png", gp.tileSize, gp.tileSize);
        collision = true;
        lockKeyType = "common";


        solidArea.x = 0;// goes 0 pixel in from the side
        solidArea.y = 16;// goes 16 pixel down from the top
        solidArea.width = 48;// the space left, 48 - 0  = 48
        solidArea.height = 32;// the space left, 48 - 16 = 32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
