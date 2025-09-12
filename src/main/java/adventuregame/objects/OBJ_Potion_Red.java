package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Potion_Red extends Entity{
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Red Potion";
        type = type_consumable;
        restoreValue = 5;
        Price = 22;
        stackable = true;
        down1 = setup("/images/objects_pickup/small-red-potion.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/big-red-potion.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "A healing potion. \nRestores " + restoreValue + " HP.";
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "You chug the " + name + " like it’s Friday night.\n+" + restoreValue + " HP and a hint of regret.";

        dialogues[1][0]  = "You drink the " + name + ".\nTastes like cherries… and poor decisions.\n+" + restoreValue + " HP.";

        dialogues[2][0] = "The " + name + " goes down smooth.\nYou feel 3% cooler and +" + restoreValue + " HP healthier.";

        dialogues[3][0] = "You slam the " + name + " like a legend.\nIt wasn’t even necessary… but it was stylish.\n+" + restoreValue + " HP.";

        dialogues[4][0] = "You sip the " + name + " with pinky out.\nRoyalty levels of healing achieved.\n+" + restoreValue + " HP.";

        dialogues[5][0] = "You drink the " + name + " and briefly see the fabric of the universe.\nOh, and +" + restoreValue + " HP.";
    }
    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;

        if (gp.player.life < gp.player.maxLife) {
            String text = "";

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 20) {
                startDialogue(this,0);
            } else if (i <= 40) {
                startDialogue(this,1);
            } else if (i <= 60) {
                startDialogue(this,2);
            } else if (i <= 80) {
                startDialogue(this,3);
            } else if (i < 100)  {
                startDialogue(this,4);
            }

            if (i == 100) {
                startDialogue(this,5);
            }

            gp.ui.currentDialogue = text;
            entity.life += restoreValue;

            if (gp.player.life > gp.player.maxLife) {
                gp.player.life = gp.player.maxLife;
            }

            gp.playSE(1);
            return true;
        } else {
            gp.ui.currentDialogue = "You consider drinking the potion...\n" +
                    "But decide not to waste good juice on a healthy body.";
            return false;
        }
    }
}
