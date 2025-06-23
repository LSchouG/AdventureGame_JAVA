package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);

        name = "Wood Shield";
        down1 = setup("/images/objects/Shield_normal.png", gp.tileSize, gp.tileSize);
        defenseValue = 1;
    }
}
