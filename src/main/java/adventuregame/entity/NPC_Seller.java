package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.objects.OBJ_Potion_Red;
import adventuregame.objects.OBJ_Potion_Blue;

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
        solidArea.height = 32;// the space left, 48 - 16 = 32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImages();
        setDialogue();
        setItems();
    }

    /**************************************************************************
     * Method: getImages()
     * Purpose: Loads all sprite images for this NPC (idle and walking).
     ***************************************************************************/
    public void getImages() {
        downStill = setup("/images/npc/seller1.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/seller2.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/seller3.png", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Welcome to my Shop, Care to have a look at my goods?";
    }
    public void speak() {
        super.speak();
        gp.gameState = gp.shopState;
        gp.ui.npc = this;
    }
    public void setItems() {

        inventory.add(new OBJ_Potion_Blue(gp));
        inventory.add(new OBJ_Potion_Red(gp));
    }
}
