package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Potion_RedBig extends Entity {
    GamePanel gp;

    public static final String objName = "Big Red Potion";

    public OBJ_Potion_RedBig(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_consumable;
        restoreValue = 16;
        Price = 40;
        stackable = true;
        down1 = setup("/images/objects_pickup/big-red-potion.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/big-red-potion.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "A Big healing potion. \nRestores " + restoreValue + " HP.";
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "You chug the " + name + " like it’s Friday night.\n+" + restoreValue + " HP and a hint of regret.";

        dialogues[1][0]  = "You drink the " + name + ".\nTastes like cherries… and poor decisions.\n+" + restoreValue + " HP.";

        dialogues[2][0] = "The " + name + " goes down smooth.\nYou feel 3% cooler and +" + restoreValue + " HP healthier.";

        dialogues[3][0] = "You slam the " + name + " like a legend.\nIt wasn’t even necessary… but it was stylish.\n+" + restoreValue + " HP.";

        dialogues[4][0] = "You sip the " + name + " with pinky out.\nRoyalty levels of healing achieved.\n+" + restoreValue + " HP.";

        dialogues[5][0] = "You drink the " + name + " and briefly see the fabric of the universe.\nOh, and +" + restoreValue + " HP.";

        // Refusal message for full health
        dialogues[6][0] = "You consider drinking the potion...\nBut decide not to waste good juice on a healthy body.";
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
            entity.life += restoreValue;

            if (gp.player.life > gp.player.maxLife) {
                gp.player.life = gp.player.maxLife;
            }

            gp.playSE(1);
            return true;
        } else {
            startDialogue(this, 6);  // Refusal dialogue
            return false;
        }
    }
}
