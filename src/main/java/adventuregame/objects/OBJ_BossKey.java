package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_BossKey extends Entity {

    public static final String objName = "Boss Key";

    public OBJ_BossKey(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_consumable;
        lockKeyType = "Boss Key";
        stackable = true;
        down1 = setup("/images/objects_pickup/key.png", gp.tileSize, gp.tileSize);
        itemDescription = "A Boss Key";
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

