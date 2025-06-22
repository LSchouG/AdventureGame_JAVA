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

package dev.adventuregame;

import dev.adventuregame.entity.NPC_OldMan;
import dev.adventuregame.monster.MON_GreenSlime;
import dev.adventuregame.objects.OBJ_Boots;
import dev.adventuregame.objects.OBJ_Chest;
import dev.adventuregame.objects.OBJ_Door;
import dev.adventuregame.objects.OBJ_Key;

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
        // Example:
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tileSize * 24;
        gp.obj[0].worldY = gp.tileSize * 19;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = gp.tileSize * 21;
        gp.obj[1].worldY = gp.tileSize * 25;
    }

    /**************************************************************************
     * Method: setNPC()
     * Purpose: Instantiates and places NPCs in the game world.
     * Notes: Currently places 1 old man NPC at tile (21, 21)
     ***************************************************************************/
    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;

        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = gp.tileSize * 39;
        gp.npc[1].worldY = gp.tileSize * 28;


        gp.npc[3] = new NPC_OldMan(gp);
        gp.npc[3].worldX = gp.tileSize * 40;
        gp.npc[3].worldY = gp.tileSize * 29;

    }
    public void setMonster(){
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize * 21;
        gp.monster[0].worldY = gp.tileSize * 22;

    }
}
