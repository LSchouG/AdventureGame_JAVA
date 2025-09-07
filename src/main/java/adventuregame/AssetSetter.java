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

    /**************************************************************************
     * Constructor: AssetSetter(GamePanel gp)
     * Purpose: Assigns GamePanel reference to allow asset placement in world.
     ***************************************************************************/
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**************************************************************************
     * Method: setObject()
     * Purpose: Instantiates and places objects in the game world.
     * Notes: Currently empty – expand to add objects like keys, chests, etc.
     ***************************************************************************/
    public void setObject() {

        // PLACE OBJECTS ON MAP 0

        int i = 0;
        int mapNum = 0;

        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22; // 22
        gp.obj[mapNum][i].worldY = gp.tileSize * 24; // 25
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[mapNum][i] = new OBJ_Crystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 23;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;

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

    /**************************************************************************
     * Method:
     * Purpose:
     * Notes:
     ***************************************************************************/
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
    /**************************************************************************
     * Method:
     * Purpose:
     * Notes:
     ***************************************************************************/
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
