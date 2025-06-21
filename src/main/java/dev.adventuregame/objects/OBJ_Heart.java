package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject {
  GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "heart";

        try {
            // Load the door image from resources
            image1 = ImageIO.read(getClass().getResourceAsStream("/images/objects/fullHeart.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/images/objects/halfHeart.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/images/objects/blankHeart.png"));
            // Scale the image to match tile size
            image1 = uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        } catch (
                IOException e) {
            e.printStackTrace(); // Output error to console for debugging
        }

    }
}
