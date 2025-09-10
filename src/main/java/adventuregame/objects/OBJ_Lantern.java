package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Lantern extends Entity {

    public OBJ_Lantern(GamePanel gp){
        super(gp);

        type = type_light;
        name = "Lantern";
        down1 = setup("/images/objects_pickup/lantern.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "Illuminates your \nsurroundings";
        gold = 200;
        lightRadius = 250;

    }

}
