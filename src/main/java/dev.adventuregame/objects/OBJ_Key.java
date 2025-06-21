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
import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {

    GamePanel gp;

    /**************************************************************************
     * Constructor: OBJ_Key(GamePanel gp)
     * Purpose: Initializes key object and loads key image.
     ***************************************************************************/
    public OBJ_Key(GamePanel gp) {
        this.gp = gp;
        name = "Key";

        try {
            // Load the key image from resources
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/key.png"));

            // Scale the image to match tile size
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace(); // Output error to console if loading fails
        }
    }
}
