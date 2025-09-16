package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_TownChest extends Entity{
    GamePanel gp;

    public static final String objName =  "Towns Treasure Chest";

    public OBJ_TownChest(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_pickUpOnly;
        Price = 10000;
        down1 = setup("/images/objects_interactive/chest-village.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_interactive/chest-village.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "The Value: " + Price;
        setDialouges();
    }
    public void setDialouges(){
        dialogues[0][0] = "you have picked up the Treasure Chest!";
    }
    public boolean use(Entity entity){

        gp.csManager.sceneNum = gp.csManager.ending;
        gp.gameState = gp.cutSceneState;



        return true;
    }
}