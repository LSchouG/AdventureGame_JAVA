package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public static final String objName = "Wooden Shield";

    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);

        name = objName;
        type = type_shield;
        down1 = setup("/images/objects_pickup/shield-wood.png", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        Price = 10;
        itemTitle = "[" + name + "]";
        itemDescription = "An old wooden shield. \nDefense Value: " + defenseValue ;
    }
}
