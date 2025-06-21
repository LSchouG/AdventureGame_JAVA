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
import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {

    GamePanel gp;

    /**************************************************************************
     * Constructor: OBJ_Chest(GamePanel gp)
     * Purpose: Initializes the chest object, loads image, enables collision.
     ***************************************************************************/
    public OBJ_Chest(GamePanel gp) {
        this.gp = gp;
        name = "Chest";

        try {
            // Load chest image
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/chest.png"));

            // Scale to tile size
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace(); // Output error to console
        }

        // Enable collision for this object (player cannot walk through it)
        collision = true;
    }
}
