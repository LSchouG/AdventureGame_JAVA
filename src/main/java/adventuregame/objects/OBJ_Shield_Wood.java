package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);

        name = "Shield";
        type = type_shield;
        down1 = setup("/images/objects/shield-wood.png", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        itemDescription = "[" + name + "]" + " \nAn old wooden shield. \nDefense Value: " + defenseValue ;
    }
}
