package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Lantern extends Entity {

    public static final String objName =  "Lantern";

    public OBJ_Lantern(GamePanel gp){
        super(gp);

        type = type_light;
        name = objName;
        down1 = setup("/images/objects_pickup/lantern.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "Illuminates your \nsurroundings";
        gold = 200;
        lightRadius = 250;

    }

}
