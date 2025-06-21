/** ******************************************************************************
 * FileName: SuperObject.java
 * Purpose: Base class for all interactive and drawable game objects.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Provides shared properties and draw functionality for all object types
 * - Extended by objects like keys, chests, doors, and boots
 *******************************************************************************/

package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;
import dev.adventuregame.UtilityTool;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject {

    /************************** OBJECT PROPERTIES *****************************/
    public BufferedImage image;      // Object image/sprite
    public String name;              // Object name identifier
    public boolean collision = false;// True if player can't walk through

    /*************************** POSITIONING **********************************/
    public int worldX, worldY;       // World coordinates

    /**************************** COLLISION BOX *******************************/
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default collision size
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    /**************************** UTILITIES ***********************************/
    UtilityTool uTool = new UtilityTool(); // for image scaling and manipulation

    /**************************************************************************
     * Method: draw(Graphics2D g2, GamePanel gp)
     * Purpose: Draw the object if it is within the visible screen area.
     * Inputs: g2 - Graphics2D context, gp - GamePanel instance
     ***************************************************************************/
    public void draw(Graphics2D g2, GamePanel gp) {

        // Translate world position to on-screen coordinates relative to player
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw object if it is within visible screen area
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
