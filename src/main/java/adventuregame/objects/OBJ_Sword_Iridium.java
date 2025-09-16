package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Sword_Iridium extends Entity {

    public static final String objName =  "Iridium Sword";

    public OBJ_Sword_Iridium(GamePanel gp) {
        super(gp);

        name = objName;
        type = type_sword;
        down1 = setup("/images/objects_pickup/sword-iridium.png", gp.tileSize, gp.tileSize);
        attackValue = 5;
        attackArea.width = 44;
        attackArea.height = 44;
        Price = 200;
        knockBackPower = 15;
        motion1_duration = 5;
        motion2_duration = 20;
        itemTitle = "[" + name + "]";
        itemDescription = "An Iridium Sword." +
                "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;

    }
}

