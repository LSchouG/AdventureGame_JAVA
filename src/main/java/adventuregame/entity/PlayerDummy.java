package adventuregame.entity;

import adventuregame.GamePanel;

public class PlayerDummy extends Entity{

    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gp){
     super(gp);

     name = npcName;
     getImages();
    }
    public void getImages() {
        downStill = setup("/images/player/player-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/player/player-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/player/player-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/player/player-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/player/player-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/player/player-up2.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/player/player-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/player/player-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/player/player-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/player/player-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/player/player-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/player/player-right2.png", gp.tileSize, gp.tileSize);
    }
}
