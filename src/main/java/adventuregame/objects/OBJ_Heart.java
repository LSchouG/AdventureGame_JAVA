package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "heart";
        image1 = setup("/images/objects/fullHeart.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects/halfHeart.png", gp.tileSize, gp.tileSize);
        image3 = setup("/images/objects/blankHeart.png", gp.tileSize, gp.tileSize);
    }
}
