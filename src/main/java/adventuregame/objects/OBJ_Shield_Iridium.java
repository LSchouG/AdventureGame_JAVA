package adventuregame.objects;

import adventuregame.entity.Entity;

public class OBJ_Shield_Iridium extends Entity {

    public static final String objName =  "Iridium Shield";

    public OBJ_Shield_Iridium (adventuregame.GamePanel gp){
        super(gp);

        name = objName;
        type = type_shield;
        down1 = setup("/images/objects_pickup/shield-iron.png", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        attackArea.width = 36;
        attackArea.height = 36;
        Price = 10;
        knockBackPower = 3;
        stackable = false;
        itemTitle = "[" + name + "]";
        itemDescription = "An Iridium shield. \nDefense Value: " + defenseValue ;
    }
}

