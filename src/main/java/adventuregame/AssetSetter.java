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
import adventuregame.monster.MON_GreenSlime;
import adventuregame.monster.MON_RedSlime;
import adventuregame.objects.*;

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
        int i = 0;


        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 20;
        gp.obj[i].worldY = gp.tileSize * 19;
        i++;
        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = gp.tileSize * 21;
        gp.obj[i].worldY = gp.tileSize * 24;
        i++;
        gp.obj[i] = new OBJ_Red_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 21;
        gp.obj[i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[i] = new OBJ_Red_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 22;
        gp.obj[i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[i] = new OBJ_Red_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 23;
        gp.obj[i].worldY = gp.tileSize * 29;



    }

    /**************************************************************************
     * Method: setNPC()
     * Purpose: Instantiates and places NPCs in the game world.
     * Notes: Currently places 1 old man NPC at tile (21, 21)
     ***************************************************************************/
    public void setNPC() {
        int i = 0;
        gp.npc[i] = new NPC_OldMan(gp);
        gp.npc[i].worldX = gp.tileSize * 25;
        gp.npc[i].worldY = gp.tileSize * 21;
        i++;
        gp.npc[i] = new NPC_OldMan(gp);
        gp.npc[i].worldX = gp.tileSize * 39;
        gp.npc[i].worldY = gp.tileSize * 28;
        i++;
        gp.npc[i] = new NPC_OldMan(gp);
        gp.npc[i].worldX = gp.tileSize * 40;
        gp.npc[i].worldY = gp.tileSize * 29;

    }
    public void setMonster(){

        int i = 0;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 20;
        gp.monster[i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 21;
        gp.monster[i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 22;
        gp.monster[i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[i] = new MON_RedSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[i] = new MON_RedSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 24;
        gp.monster[i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[i] = new MON_RedSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 25;
        gp.monster[i].worldY = gp.tileSize * 30;

    }
}
