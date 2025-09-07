/** ******************************************************************************
 * FileName: EventHandler.java
 * Purpose: Handles in-game events such as traps, healing, and scripted interactions.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Uses small rectangles to define trigger zones on the map
 * - Supports event types like pit traps and healing beds
 *******************************************************************************/

package adventuregame;

import adventuregame.entity.Entity;

public class EventHandler {

    GamePanel gp;

    EventRect eventRect1[][][];
    EventRect eventRect2[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    /**************************************************************************
     * Constructor: EventHandler(GamePanel gp)
     * Purpose: Initializes trigger rectangles and stores default positions.
     ***************************************************************************/
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        // Define a small collision area (tight trigger)
        eventRect1 = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect1[map][col][row] = new EventRect();
            eventRect1[map][col][row].x = 23;
            eventRect1[map][col][row].y = 23;
            eventRect1[map][col][row].width = 2;
            eventRect1[map][col][row].height = 2;
            eventRect1[map][col][row].eventRectDefaultX = eventRect1[map][col][row].x;
            eventRect1[map][col][row].eventRectDefaultY = eventRect1[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
        // Define a small collision area (tight trigger)
        eventRect2 = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        map = 0;
        col = 0;
        row = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect2[map][col][row] = new EventRect();
            eventRect2[map][col][row].x = 23;
            eventRect2[map][col][row].y = 23;
            eventRect2[map][col][row].width = 23;
            eventRect2[map][col][row].height = 23;
            eventRect2[map][col][row].eventRectDefaultX = eventRect2[map][col][row].x;
            eventRect2[map][col][row].eventRectDefaultY = eventRect2[map][col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }
    /**************************************************************************
     * Method: checkEvent()
     * Purpose: Triggers specific game events based on player's tile position.
     ***************************************************************************/
    public void checkEvent() {
        // check if player is more than one tile away from last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        // create event for intaeractiv
        if (canTouchEvent){
            if (hit(0,23, 28, "any")) {damagePit( gp.dialogueState);}
            else if (interact(23, 19, "any")) {healingBed(gp.dialogueState);}
            else if (hit(1,16, 27, "any")) {teleport( 3, 15, 31);} // from home to City
            else if (hit(3,15, 31, "any")) {teleport( 1,16, 27);} // from City to home
            else if (hit(2, 16, 27, "any")) {teleport( 3,19, 28);} // from Seller to City
            else if (hit(3,19, 28, "any")) {teleport( 2, 16, 27);} // from city to seller
            else if (hit(3,21, 40, "any")) {teleport( 0, 12, 36);} // from City to map
            else if (hit(0,12, 36, "any")) {teleport( 3, 21, 40);} // from map to City

            else if (hit(2,16, 22, "any")) {speak(gp.npc[2][0]);} // from map to City
        }
    }
    /**************************************************************************
     * Method: hit(int eventCol, int eventRow, String reqDirection)
     * Purpose: Detects collision with a small event zone.
     * Inputs:
     *   - eventCol/eventRow: tile coordinates of the event
     *   - reqDirection: direction required to trigger (or "any")
     * Output: true if player hits the trigger
     ***************************************************************************/
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap)
        {
            // Translate solid area to world coordinates
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            // Translate event rectangle to world position
            eventRect1[map][col][row].x = col * gp.tileSize + eventRect1[map][col][row].x;
            eventRect1[map][col][row].y = row * gp.tileSize + eventRect1[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect1[map][col][row]) && eventRect1[map][col][row].eventDone == false) {
                if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            // Reset positions
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect1[map][col][row].x = eventRect1[map][col][row].eventRectDefaultX;
            eventRect1[map][col][row].y = eventRect1[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    /**************************************************************************
     * Method: interact(int eventCol, int eventRow, String reqDirection)
     * Purpose: Detects interaction with a larger area (e.g., beds).
     * Inputs and logic same as hit()
     ***************************************************************************/
    public boolean interact(int col, int row, String reqDirection) {
        boolean interact = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        eventRect2[gp.currentMap][col][row].x = col * gp.tileSize + eventRect2[gp.currentMap][col][row].x;
        eventRect2[gp.currentMap][col][row].y = row * gp.tileSize + eventRect2[gp.currentMap][col][row].y;

        if (gp.player.solidArea.intersects(eventRect2[gp.currentMap][col][row]) && eventRect2[gp.currentMap][col][row].eventDone == false) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                interact = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect2[gp.currentMap][col][row].x = eventRect2[gp.currentMap][col][row].eventRectDefaultX;
        eventRect2[gp.currentMap][col][row].y = eventRect2[gp.currentMap][col][row].eventRectDefaultY;

        return interact;
    }
    /**************************************************************************
     * Method: damagePit(int gameState)
     * Purpose: Simulates falling into a pit and decreases player health.
     * Inputs: gameState - the dialogue state to switch to
     ***************************************************************************/
    public void damagePit( int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You are falling into a pit!";
        gp.player.life--;
       // eventRect1[col][row].eventDone = true;
        canTouchEvent = false;
    }
    /**************************************************************************
     * Method: healingBed(int gameState)
     * Purpose: Heals the player to full health when interacting with a bed.
     * Inputs: gameState - the dialogue state to switch to
     ***************************************************************************/
    public void healingBed( int gameState) {
        if (gp.keyH.enterPressed) {
        gp.gameState = gameState;
        gp.player.attackCanceled = true;
        gp.ui.currentDialogue = "You took a nap and feel better!";
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.aSetter.setMonster();
    }}
    /**************************************************************************
     * Method: teleport(int gameState)
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void teleport(int map, int col, int row) {

        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;

        gp.playSE(3);

    }
    public void speak(Entity entity) {
        if(gp.keyH.enterPressed) {
            gp.player.attackCanceled = true;
           gp.gameState = gp.dialogueState;
           entity.speak();

        }
    }
}
