package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Sword_Normal extends Entity {

   public OBJ_Sword_Normal(GamePanel gp) {
       super(gp);

       name = "Sword Normal";
       down1 = setup("/images/objects/sword_normal.png", gp.tileSize, gp.tileSize);
       attackValue = 4;
   }
}
