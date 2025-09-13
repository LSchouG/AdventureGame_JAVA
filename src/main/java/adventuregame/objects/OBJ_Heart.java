package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Heart extends Entity {
    GamePanel gp;

    public static final String objName =  "Heart";

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickUpOnly;
        name = objName;
        value = 2;
        down1 = setup("/images/objects_pickup/full-heart.png", gp.tileSize, gp.tileSize);
        image1 = setup("/images/objects_pickup/full-heart.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/half-heart.png", gp.tileSize, gp.tileSize);
        image3 = setup("/images/objects_pickup/blank-heart.png", gp.tileSize, gp.tileSize);
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "You pick up the "+name+".\nYou feel stronger... emotionally and physically. \n+"+value+" Max HP!";

        dialogues[1][0] = "You absorb the "+name+".\nYou swear it whispered, 'We ride at dawn.' \n+"+value+" Max HP!";

        dialogues[2][0] = "You hold the "+name+"aloft like a champion.\nCue dramatic music. \n+"+value+" Max HP!";

        dialogues[3][0] = "You found a "+name+"!\nDoctors recommend not eating strange organs... but here we are. \n+"+value+" Max HP!";

        dialogues[4][0] =  "You grab the "+name+" with zero hesitation.\nFive-second rule still counts in dungeons. \n+"+value+" Max HP!";

        dialogues[5][0] = "The "+name+" fuses with yours...\nSomewhere in the multiverse, another you just exploded. \n+"+value+" Max HP!";
    }
    public boolean use(Entity entity){
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


        entity.maxLife += value;
        gp.player.life = gp.player.maxLife;
        gp.playSE(1);
        return true;

    }
}
