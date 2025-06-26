package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Crystal extends Entity {
    GamePanel gp;
    public OBJ_Crystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Crystal";
        image1 = setup("/images/objects/blank-crystal.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects/half-crystal.png", gp.tileSize, gp.tileSize);
        image3 = setup("/images/objects/full-crystal.png", gp.tileSize, gp.tileSize);
    }
}
