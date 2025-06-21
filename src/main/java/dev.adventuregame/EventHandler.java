package dev.adventuregame;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect1, eventRect2;
    int eventRectDefaultX1, eventRectDefaultY1, eventRectDefaultX2, eventRectDefaultY2;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        // SMALL
        eventRect1 = new Rectangle();
        eventRect1.x = 23;
        eventRect1.y = 23;
        eventRect1.width = 2;
        eventRect1.height = 2;
        eventRectDefaultX1 = eventRect1.x;
        eventRectDefaultY1 = eventRect1.y;

        eventRect2 = new Rectangle();
        eventRect2.x = 23;
        eventRect2.y = 23;
        eventRect2.width = 16;
        eventRect2.height = 16;
        eventRectDefaultX2 = eventRect2.x;
        eventRectDefaultY2 = eventRect2.y;

    }

    public void checkEvent() {

        if(hit(23, 28, "any") == true){damagePit(gp.dialogueState);}
        if(interact(24, 19, "any") == true){healingBed(gp.dialogueState);}

    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect1.x = eventCol*gp.tileSize + eventRect1.x;
        eventRect1.y = eventRow*gp.tileSize + eventRect1.y;

        if (gp.player.solidArea.intersects(eventRect1)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect1.x = eventRectDefaultX1;
        eventRect1.y = eventRectDefaultY1;

        return hit;
    }

    public boolean interact(int eventCol, int eventRow, String reqDirection) {
        boolean interact = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect2.x = eventCol * gp.tileSize + eventRect2.x;
        eventRect2.y = eventRow * gp.tileSize + eventRect2.y;

        if (gp.player.solidArea.intersects(eventRect2)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                interact = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect2.x = eventRectDefaultX2;
        eventRect2.y = eventRectDefaultY2;

        return interact;
    }

    public void damagePit (int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You a falling into a pit!";
        gp.player.life--;
    }
    public void healingBed(int gameState){
        if (gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You took a nap and feel better!";
            gp.player.life = gp.player.maxLife;
            gp.keyH.enterPressed = false;
        }
    }
}
