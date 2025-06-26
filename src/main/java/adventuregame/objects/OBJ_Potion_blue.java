package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Potion_blue extends Entity {
    GamePanel gp;

    public OBJ_Potion_blue(GamePanel gp){
    super(gp);
    this.gp = gp;

        name = "Blue Potion";
        type = type_consumable;
        restoreValue = 2;
        image1 = setup("/images/objects/small-blue-potion.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects/big-blue-potion.png", gp.tileSize, gp.tileSize);
        itemDescription = "[" + name + "]" + " \nA mana restoring potion. \nRestores " + restoreValue + " HP.";
    }
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;

        if (gp.player.mana < gp.player.maxMana) {
            String text;
            Random random = new Random();
            int i = random.nextInt(100) + 1;  // 1–100

            if (i <= 20) {
                text = "You down the " + name + " like it's happy hour at the wizard's tavern.\nTastes like blueberry... and bad decisions.\n+" + restoreValue + " Mana.";
            } else if (i <= 40) {
                text = "You sip the " + name + " with dramatic flair.\nA nearby squirrel claps. +" + restoreValue + " Mana.";
            } else if (i <= 60) {
                text = "The " + name + " fizzes violently as you drink it.\nProbably fine. +" + restoreValue + " Mana.";
            } else if (i <= 80) {
                text = "You chug the " + name + " and feel a magical burp rising.\nMana +"+ restoreValue +", dignity -1.";
            } else {
                text = "You drink the " + name + " carefully.\nIt tastes like liquid arcane taxes. +" + restoreValue + " Mana.";
            }

            if (i == 100) {
                text = "You drink the " + name + " and briefly glimpse the mana gods.\nThey wink at you.\n+" + restoreValue + " Mana.";
            }

            gp.ui.currentDialogue = text;
            entity.mana += restoreValue;

            if (gp.player.mana > gp.player.maxMana) {
                gp.player.mana = gp.player.maxMana;
            }

            gp.playSE(1);
        } else {
            gp.ui.currentDialogue = "You're already at full Mana.\nSave the juice for a real magical emergency.";
        }
    }
}