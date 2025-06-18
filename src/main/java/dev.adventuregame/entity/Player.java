package dev.adventuregame.entity;

import dev.adventuregame.GamePanel;
import dev.adventuregame.KeyHandler;

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
    public int hasKey = 0;
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
        try {
            downStill = ImageIO.read(getClass().getResourceAsStream("/images/player/down_still_boy.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/images/player/down_1_boy.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/images/player/down_2_boy.png"));
            upStill = ImageIO.read(getClass().getResourceAsStream("/images/player/up_still_boy.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/images/player/up_1_boy.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/images/player/up_2_boy.png"));
            leftStill = ImageIO.read(getClass().getResourceAsStream("/images/player/left_still_boy.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/images/player/left_1_boy.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/images/player/left_2_boy.png"));
            rightStill = ImageIO.read(getClass().getResourceAsStream("/images/player/right_still_boy.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/images/player/right_1_boy.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/images/player/right_2_boy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(2);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Found a Key");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Opend a door");
                    } else {
                        gp.ui.showMessage("Door is locked, find a key");
                    }
                    break;
                case "Chest":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Opened a Chest");
                    } else {
                        gp.ui.showMessage("Chest is locked, find a key");
                    }
                    if (gp.obj[5] == null) {
                        gp.ui.gameFinished = true;
                        gp.stopMusic();
                        gp.playSE(4);
                    }
                    break;
                case "Boots":
                    if (gp.obj[4] == null) {
                        gp.playSE(1);
                        gp.obj[i] = null;
                        speed += 2;
                        gp.ui.showMessage("Equipped better Boots");
                    }
                    break;
            }
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
