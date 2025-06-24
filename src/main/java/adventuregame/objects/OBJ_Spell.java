package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Spell extends Entity {

    public OBJ_Spell(GamePanel gp) {
        super(gp);

        name = "Spell Book";
        type = type_spell;
        down1 = setup("/images/objects/Book.png", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 50;
        attackArea.height = 50;
        itemDescription = "[" + name + "]\nBook of basic fire magic." +
                "\nAttack Value: " + attackValue + "\nAttack range: " + attackArea.width;
    }
}
