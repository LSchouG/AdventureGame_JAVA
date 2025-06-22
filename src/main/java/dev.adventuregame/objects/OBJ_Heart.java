package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;
import dev.adventuregame.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "heart";
        image1 = setup("/images/objects/fullHeart.png");
        image2 = setup("/images/objects/halfHeart.png");
        image3 = setup("/images/objects/blankHeart.png");
    }
}
