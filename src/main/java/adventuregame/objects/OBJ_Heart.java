package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Heart extends Entity {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickUpOnly;
        name = "Heart";
        value = 2;
        down1 = setup("/images/objects_pickup/full-heart.png", gp.tileSize, gp.tileSize);
        image1 = setup("/images/objects_pickup/full-heart.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/half-heart.png", gp.tileSize, gp.tileSize);
        image3 = setup("/images/objects_pickup/blank-heart.png", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
    gp.gameState = gp.dialogueState;
    String text = "";
    Random random = new Random();
    int i = random.nextInt(100) + 1;

        if (i <= 20) {
            text = "You pick up the "+name+".\nYou feel stronger... emotionally and physically. \n+"+value+" Max HP!";
        } else if (i <= 40) {
            text = "You absorb the "+name+".\nYou swear it whispered, 'We ride at dawn.' \n+"+value+" Max HP!";
        } else if (i <= 60) {
            text = "You hold the "+name+"aloft like a champion.\nCue dramatic music. \n+"+value+" Max HP!";
        } else if (i <= 80) {
            text = "You found a "+name+"!\nDoctors recommend not eating strange organs... but here we are. \n+"+value+" Max HP!";
        } else {
            text = "You grab the "+name+" with zero hesitation.\nFive-second rule still counts in dungeons. \n+"+value+" Max HP!";
        }

        if (i == 100) {
            text = "The "+name+" fuses with yours...\nSomewhere in the multiverse, another you just exploded. \n+"+value+" Max HP!";
        }


        gp.ui.currentDialogue = text;
        entity.maxLife += value;
        gp.player.life = gp.player.maxLife;


        gp.playSE(1);
        return true;

    }
}
