package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        name = objName;
        type = type_pickaxe;
        down1 = setup("/images/objects_pickup/pick-axe.png", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 30;
        Price = 5;
        knockBackPower = 0;
        motion1_duration = 40;
        motion2_duration = 60;
        itemTitle = "[" + name + "]";
        itemDescription = "An old Pickaxe." + "\nIdeal for mining or breaking through weakened barriers.";
    }
}
