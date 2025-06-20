package dev.adventuregame.entity;
import dev.adventuregame.GamePanel;
import dev.adventuregame.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int screenX, screenY;
    public int speed;
    public BufferedImage downStill, down1, down2, upStill, up1, up2, leftStill, left1, left2, rightStill, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gp) {
        this.gp = gp;

    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        BufferedImage scaledimage = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            scaledimage = image;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledimage;
    }



}
