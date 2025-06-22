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
package dev.adventuregame.objects;
import dev.adventuregame.GamePanel;
import dev.adventuregame.entity.Entity;

public class OBJ_Door extends Entity {
    public OBJ_Door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setup("/images/objects/door.png");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
