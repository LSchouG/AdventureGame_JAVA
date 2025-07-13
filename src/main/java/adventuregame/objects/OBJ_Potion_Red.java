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
        down1 = setup("/images/objects_pickup/small-red-potion.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/big-red-potion.png", gp.tileSize, gp.tileSize);
        itemDescription = "[" + name + "]" + " \nA healing potion. \nRestores " + restoreValue + " HP.";
    }
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;

        if (gp.player.life < gp.player.maxLife) {
            String text = "";

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 20) {text = "You chug the " + name + " like it’s Friday night.\n+" + restoreValue + " HP and a hint of regret.";
            } else if (i <= 40) {
                text = "You drink the " + name + ".\nTastes like cherries… and poor decisions.\n+" + restoreValue + " HP.";
            } else if (i <= 60) {
                text = "The " + name + " goes down smooth.\nYou feel 3% cooler and +" + restoreValue + " HP healthier.";
            } else if (i <= 80) {
                text = "You slam the " + name + " like a legend.\nIt wasn’t even necessary… but it was stylish.\n+" + restoreValue + " HP.";
            } else {
                text = "You sip the " + name + " with pinky out.\nRoyalty levels of healing achieved.\n+" + restoreValue + " HP.";
            }

            if (i == 100) {
                text = "You drink the " + name + " and briefly see the fabric of the universe.\nOh, and +" + restoreValue + " HP.";
            }

            gp.ui.currentDialogue = text;
            entity.life += restoreValue;

            if (gp.player.life > gp.player.maxLife) {
                gp.player.life = gp.player.maxLife;
            }

            gp.playSE(1);
        } else {
            gp.ui.currentDialogue = "You consider drinking the potion...\n" +
                    "But decide not to waste good juice on a healthy body.";
        }
    }
}
