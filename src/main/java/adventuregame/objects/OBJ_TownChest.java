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
        type = type_NonUse;
        Price = 10000;
        down1 = setup("/images/objects_interactive/chest-closed.png", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects_interactive/chest-closed.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "Value: " + Price;
    }
}