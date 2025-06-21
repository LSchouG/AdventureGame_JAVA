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
import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject {

    GamePanel gp;

    /**************************************************************************
     * Constructor: OBJ_Door(GamePanel gp)
     * Purpose: Initializes door object with image and collision.
     ***************************************************************************/
    public OBJ_Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";

        try {
            // Load the door image from resources
            image1 = ImageIO.read(getClass().getResourceAsStream("/images/objects/door.png"));

            // Scale the image to match tile size
            image1 = uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace(); // Output error to console for debugging
        }

        // Enable collision so the player cannot walk through it
        collision = true;
    }
}
