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
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventMaster = new Entity(gp);

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
        setDialogue();
    }
    public void setDialogue() {
        eventMaster.dialogues[0][0] = "You are falling into a pit!";

        eventMaster.dialogues[1][0] = "You took a nap and feel better! \n\n(The Game have been saved)";
    }
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

            // TELEPORT

            if (hit(1,16, 27, "any")) {teleport( 3, 15, 31);} // from home to City
            else if (hit(3,15, 31, "any")) {teleport( 1,16, 27);} // from City to home
            else if (hit(2, 16, 27, "any")) {teleport( 3,19, 28);} // from Seller to City
            else if (hit(3,19, 28, "any")) {teleport( 2, 16, 27);} // from city to seller
            else if (hit(3,21, 40, "any")) {teleport( 0, 12, 36);} // from City to map
            else if (hit(0,12, 36, "any")) {teleport( 3, 21, 40);} // from map to City
            else if (hit(0,65, 18, "any")) {teleport( 5, 46, 66);} // from map to dungeon
            else if (hit(5,46, 66, "any")) {teleport( 0, 65, 18);} // from dungeon to map
            else if (hit(5,82, 39, "any")) {teleport( 4, 28, 43);} // from dungeon to bossMap
            else if (hit(4,28, 43, "any")) {teleport( 5, 82, 39);} // from bossMap to dungeon
            else if (hit(2,16, 22, "any")) {speak(gp.npc[2][0]);} // from map to City


            // TILE EVENTS LIKE BED OR PIT

            else if (hit(0,23, 28, "any")) {damagePit( gp.dialogueState);}
            else if (hit(1,17, 20, "any")) {healingBed(gp.dialogueState);}
        }
    }
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
    public boolean interact(int map, int col, int row, String reqDirection) {
        boolean interact = false;

        if (gp.currentMap == map) {  // Add explicit map check to prevent index errors
            // Translate solid area to world coordinates
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            // Translate event rectangle to world position
            eventRect2[gp.currentMap][col][row].x = col * gp.tileSize + eventRect2[gp.currentMap][col][row].x;
            eventRect2[gp.currentMap][col][row].y = row * gp.tileSize + eventRect2[gp.currentMap][col][row].y;

            if (gp.player.solidArea.intersects(eventRect2[gp.currentMap][col][row]) && !eventRect2[gp.currentMap][col][row].eventDone) {
                if (gp.player.direction.equals(reqDirection) || "any".equals(reqDirection)) {
                    interact = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                    // Optional: System.out.println("Interaction triggered at col " + col + ", row " + row);
                }
            }

            // Reset positions
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect2[gp.currentMap][col][row].x = eventRect2[gp.currentMap][col][row].eventRectDefaultX;
            eventRect2[gp.currentMap][col][row].y = eventRect2[gp.currentMap][col][row].eventRectDefaultY;
        }  // Implicit false if map mismatch

        return interact;
    }
    public void damagePit( int gameState) {
        gp.gameState = gameState;
        eventMaster.startDialogue(eventMaster,0);
        gp.player.life--;
       // eventRect1[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healingBed( int gameState) {
        if (gp.keyH.enterPressed) {
        gp.gameState = gameState;
        gp.player.attackCanceled = true;
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.aSetter.setMonster();
        gp.saveLoad.save();
            eventMaster.startDialogue(eventMaster,1);
    }}
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
