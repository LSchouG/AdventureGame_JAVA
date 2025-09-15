package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_KeyGold extends Entity {

    public static final String objName = "Gold Key";

    public OBJ_KeyGold(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_consumable;
        lockKeyType = "Boss Key";
        stackable = true;
        down1 = setup("/images/objects_pickup/key-gold.png", gp.tileSize, gp.tileSize);
        itemDescription = "A Gold Key";
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "You use the " + name + " and open the Boss Room";

        dialogues[1][0]  = "The key can be used on a door";
    }
    public boolean use(Entity entity){

        int objIndex = getDetected(entity, gp.obj, "Door");

        if(objIndex != 999){
            startDialogue(this,0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            startDialogue(this,1);
            return false;
        }
    }
}

