package adventuregame.objects;

import adventuregame.entity.Entity;

public class OBJ_Iron_Shield extends Entity{
     public OBJ_Iron_Shield(adventuregame.GamePanel gp){
         super(gp);

         name = "Iron Shield";
         type = type_shield;
         down1 = setup("/images/objects/Shield_Iron.png", gp.tileSize, gp.tileSize);
         defenseValue = 2;
         attackArea.width = 36;
         attackArea.height = 36;
         itemDescription = "[" + name + "]" + " \nAn Iron shield. \nDefense Value: " + defenseValue ;
     }

}
