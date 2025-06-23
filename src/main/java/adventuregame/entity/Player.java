/**
 * *****************************************************************************
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
package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;
    KeyHandler keyH;
    int standCounter = 0;

    /**************************************************************************
     * Constructor: Player(GamePanel gp, KeyHandler keyH)
     * Purpose: Initializes the player with key handler and position.
     ***************************************************************************/
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        type = 0;
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

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImages();
        getPlayerAttachImages();
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
    public void getPlayerImages() {
        downStill = setup("/images/player/down_still_boy.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/player/down_1_boy.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/player/down_2_boy.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/player/up_still_boy.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/player/up_1_boy.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/player/up_2_boy.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/player/left_still_boy.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/player/left_1_boy.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/player/left_2_boy.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/player/right_still_boy.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/player/right_1_boy.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/player/right_2_boy.png", gp.tileSize, gp.tileSize);
    }

    /**************************************************************************
     * Method: getImages()
     * Purpose: Load and assign all directional sprite images.
     ***************************************************************************/
    public void getPlayerAttachImages() {
        attachUp = setup("/images/player/attach_up.png", gp.tileSize, gp.tileSize * 2);
        attachDown = setup("/images/player/attach_down.png", gp.tileSize, gp.tileSize * 2);
        attachLeft = setup("/images/player/attach_left.png", gp.tileSize * 2, gp.tileSize);
        attachRight = setup("/images/player/attach_right.png", gp.tileSize * 2, gp.tileSize);
    }

    /**************************************************************************
     * Method: update()
     * Purpose: Handle player input, movement, collisions, and animation.
     ***************************************************************************/
    public void update() {


        if (attaching == true){ attaching(); }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

            // 1. Update direction
            if (keyH.upPressed) {direction = "up";}
            else if (keyH.downPressed) {direction = "down";}
            else if (keyH.leftPressed) {direction = "left";}
            else if (keyH.rightPressed) {direction = "right";}
            if(spriteNumber == 0){spriteNumber = 1;}

            // 2 CHECK
            // COLLISION CHECK TILE
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // COLLISION CHECK OBJECT
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // COLLISION CHECK NPC
            int ncpIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(ncpIndex);

            if (ncpIndex == 999) {
                handleAttack();
            }

            // COLLISION CHECK ENTITY
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            interactNPC(monsterIndex);
            contactMonster(monsterIndex);


            // COLLISION CHECK TILE EVENT
            gp.eventHandler.checkEvent();

            // 3. Move player if no collision
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            gp.keyH.enterPressed = false;

            // Increase the counter
            spriteCounter++;

            // Every 13 frames, switch the sprite between 1 and 2
            if (spriteCounter > 12) {
                if (spriteNumber == 1)
                {
                    spriteNumber = 2;
                } else if (spriteNumber == 2)
                {
                    spriteNumber = 1;
                }

                // Reset the counter
                spriteCounter = 0;
            }
        } else {
            // Player standing still
            standCounter++;
            if (standCounter > 20) {
                spriteNumber = 0;
                standCounter = 0;
            }
        }

        // this needs to be outside of key if statment
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 100) {
                invincible = false;
                invincibleCounter = 0;
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
     public void attaching(){
         spriteCounter++;
         if (spriteCounter >= 3){
             spriteNumber = 1;
         }
         if (spriteCounter > 3 && spriteCounter < 12){
             spriteNumber = 2;

             // save the current worldX, worldY and solidarea
             int currentWorldX = worldX;
             int currentWorldY = worldY;
             int solidAreaWidth = solidArea.width;
             int solidAreaHeight = solidArea.height;

             //Adjust players worldX/Y for the attackArea
             switch (direction){
                 case "up": worldX -= attackArea.height; break;
                 case "down": worldY += attackArea.height; break;
                 case "left": worldX -= attackArea.width; break;
                 case "right": worldX += attackArea.width; break;
             }

             // attack area become solid area
             solidArea.width = attackArea.width;
             solidArea.height = attackArea.height;

             //check if solidArea(attackeArea) hit the monster
             int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);

             // After checking change back to  current worldX, worldY and solidarea
             worldX = currentWorldX;
             worldY = currentWorldY;
             solidArea.width = solidAreaWidth;
             solidArea.height = solidAreaHeight;

             damageMonster(monsterIndex);

         }
         if (spriteCounter >= 12 && spriteCounter < 15){
             spriteNumber = 1;
         }
         if (spriteCounter >= 15 && spriteCounter < 24){
             spriteNumber = 2;
         }
         if (spriteCounter >= 24){
             spriteNumber = 1;
             spriteCounter = 0;
             attaching = false;
         }
     }

    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed && i != 999) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
    }


    /**************************************************************************
     * Method: contactMonster()
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }
    /**************************************************************************
     * Method: handleAttack()
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void handleAttack() {
        if (gp.keyH.enterPressed && !attaching) {
            attaching = true;
            spriteCounter = 0;  // Reset sprite counter for attack animation
        }
    }
    /**************************************************************************
     * Method: handleAttack()
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void damageMonster(int i){
        if (i != 999){
            if (gp.monster[i].invincible == false){
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;

                if (gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                }
            }
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
            case "down":
                if (attaching == false){
                    if (spriteNumber == 0){image = downStill;}
                    if (spriteNumber == 1){image = down1;}
                    if (spriteNumber == 2){image = down2;}
                }
                if (attaching == true){
                    if (spriteNumber == 1){image = down1;}
                    if (spriteNumber == 2){image = attachDown;}
                } break;
            case "up":
                if (attaching == false){
                    if (spriteNumber == 0){image = upStill;}
                    if (spriteNumber == 1){image = up1;}
                    if (spriteNumber == 2){image = up2;}
                }
                if (attaching == true){
                    if (spriteNumber == 1){image = up1;}
                    if (spriteNumber == 2){image = attachUp;}
                } break;
            case "right":
                if (attaching == false){
                    if (spriteNumber == 0){image = rightStill;}
                    if (spriteNumber == 1){image = right1;}
                    if (spriteNumber == 2){image = right2;}
                }
                if (attaching == true){
                    if (spriteNumber == 1){image = right1;}
                    if (spriteNumber == 2){image = attachRight;}
                } break;
            case "left":
                if (attaching == false){
                    if (spriteNumber == 0){image = leftStill;}
                    if (spriteNumber == 1){image = left1;}
                    if (spriteNumber == 2){image = left2;}
                }
                if (attaching == true){
                    if (spriteNumber == 1){image = left1;}
                    if (spriteNumber == 2){image = attachLeft;}
                } break;
            default:
                if (attaching == false){
                    image = downStill;
                }
                if (attaching == true){
                    if (spriteNumber == 0){image = downStill;}
                    if (spriteNumber == 1){image = down1;}
                    if (spriteNumber == 2){image = attachDown;}
                } break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null); // draw sprite
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
