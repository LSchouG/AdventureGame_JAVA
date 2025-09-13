package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Sword_Normal extends Entity {

    public static final String objName =  "Iron Sword";

   public OBJ_Sword_Normal(GamePanel gp) {
       super(gp);

       name = objName;
       type = type_sword;
       down1 = setup("/images/objects_pickup/sword-old.png", gp.tileSize, gp.tileSize);
       attackValue = 1;
       attackArea.width = 36;
       attackArea.height = 36;
       Price = 10;
       knockBackPower = 10;
       motion1_duration = 5;
       motion2_duration = 20;
       itemTitle = "[" + name + "]";
       itemDescription = "An old sword." +
               "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;

   }
}
