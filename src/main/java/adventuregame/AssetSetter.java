/** ******************************************************************************
 * FileName: AssetSetter.java
 * Purpose: Handles the placement and initialization of game objects and NPCs.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Used by GamePanel during setup to populate the world with assets
 * - Includes both static objects and non-player characters (NPCs)
 *******************************************************************************/

package adventuregame;

import adventuregame.entity.NPC_OldMan;
import adventuregame.entity.NPC_Seller;
import adventuregame.monster.MON_GreenSlime;
import adventuregame.monster.MON_RedSlime;
import adventuregame.objects.*;
import adventuregame.tile_interactive.IT_DryTree;
import adventuregame.tile_interactive.IT_Trunk;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {

        // PLACE OBJECTS ON MAP 0

        int i = 0;
        int mapNum = 0;

        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 60;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 65;
        gp.obj[mapNum][i].worldY = gp.tileSize * 19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 57;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 58;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new  OBJ_Tent(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 56;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new  OBJ_Potion_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 55;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 54;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;

        i++;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;


        // PLACE OBJECTS ON MAP 0
        /*

        int mapNum = 1;
        i = 0;

        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;

         */


    }
    public void setNPC() {

        // PLACE NPC ON MAP 0

        int i = 0;
        int mapNum = 0;

        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 25;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 39;
        gp.npc[mapNum][i].worldY = gp.tileSize * 28;
        i++;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 40;
        gp.npc[mapNum][i].worldY = gp.tileSize * 29;
        i++;

        // PLACE NPC ON MAP 1
        /*
        mapNum = 1;
        i = 0;

        gp.npc[mapNum][i] = new NPC_Seller(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 40;
        gp.npc[mapNum][i].worldY = gp.tileSize * 29;

         */

        // PLACE NPC ON MAP 2
        mapNum = 2;
        i = 0;

        gp.npc[mapNum][i] = new NPC_Seller(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 16;
        gp.npc[mapNum][i].worldY = gp.tileSize * 20;




    }
    public void setMonster(){

        // PLACE MONSTERS ON MAP 0

        int i = 0;
        int mapNum = 0;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 20;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 21;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;

        // PLACE MONSTERS ON MAP 1
        /*

        int mapNum = 1;
        i = 0;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;

         */

    }
    public void setInteractiveTiles() {

        // PLACE InteractiveTiles ON MAP 0

        int i = 0;
        int mapNum = 0;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 19, 25); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 25); i++;
        gp.iTile[mapNum][i] = new IT_Trunk(gp, 18, 25); i++;


        // PLACE InteractiveTiles ON MAP 1
        /*
        int mapNum = 1;
        i = 0;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 19, 25); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 25); i++;
        gp.iTile[mapNum][i] = new IT_Trunk(gp, 18, 25); i++;

         */

    }

}
