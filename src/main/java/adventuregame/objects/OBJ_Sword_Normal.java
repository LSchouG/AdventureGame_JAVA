package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Sword_Normal extends Entity {

   public OBJ_Sword_Normal(GamePanel gp) {
       super(gp);

       name = "Sword";
       type = type_sword;
       down1 = setup("/images/objects/sword_normal.png", gp.tileSize, gp.tileSize);
       attackValue = 1;
       attackArea.width = 36;
       attackArea.height = 36;
       itemDescription = "[" + name + "]" + " \nAn old sword." +
               "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
   }
}
