package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.objects.OBJ_DoorIron;
import adventuregame.tile_interactive.IT_MetalPlate;
import adventuregame.tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;

public class NPC_Torch extends Entity{
    public static final  String  npcName = "Torch";

    public NPC_Torch(GamePanel gp) {
        super(gp);

        name = npcName;
        type = type_light;
        direction = "down";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 42;
        solidArea.height = 42;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        lightRadius = 250;

        getImages();
    }
    public void getImages() {
        downStill = setup("/images/objects_interactive/torch1.png", gp.tileSize, gp.tileSize);
        down1= setup("/images/objects_interactive/torch2.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/objects_interactive/torch3.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/objects_interactive/torch1.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/objects_interactive/torch2.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/objects_interactive/torch3.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/objects_interactive/torch1.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/objects_interactive/torch2.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/objects_interactive/torch3.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/objects_interactive/torch1.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/objects_interactive/torch2.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/objects_interactive/torch3.png", gp.tileSize, gp.tileSize);
    }
}

