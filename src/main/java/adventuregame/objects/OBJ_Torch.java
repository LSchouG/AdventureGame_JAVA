package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Torch extends Entity {

    public static final String objName =  "torch";

    public OBJ_Torch(GamePanel gp){
        super(gp);

        type = type_light;
        name = objName;
        downStill = setup("/images/objects_interactive/torch1.png", gp.tileSize, gp.tileSize);
        down1= setup("/images/objects_interactive/torch2.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/objects_interactive/torch3.png", gp.tileSize, gp.tileSize);
        itemTitle = "[" + name + "]";
        itemDescription = "Illuminates your \nsurroundings";
        lightRadius = 200;
    }
}
