package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);


        name = "Axe";
        type = type_axe;
        down1 = setup("/images/objects_pickup/axe.png", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        Price = 10;
        knockBackPower = 100;
        motion1_duration = 10;
        motion2_duration = 35;
        itemTitle = "[" + name + "]";
        itemDescription = "An old Axe." +
                "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
    }
}
