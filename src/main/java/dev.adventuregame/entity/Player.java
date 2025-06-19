package dev.adventuregame.entity;

import dev.adventuregame.GamePanel;
import dev.adventuregame.KeyHandler;
import dev.adventuregame.UtilityTool;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
//  public int hasKey = 0;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 22;
        solidArea.height = 15;

        setDefaultValues();
        getPlayerImages();
        // System.out.println("DEBUG: Player constructed at worldX: " + worldX + ", worldY: " + worldY);
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 22;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImages() {
        downStill  = setup("down_still_boy");
        down1      = setup("down_1_boy");
        down2      = setup("down_2_boy");
        upStill    = setup("up_still_boy");
        up1        = setup("up_1_boy");
        up2        = setup("up_2_boy");
        leftStill  = setup("left_still_boy");
        left1      = setup("left_1_boy");
        left2      = setup("left_2_boy");
        rightStill = setup("right_still_boy");
        right1     = setup("right_1_boy");
        right2     = setup("right_2_boy");
    }

    public BufferedImage setup(String imageName)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        BufferedImage scaledimage = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            scaledimage = image;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledimage;
    }

    public void update() {
        boolean moving = false;
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
                spriteNumber = 1;
            } else if (keyH.downPressed == true) {
                direction = "down";
                spriteNumber = 1;
            } else if (keyH.leftPressed == true) {
                direction = "left";
                spriteNumber = 1;
            } else if (keyH.rightPressed == true) {
                direction = "right";
                spriteNumber = 1;
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISION IF FALSE PLAYER CAN MOVE
            if (collisionOn == false) {
                switch (direction) {
                    case "up" ->
                        worldY -= speed;
                    case "down" ->
                        worldY += speed;
                    case "right" ->
                        worldX += speed;
                    case "left" ->
                        worldX -= speed;
                }
            }
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }

        } else {
            standCounter++;
                    
            if (standCounter > 20){
            
            spriteNumber = 0;
            standCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {

        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize); 

        BufferedImage image = null;

        switch (direction) {
            case "down" -> {
                if (spriteNumber == 0) {
                    image = downStill;
                } else if (spriteNumber == 1) {
                    image = down1;
                } else if (spriteNumber == 2) {
                    image = down2;
                }
                break;
            }
            case "up" -> {
                if (spriteNumber == 0) {
                    image = upStill;
                } else if (spriteNumber == 1) {
                    image = up1;
                } else if (spriteNumber == 2) {
                    image = up2;
                }
                break;
            }
            case "right" -> {
                if (spriteNumber == 0) {
                    image = rightStill;
                } else if (spriteNumber == 1) {
                    image = right1;
                } else if (spriteNumber == 2) {
                    image = right2;
                }
                break;
            }
            case "left" -> {
                if (spriteNumber == 0) {
                    image = leftStill;
                } else if (spriteNumber == 1) {
                    image = left1;
                } else if (spriteNumber == 2) {
                    image = left2;
                }
            }

        }
        g2.drawImage(image, screenX, screenY, null);

    }
}
