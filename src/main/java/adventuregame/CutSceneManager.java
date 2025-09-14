package adventuregame;

import adventuregame.entity.Player;
import adventuregame.entity.PlayerDummy;
import adventuregame.monster.MON_BOSS;
import adventuregame.objects.OBJ_IronDoor;

import java.awt.*;

public class CutSceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    // Scene Numbers
    public final  int NA = 0;
    public final int boss = 1;

    public CutSceneManager(GamePanel gp){
        this.gp = gp;

    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        // SWITCH BETWEEN SECENE
        switch (sceneNum) {
            case boss: scene_Boss(); break;
        }
    }
    public void scene_Boss(){
        if(scenePhase == 0){
            gp.bossBattleOn = true;

            // SHUT THE DOOR
            for(int i = 0; i < gp.obj[1].length; i++){
                if(gp.obj[gp.currentMap][i] == null){
                    gp.obj[gp.currentMap][i] = new OBJ_IronDoor(gp);                   // BOSS DOOR
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
        if(scenePhase == 1){

            gp.player.worldY -= 2;
            if (gp.player.worldY == gp.tileSize*22){
                scenePhase++;
            }
        }
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
        if(scenePhase == 3){
            gp.ui.drawDialogueScreen();

            // Wait until dialogue is finished before advancing
            if(gp.ui.npc.dialogues[gp.ui.npc.dialogueSet][gp.ui.npc.dialogueIndex] == null){
                scenePhase++;
            }
        }

        if(scenePhase == 4) {

            // return to player

            // Search for the dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
                    // RESTOR Player porition
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                    // Delete the dummy
                    gp.npc[gp.currentMap][i] = null;
                    break;
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
}
