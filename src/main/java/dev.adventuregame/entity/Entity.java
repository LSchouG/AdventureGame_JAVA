/**
 * *****************************************************************************
 * FileName: Entity.java
 * Purpose: Base class for all moving/drawable entities (e.g., player, NPCs).
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Handles position, movement, sprite animation, and drawing
 * - Can be extended by more specific entities
 *******************************************************************************/

package dev.adventuregame.entity;

import dev.adventuregame.GamePanel;
import dev.adventuregame.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    /****************************** POSITIONING *******************************/
    public int worldX, worldY; // world coordinates
    public int screenX, screenY; // on-screen position
    public int speed;
    public int actionLockCounter;

    /******************************* SPRITES **********************************/
    public BufferedImage downStill, down1, down2, upStill, up1, up2, leftStill, left1, left2, rightStill, right1, right2;


    /****************************** STATE *************************************/
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;

    /**************************** COLLISION BOX *******************************/
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // collision bounds
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    protected GamePanel gp;

    /**************************** CHARACTER STATUS *******************************/
    public int  maxLife;
    public int  life;

    /**************************** OTHER *******************************/
    String dialogues[] = new String[20];
    int dialogueIndex = 0;


    /**************************************************************************
     * Constructor: Entity(GamePanel gp)
     * Purpose: Initialize with reference to the game panel.
     ***************************************************************************/
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    /**************************************************************************
     * Method: setAction()
     * Purpose: Placeholder for subclasses to define specific behavior.
     * Notes: Empty in base class. Override in subclasses.
     ***************************************************************************/
    public void setAction() {
    }

    /**************************************************************************
     * Method: speak()
     * Purpose:
     * Notes:
     ***************************************************************************/
    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";

        }

    }

    /**************************************************************************
     * Method: update()
     * Purpose: Update logic (e.g., movement, collision). Called every frame.
     ***************************************************************************/
    public void update() {
        setAction();

        // Reset collision status before checking again
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkEntity(this);

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
    }

    /**************************************************************************
     * Method: draw(Graphics2D g2)
     * Purpose: Draws the entity to the screen with correct sprite based on direction.
     * Inputs: g2 - the graphics context
     ***************************************************************************/
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Calculate screen position based on world coordinates relative to the player
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw if within visible screen bounds
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Select sprite based on direction and sprite number
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

            // Draw the image on screen
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    /**************************************************************************
     * Method: setup(String imagePath)
     * Purpose: Load and scale an image for entity sprite.
     * Inputs: imagePath - relative path to the image file
     * Outputs: Scaled BufferedImage
     ***************************************************************************/
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        BufferedImage scaledImage = null;

        try {
            // Load image
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            // Scale image to tile size
            scaledImage = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image: " + imagePath, e);
        }

        return scaledImage;
    }
}
