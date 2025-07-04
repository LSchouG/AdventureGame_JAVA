package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);


        name = "Axe";
        type = type_axe;
        down1 = setup("/images/objects/axe.png", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        itemDescription = "[" + name + "]" + "\nAn old Axe." +
                "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
    }
}
