/** ******************************************************************************
 * FileName: UtilityTool.java
 * Purpose: Provides utility functions for graphics manipulation.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Currently includes image scaling utility
 * - Can be extended for other image/graphics-related utilities
 *******************************************************************************/

package adventuregame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    /**************************************************************************
     * Method: scaleImage(BufferedImage original, int width, int height)
     * Purpose: Scales a BufferedImage to a given width and height.
     * Inputs:
     *   - original: the original BufferedImage
     *   - width: target width
     *   - height: target height
     * Output:
     *   - Scaled BufferedImage matching target dimensions
     ***************************************************************************/
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();

        // Draw the original image into the new scaled image
        g2.drawImage(original, 0, 0, width, height, null);

        g2.dispose(); // Clean up graphics resources

        return scaledImage;
    }
}
