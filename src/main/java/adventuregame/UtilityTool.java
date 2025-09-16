
package adventuregame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();

        // Draw the original image into the new scaled image
        g2.drawImage(original, 0, 0, width, height, null);

        g2.dispose(); // Clean up graphics resources

        return scaledImage;
    }
}
