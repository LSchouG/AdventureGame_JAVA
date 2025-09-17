package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Potion_BlueBig extends Entity {
    GamePanel gp;

    public static final String objName =  "Big Blue Potion";

    public OBJ_Potion_BlueBig(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_consumable;
        restoreValue = 10;
        Price = 40;
        stackable = true;
        down1 =setup("/images/objects_pickup/big-blue-potion.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/big-blue-potion.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "A Big mana restoring potion. \nRestores " + restoreValue + " HP.";
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "You down the " + name + " like it's happy hour at the wizard's tavern.\nTastes like blueberry... and bad decisions.\n+" + restoreValue + " Mana.";

        dialogues[1][0]  = "You sip the " + name + " with dramatic flair.\nA nearby squirrel claps. +" + restoreValue + " Mana.";

        dialogues[2][0] = "The " + name + " fizzes violently as you drink it.\nProbably fine. +" + restoreValue + " Mana.";

        dialogues[3][0] = "You chug the " + name + " and feel a magical burp rising.\nMana +"+ restoreValue +", dignity -1.";

        dialogues[4][0] ="You drink the " + name + " carefully.\nIt tastes like liquid arcane taxes. +" + restoreValue + " Mana.";

        dialogues[5][0] = "You drink the " + name + " and briefly glimpse the mana gods.\nThey wink at you.\n+" + restoreValue + " Mana.";

        dialogues[6][0] = "You're already at full Mana.\nSave the juice for a real magical emergency.";
    }
    public boolean use(Entity entity) {
        if (gp.player.life < gp.player.maxLife) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            int dialogueSet;

            if (i <= 20) {
                dialogueSet = 0;
            } else if (i <= 40) {
                dialogueSet = 1;
            } else if (i <= 60) {
                dialogueSet = 2;
            } else if (i <= 80) {
                dialogueSet = 3;
            } else if (i <= 99) {
                dialogueSet = 4;
            } else {  // i == 100
                dialogueSet = 5;
            }

            startDialogue(this, dialogueSet);

            entity.mana += restoreValue;
            if (gp.player.mana > gp.player.maxMana) {
                gp.player.mana = gp.player.maxMana;
            }

            gp.playSE(1);
            return true;
        } else {
            startDialogue(this, 6);  // Refusal dialogue
            return false;
        }
    }
}
