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

package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;
import dev.adventuregame.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        down1 = setup("/images/objects/chest.png");
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
