package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_DoorIron extends Entity {
    public static final String objName = "Iron Door";

    public OBJ_DoorIron(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/images/objects_interactive/iron-door.png", gp.tileSize, gp.tileSize);
        collision = true;
        type = type_obstacle;


        solidArea.x = 0;// goes 0 pixel in from the side
        solidArea.y = 16;// goes 16 pixel down from the top
        solidArea.width = 48;// the space left, 48 - 0  = 48
        solidArea.height = 32;// the space left, 48 - 16 = 32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();
    }
    public void interact(){
        startDialogue(this,0);
    }
    public void setDialogue() {
        dialogues[0][0] = "Oi, this door’s locked tight! Some mechanism holds it shut";
        dialogues[0][1] = "Three big rock, place ‘em wise!";
    }
}

