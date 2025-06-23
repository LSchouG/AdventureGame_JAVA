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

public class EventHandler {

    GamePanel gp;
    //Rectangle eventRect;
    // Event trigger zones
    //Rectangle eventRect1, eventRect2;
    // Default positions for resetting after checks
    //int eventRectDefaultX1, eventRectDefaultY1;
    //int eventRectDefaultX2, eventRectDefaultY2;

    EventRect eventRect1[][];
    EventRect eventRect2[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;



    /**************************************************************************
     * Constructor: EventHandler(GamePanel gp)
     * Purpose: Initializes trigger rectangles and stores default positions.
     ***************************************************************************/
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        // Define a small collision area (tight trigger)
        eventRect1 = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect1[col][row] = new EventRect();
            eventRect1[col][row].x = 23;
            eventRect1[col][row].y = 23;
            eventRect1[col][row].width = 2;
            eventRect1[col][row].height = 2;
            eventRect1[col][row].eventRectDefaultX = eventRect1[col][row].x;
            eventRect1[col][row].eventRectDefaultY = eventRect1[col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        // Define a small collision area (tight trigger)
        eventRect2 = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        col = 0;
        row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect2[col][row] = new EventRect();
            eventRect2[col][row].x = 23;
            eventRect2[col][row].y = 23;
            eventRect2[col][row].width = 23;
            eventRect2[col][row].height = 23;
            eventRect2[col][row].eventRectDefaultX = eventRect2[col][row].x;
            eventRect2[col][row].eventRectDefaultY = eventRect2[col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
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
        if (canTouchEvent){
            if (hit(23, 28, "any")) {damagePit(23, 28, gp.dialogueState);}
            if (interact(24, 19, "any")) {healingBed(24,19,gp.dialogueState);}
        }
        if (interact(23, 29, "up")) {teleport(gp.dialogueState);}

    }

    /**************************************************************************
     * Method: hit(int eventCol, int eventRow, String reqDirection)
     * Purpose: Detects collision with a small event zone.
     * Inputs:
     *   - eventCol/eventRow: tile coordinates of the event
     *   - reqDirection: direction required to trigger (or "any")
     * Output: true if player hits the trigger
     ***************************************************************************/
    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        // Translate solid area to world coordinates
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // Translate event rectangle to world position
        eventRect1[col][row].x = col * gp.tileSize + eventRect1[col][row].x;
        eventRect1[col][row].y = row * gp.tileSize + eventRect1[col][row].y;

        if (gp.player.solidArea.intersects(eventRect1[col][row]) && eventRect1[col][row].eventDone == false) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        // Reset positions
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect1[col][row].x = eventRect1[col][row].eventRectDefaultX;
        eventRect1[col][row].y = eventRect1[col][row].eventRectDefaultY;

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

        eventRect2[col][row].x = col * gp.tileSize + eventRect2[col][row].x;
        eventRect2[col][row].y = row * gp.tileSize + eventRect2[col][row].y;

        if (gp.player.solidArea.intersects(eventRect2[col][row]) && eventRect2[col][row].eventDone == false) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                interact = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect2[col][row].x = eventRect2[col][row].eventRectDefaultX;
        eventRect2[col][row].y = eventRect2[col][row].eventRectDefaultY;

        return interact;
    }

    /**************************************************************************
     * Method: damagePit(int gameState)
     * Purpose: Simulates falling into a pit and decreases player health.
     * Inputs: gameState - the dialogue state to switch to
     ***************************************************************************/
    public void damagePit(int col, int row, int gameState) {
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
    public void healingBed(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You took a nap and feel better!";
        gp.player.life = gp.player.maxLife;
        gp.keyH.enterPressed = false;
        if (gp.keyH.enterPressed) {}
        canTouchEvent = false;
    }

    /**************************************************************************
     * Method: teleport(int gameState)
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void teleport(int gameState) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Teleport!";
            gp.player.worldX = gp.tileSize*37;
            gp.player.worldY = gp.tileSize*20;
        }
    }
}
