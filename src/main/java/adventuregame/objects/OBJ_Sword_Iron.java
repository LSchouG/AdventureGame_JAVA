package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Sword_Iron extends Entity {

    public static final String objName =  "Iron Sword";

    public OBJ_Sword_Iron(GamePanel gp) {
        super(gp);

        name = objName;
        type = type_sword;
        down1 = setup("/images/objects_pickup/sword-iron.png", gp.tileSize, gp.tileSize);
        attackValue = 3;
        attackArea.width = 40;
        attackArea.height = 40;
        Price = 50;
        knockBackPower = 10;
        motion1_duration = 5;
        motion2_duration = 20;
        itemTitle = "[" + name + "]";
        itemDescription = "An Iron Sword." +
                "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
    }
}


