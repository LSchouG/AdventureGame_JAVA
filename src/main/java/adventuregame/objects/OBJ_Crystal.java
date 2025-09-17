package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Crystal extends Entity {
    GamePanel gp;
    public static final String objName = "Crystal";

    public OBJ_Crystal(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        value = 2;
        type = type_pickUpOnly;
        useCost = 1;
        down1 = setup("/images/objects_pickup/full-crystal.png", gp.tileSize, gp.tileSize);
        image1 = setup("/images/objects_pickup/blank-crystal.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/half-crystal.png", gp.tileSize, gp.tileSize);
        image3 = setup("/images/objects_pickup/full-crystal.png", gp.tileSize, gp.tileSize);
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "You absorb the"+name+".\nYour mana tingles in places you didn't know existed. \n+"+value+" Max Mana!";

        dialogues[1][0]  = "You crush the "+name+" in your hand.\nYou feel slightly cooler.\n+"+value+" Max Mana!";

        dialogues[2][0] = "You stare into the "+name+"... and it blinks back.\nAwkward, but effective.\n+"+value+" Max Mana!";

        dialogues[3][0] = "You inhale the magic of the "+name+".\nProbably not how it was meant to be used. \n+"+value+" Max Mana!";

        dialogues[4][0] = "You pocket the "+name+" like a pro.\nNo one saw. Except the gods. \n+"+value+" Max Mana!";

        dialogues[5][0] = "The "+name+" fuses with your soul...\nSomewhere in the universe, reality rolls a saving throw.\n+"+value+" Max Mana!";
    }
    public boolean use(Entity entity) {
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

        if(entity.maxMana < 16){
            entity.maxMana += value;
        }
        mana = maxMana;
        gp.playSE(1);
        return true;
    }
    }
