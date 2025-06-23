package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Spell extends Entity {

    public OBJ_Spell(GamePanel gp) {
        super(gp);

        name = "Fire Spell";
        down1 = setup("/images/objects/Book.png", gp.tileSize, gp.tileSize);
        magicValue = 1;
    }
}
