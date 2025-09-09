package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Crystal extends Entity {
    GamePanel gp;
    public OBJ_Crystal(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Crystal";
        value = 2;
        type = type_pickUpOnly;
        useCost = 1;
        down1 = setup("/images/objects_pickup/full-crystal.png", gp.tileSize, gp.tileSize);
        image1 = setup("/images/objects_pickup/blank-crystal.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_pickup/half-crystal.png", gp.tileSize, gp.tileSize);
        image3 = setup("/images/objects_pickup/full-crystal.png", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;
        String text = "";
        Random random = new Random();
        int i = random.nextInt(100) + 1;

        if (i <= 20) {
            text = "You absorb the"+name+".\nYour mana tingles in places you didn't know existed. \n+"+value+" Max Mana!";
        } else if (i <= 40) {
            text = "You crush the "+name+" in your hand.\nYou feel slightly cooler.\n+"+value+" Max Mana!";
        } else if (i <= 60) {
            text = "You stare into the "+name+"... and it blinks back.\nAwkward, but effective.\n+"+value+" Max Mana!";
        } else if (i <= 80) {
            text = "You inhale the magic of the "+name+".\nProbably not how it was meant to be used. \n+"+value+" Max Mana!";
        } else {
            text = "You pocket the "+name+" like a pro.\nNo one saw. Except the gods. \n+"+value+" Max Mana!";
        }

        if (i == 100) {
            text = "The "+name+" fuses with your soul...\nSomewhere in the universe, reality rolls a saving throw.\n+"+value+" Max Mana!";
        }

        gp.ui.currentDialogue = text;
        entity.maxMana += value;
        gp.playSE(1);
        return true;

    }
    }
