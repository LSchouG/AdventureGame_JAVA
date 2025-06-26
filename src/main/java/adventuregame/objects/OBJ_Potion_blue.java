package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Potion_blue extends Entity {
    GamePanel gp;

    public OBJ_Potion_blue(GamePanel gp){
    super(gp);
    this.gp = gp;

        name = "Blue Potion";
        type = type_consumable;
        restoreValue = 5;
        image1 = setup("/images/objects/small-blue-potion.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects/big-blue-potion.png", gp.tileSize, gp.tileSize);
        itemDescription = "[" + name + "]" + " \nA mana restoring potion. \nRestores " + restoreValue + " HP.";
    }
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;

        if (gp.player.mana < gp.player.maxMana) {
            gp.ui.currentDialogue = "You down the " + name + " like it’s happy hour at the wizard’s tavern.\n" +
                    "Tastes like blueberry... and poor life choices. Restores +" + restoreValue + " Mana.";
            entity.mana += restoreValue;

            if (gp.player.mana > gp.player.maxMana) {
                gp.player.mana = gp.player.maxMana;
            }

            gp.playSE(1);
        } else {
            gp.ui.currentDialogue = "You're already at full Mana.\nSave it for when things get spicy.";
        }
    }


}
