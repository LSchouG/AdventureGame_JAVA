package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Potion_Red extends Entity{
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Red Potion";
        type = type_consumable;
        restoreValue = 5;
        image1 = setup("/images/objects/small-red-potion.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects/big-red-potion.png", gp.tileSize, gp.tileSize);
        itemDescription = "[" + name + "]" + " \nA healing potion. \nRestores " + restoreValue + " HP.";
    }
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;

        if (gp.player.life < gp.player.maxLife) {
            gp.ui.currentDialogue = "You chug the " + name + " like a true hero!\n" +
                    "Ahh... sweet relief. +" + restoreValue + " life.";
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
