/** ******************************************************************************
 * FileName: Player.java
 * Purpose: Represents the player character with movement, collision, and animation.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Inherits from Entity
 * - Handles keyboard input and character sprite animation
 * - Uses collision detection and supports object interactions
 *******************************************************************************/
package dev.adventuregame.entity;

import dev.adventuregame.GamePanel;
import dev.adventuregame.KeyHandler;
import dev.adventuregame.UtilityTool;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;

    /**************************************************************************
     * Constructor: Player(GamePanel gp, KeyHandler keyH)
     * Purpose: Initializes the player with key handler and position.
     ***************************************************************************/
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        // Place player in center of screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Define the player's collision box
        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 22;
        solidArea.height = 15;

        setDefaultValues();
        getImages();
    }

    /**************************************************************************
     * Method: setDefaultValues()
     * Purpose: Set default player position, speed, and direction.
     ***************************************************************************/
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 22;
        speed = 4;
        direction = "down";
        maxLife = 6;
        life = maxLife;
    }

    /**************************************************************************
     * Method: getImages()
     * Purpose: Load and assign all directional sprite images.
     ***************************************************************************/
    public void getImages() {
        downStill  = setup("/images/player/down_still_boy.png");
        down1      = setup("/images/player/down_1_boy.png");
        down2      = setup("/images/player/down_2_boy.png");
        upStill    = setup("/images/player/up_still_boy.png");
        up1        = setup("/images/player/up_1_boy.png");
        up2        = setup("/images/player/up_2_boy.png");
        leftStill  = setup("/images/player/left_still_boy.png");
        left1      = setup("/images/player/left_1_boy.png");
        left2      = setup("/images/player/left_2_boy.png");
        rightStill = setup("/images/player/right_still_boy.png");
        right1     = setup("/images/player/right_1_boy.png");
        right2     = setup("/images/player/right_2_boy.png");
    }

    /**************************************************************************
     * Method: update()
     * Purpose: Handle player input, movement, collisions, and animation.
     ***************************************************************************/
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // 1. Update direction
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            spriteNumber = 1; // start walking animation

            // 2. Check tile collisions
            collisionOn = false;
            gp.collisionChecker.checkTile(this);
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // 2.1. Check player to entity collisions
            int ncpIndex = gp.collisionChecker.checkPlayer(this, gp.npc);
            interactNPC(ncpIndex);

            // CHECK EVENT
            gp.eventHandler.checkEvent();

            // 3. Move player if no collision
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // 4. Handle sprite animation timing
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNumber = (spriteNumber == 1) ? 2 : 1;
                spriteCounter = 0;
            }

        } else {
            // Player standing still
            standCounter++;
            if (standCounter > 20) {
                spriteNumber = 0; // idle frame
                standCounter = 0;
            }
        }
    }

    /**************************************************************************
     * Method: pickUpObject(int i)
     * Purpose: Handle object pickup when player collides with one.
     * Inputs: i - index of the object collided with
     ***************************************************************************/
    public void pickUpObject(int i) {
        if (i != 999) {
            // future: implement object interaction logic here
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void interactNPC(int i){
        if (i != 999){
            if (gp.keyH.enterPressed == true)
            {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            gp.keyH.enterPressed = false;

        }
    }

    /**************************************************************************
     * Method: draw(Graphics2D g2)
     * Purpose: Draw the player's current sprite on screen.
     * Inputs: g2 - the graphics context
     ***************************************************************************/
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "down" -> {
                image = switch (spriteNumber) {
                    case 0 -> downStill;
                    case 1 -> down1;
                    case 2 -> down2;
                    default -> null;
                };
            }
            case "up" -> {
                image = switch (spriteNumber) {
                    case 0 -> upStill;
                    case 1 -> up1;
                    case 2 -> up2;
                    default -> null;
                };
            }
            case "right" -> {
                image = switch (spriteNumber) {
                    case 0 -> rightStill;
                    case 1 -> right1;
                    case 2 -> right2;
                    default -> null;
                };
            }
            case "left" -> {
                image = switch (spriteNumber) {
                    case 0 -> leftStill;
                    case 1 -> left1;
                    case 2 -> left2;
                    default -> null;
                };
            }
        }

        g2.drawImage(image, screenX, screenY, null); // draw sprite
    }
}
