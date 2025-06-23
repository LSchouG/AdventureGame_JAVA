/**
 * *****************************************************************************
 * FileName: CollisionChecker.java
 * Purpose: Handles collision detection between entities, tiles, and objects.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Supports tile-based and object-based collision detection
 * - Used by Player and NPCs during movement updates
 *******************************************************************************/

package adventuregame;

import adventuregame.entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    /**************************************************************************
     * Constructor: CollisionChecker(GamePanel gp)
     * Purpose: Initializes the collision checker with reference to the game panel.
     ***************************************************************************/
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**************************************************************************
     * Method: checkTile(Entity entity)
     * Purpose: Checks for collision between the entity and map tiles.
     * Inputs: entity - the entity whose position and direction is checked
     * Notes: Sets entity.collisionOn = true if a solid tile is detected ahead
     ***************************************************************************/
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNumber1, tileNumber2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNumber1].collision || gp.tileM.tile[tileNumber2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNumber2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNumber1].collision || gp.tileM.tile[tileNumber2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNumber1].collision || gp.tileM.tile[tileNumber2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNumber1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
                tileNumber2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNumber1].collision || gp.tileM.tile[tileNumber2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    /**************************************************************************
     * Method: checkObject(Entity entity, boolean player)
     * Purpose: Checks if an entity collides with an object in the game world.
     * Inputs:
     *   - entity: The entity to check (e.g., player or NPC)
     *   - player: Boolean flag; if true, allows object pickup (returns index)
     * Output:
     *   - Returns index of collided object if player and intersected, otherwise 999
     * Notes:
     *   - Sets entity.collisionOn = true if object blocks movement
     ***************************************************************************/
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // Set entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Set object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // Simulate future position based on movement direction
                switch (entity.direction) {
                    case "up":  entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                    }

                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
                // Reset solid areas to default after checking
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
                }
            }
        return index;
    }

    /**************************************************************************
     * Method: checkPlayer(Entity entity)
     * Purpose: Checks for collision between the player to entity.
     * Inputs: entity - the entity whose position and direction is checked
     * Notes: Sets entity.collisionOn = true if a solid tile is detected ahead
     ***************************************************************************/
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (target[i] != null) {

                // Set entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Set object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                // Simulate future position based on movement direction
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                // Reset solid areas to default after checking
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**************************************************************************
     * Method: checkEntity(Entity entity)
     * Purpose: Checks for collision between the entity to player.
     * Inputs: entity - the entity whose position and direction is checked
     * Notes: Sets entity.collisionOn = true if a solid tile is detected ahead
     ***************************************************************************/
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        // Set entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Set object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // Simulate future position based on movement direction
        switch (entity.direction) {
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        // Reset solid areas to default after checking
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;
    }
}
