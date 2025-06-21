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

package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject {

    GamePanel gp;

    /**************************************************************************
     * Constructor: OBJ_Boots(GamePanel gp)
     * Purpose: Initializes the Boots object, sets name and loads sprite.
     ***************************************************************************/
    public OBJ_Boots(GamePanel gp) {
        this.gp = gp;
        name = "Boots";

        try {
            // Load the boots image
            image1 = ImageIO.read(getClass().getResourceAsStream("/images/objects/boots.png"));

            // Scale the image to tile size
            image1 = uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace(); // Output error to console for debugging
        }
    }
}
