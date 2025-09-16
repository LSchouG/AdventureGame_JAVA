package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Axe extends Entity {

    public static final String objName = "Axe";

    public OBJ_Axe(GamePanel gp) {
        super(gp);


        name = objName;
        type = type_axe;
        down1 = setup("/images/objects_pickup/axe.png", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        Price = 10;
        knockBackPower = 5;
        motion1_duration = 10;
        motion2_duration = 35;
        itemTitle = "[" + name + "]";
        itemDescription = "An old Axe." + "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
    }
}
