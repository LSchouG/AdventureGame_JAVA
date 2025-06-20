package dev.adventuregame.entity;

import dev.adventuregame.GamePanel;
import dev.adventuregame.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImages();
    }

    public void getImages() {
        downStill = setup("/images/npc/oldManDownStill.png");
        down1 = setup("/images/npc/oldManDown1.png");
        down2 = setup("/images/npc/oldManDown2.png");
        upStill = setup("/images/npc/oldManUpStill.png");
        up1 = setup("/images/npc/oldManUp1.png");
        up2 = setup("/images/npc/oldManUp2.png");
        leftStill = setup("/images/npc/oldManLeftStill.png");
        left1 = setup("/images/npc/oldManLeft1.png");
        left2 = setup("/images/npc/oldManLeft2.png");
        rightStill = setup("/images/npc/oldManRightStill.png");
        right1 = setup("/images/npc/oldManRight1.png");
        right2 = setup("/images/npc/oldManRight2.png");
    }
}
