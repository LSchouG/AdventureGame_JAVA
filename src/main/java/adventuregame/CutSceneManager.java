package adventuregame;

import adventuregame.entity.PlayerDummy;
import adventuregame.monster.MON_BOSS;
import adventuregame.objects.OBJ_DoorIron;
import adventuregame.objects.OBJ_TownChest;

import java.awt.*;

public class CutSceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha;
    int y;
    String endCredit;

    // Scene Numbers
    public final  int NA = 0;
    public final int boss = 1;
    public final int ending = 2;

    public CutSceneManager(GamePanel gp){
        this.gp = gp;
        endCredit = "Made by\n"
                + "Lars Gregersen\n"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "Special Thanks\n"
                + "RyiShow\n"
                + "and other creates for art and music\n\n"
                + "Thank you for playing";
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        // SWITCH BETWEEN SECENE
        switch (sceneNum) {
            case boss: scene_Boss(); break;
            case ending: scene_ending(); break;
        }
    }
    public void scene_Boss(){
        // scenePhase == 0
        if(scenePhase == 0){
            gp.bossBattleOn = true;

            // SHUT THE DOOR
            for(int i = 0; i < gp.obj[1].length; i++){
                if(gp.obj[gp.currentMap][i] == null){
                    gp.obj[gp.currentMap][i] = new OBJ_DoorIron(gp);                   // BOSS DOOR
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize * 28;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize * 43;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSE(23);
                    break;
                }
            }
            // PLACE THE DUMMY PLAYER
            for(int i = 0; i < gp.npc[1].length; i++){
                if(gp.npc[gp.currentMap][i] == null){
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);                   // BOSS DOOR
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = "up";
                    break;
                }
            }

            gp.player.drawing = false;
            scenePhase++;
        }

        // scenePhase == 1
        if(scenePhase == 1){

            gp.player.worldY -= 2;
            if (gp.player.worldY < 1010){
                scenePhase++;
            }
        }

        // scenePhase == 2
        if(scenePhase == 2){

            // SEARCH BOSS AND ACTIVATE HIM
            for(int i = 0; i < gp.monster[1].length; i++){
                if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name == MON_BOSS.monName){
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    scenePhase++;
                break;
            }
            }
        }

        // scenePhase == 3
        if(scenePhase == 3){
            gp.ui.drawDialogueScreen();
            // Wait until dialogue is finished before advancing
            if(gp.ui.npc.dialogues[gp.ui.npc.dialogueSet][gp.ui.npc.dialogueIndex] == null){
                scenePhase++;
            }
        }

        // scenePhase == 4
        if(scenePhase == 4) {
            // return to player
            // Search for the dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] != null){
                    if(gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
                        // RESTOR Player porition
                        gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                        gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                        // Delete the dummy
                        gp.npc[gp.currentMap][i] = null;
                        break;
                    }
                }
            }

            // Start drawing the player again
            gp.player.drawing = true;
            // reset
            scenePhase = 0;
            sceneNum = 0;
            gp.gameState = gp.playState;
            gp.stopMusic();
            gp.playMusic(24);
        }
    }
    public void scene_ending() {

        // scenePhase == 0
        if (scenePhase == 0) {

            gp.stopMusic();
            gp.ui.npc = new OBJ_TownChest(gp);

            scenePhase++;
        }

        // scenePhase == 1
        if (scenePhase == 1) {
            // display dialogues
            gp.ui.drawDialogueScreen();
        }

        // scenePhase == 2
        if (scenePhase == 2) {
            // Play fanfare
            gp.playSE(4);
            scenePhase++;
        }

        // scenePhase == 3
        if (scenePhase == 3) {
            // Wait for music to end using the  counterReached(int target)
            if( counterReached(480) == true){

                scenePhase++;
            }
        }

        // scenePhase == 4
        if (scenePhase == 4) {
            // Wait for music to end using the  counterReached(int target)

                // The Screen gets darker
                alpha += 0.005f;
                if(alpha > 1f){
                    alpha = 1f;
                }
                drawBlackBackground(alpha);
                if (alpha == 1f){
                    alpha = 0;
                    scenePhase++;
                }
            }

        // scenePhase == 5
        if (scenePhase == 5) {
            // Remove boss room NPCs after defeat to clean saved state
            int mapNum = 4;  // Boss room
            for (int i = 0; i < 4; i++) {  // Assuming indices 0-3 for the four girls
                if (gp.npc[mapNum][i] != null &&
                        (gp.npc[mapNum][i].name.equals("Blue Girl") ||  // Adjust names as per your NPC classes
                                gp.npc[mapNum][i].name.equals("Red Girl") ||
                                gp.npc[mapNum][i].name.equals("Yellow Girl") ||
                                gp.npc[mapNum][i].name.equals("Green Girl"))) {
                    gp.npc[mapNum][i] = null;
                }
            }

            drawBlackBackground(1f);
            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }

            String text = "Huzzah, ye dafty champion! Ye thrashed that golom lord\n"
                    + "like it owed ye money! The tiny terrors are free, sprintin’ home\n"
                    + "to raid the biscuit tin, and ye pried open the Town's Treasure Chest\n"
                    + "—stuffed with more glitters than a dragon's den!";

            drawString(alpha, 30f, 200, text, 70);

            if(counterReached(900)){
                scenePhase++;
                gp.playMusic(0);
            }
        }

        // scenePhase == 6
        if (scenePhase == 6) {

            drawBlackBackground(1f);

            drawString(1f, 120f,  gp.screenHeight/2, "Adventure Game", 40);

            if(counterReached(480)){
                scenePhase++;
            }
        }
        // scenePhase == 7
        if (scenePhase == 7) {

            y = gp.tileSize*2;
            drawBlackBackground(1f);
            drawString(1f, 38f,  y, endCredit, 40);

            if(counterReached(200)){
                scenePhase++;
            }
        }
          // scenePhase == 8
        if (scenePhase == 8) {
            drawBlackBackground(1f);
            y--;
            drawString(1f, 38f, y, endCredit, 40);
            if (y == -850) {
                gp.currentMap = 1;
                gp.player.setDefaultPositions();
                gp.saveLoad.save();
                y = 0;
                gp.resetGame(true);
                gp.gameState = gp.titleState;
                gp.currentMap = 1;

                // Reset cutscene state to prevent resumption on load
                sceneNum = NA;
                scenePhase = 0;
                counter = 0;  // Optional: Reset counter if it is also persisted

            }
        }
    }
    public boolean counterReached(int target){
        boolean counterReached = false;
        counter++;

            if (counter > target){
                counterReached = true;
                counter = 0;
            }
        return counterReached;
    }
    public void drawBlackBackground(float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for(String line: text.split("\n")){
            int x = gp.ui.getXForCenter(line);
            g2.drawString(line, x,y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
