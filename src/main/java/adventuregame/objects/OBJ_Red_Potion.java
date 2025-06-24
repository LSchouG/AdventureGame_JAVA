package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Red_Potion extends Entity{
    GamePanel gp;

    public OBJ_Red_Potion(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Red Potion";
        type = type_consumable;
        restoreValue = 5;
        down1 = setup("/images/objects/red_potion.png", gp.tileSize, gp.tileSize);
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
