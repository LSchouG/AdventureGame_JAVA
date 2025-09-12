package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.objects.*;

public class NPC_Seller extends Entity{
    /**************************************************************************
     * Constructor: NPC_OldMan(GamePanel gp)
     * Purpose: Initializes the old man NPC with a default direction and speed.
     ***************************************************************************/
    public NPC_Seller(GamePanel gp) {
        super(gp);
        type = type_npc;
        direction = "down"; // default facing direction
        solidArea.x = 0;// goes 0 pixel in from the side
        solidArea.y = 16;// goes 16 pixel down from the top
        solidArea.width = 48;// the space left, 48 - 0  = 48
        solidArea.height = 32; // the space left, 48 - 16 = 32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImages();
        setDialogue();
        setItems();
    }
    public void getImages() {
        downStill = setup("/images/npc/seller1.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/seller2.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/seller3.png", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Welcome to my Shop, Care to have a look at my goods?";

        dialogues[1][0] ="Come Again soon lad";

        dialogues[2][0] = "You need more gold to buy that";

        dialogues[3][0] = "You inventory is full";

        dialogues[4][0] = "You cannot sell an equipped item";
    }
    public void speak() {
        facePlayer();
        startDialogue(this, 0);
        gp.gameState = gp.shopState;
        gp.ui.npc = this;
    }
    public void setItems() {

        inventory.add(new OBJ_Potion_Blue(gp));
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Boots(gp));
        inventory.add(new OBJ_Iron_Shield(gp));
        inventory.add(new OBJ_Axe(gp));
    }
}
