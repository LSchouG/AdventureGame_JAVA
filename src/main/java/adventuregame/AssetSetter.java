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

import adventuregame.entity.NPC_BigRock;
import adventuregame.entity.NPC_Seller;
import adventuregame.monster.MON_GreenSlime;
import adventuregame.monster.MON_RedSlime;
import adventuregame.monster.MON_Spider;
import adventuregame.objects.*;
import adventuregame.tile_interactive.IT_DestructibleWall;
import adventuregame.tile_interactive.IT_DryTree;
import adventuregame.tile_interactive.IT_MetalPlate;
import adventuregame.tile_interactive.IT_Trunk;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {

        // PLACE OBJECTS ON MAP 0                        WORLDMAP
        int i = 0;    int mapNum = 0;
        gp.obj[mapNum][i] = new OBJ_Door(gp);           // DOOR
        gp.obj[mapNum][i].worldX = gp.tileSize * 59;
        gp.obj[mapNum][i].worldY = gp.tileSize * 16;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);          // BOOTS
        gp.obj[mapNum][i].setLoot(new OBJ_Boots(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 68;
        gp.obj[mapNum][i].worldY = gp.tileSize * 59;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);          // AXE
        gp.obj[mapNum][i].setLoot(new OBJ_Axe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 63;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);          // KEY
        gp.obj[mapNum][i].setLoot(new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 72;
        gp.obj[mapNum][i].worldY = gp.tileSize * 46;



        // PLACE OBJECTS ON MAP 3                            TOWN
        mapNum = 3;   i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        OBJ_Potion_Red potionRed = new OBJ_Potion_Red(gp);
        potionRed.amount = 5;
        gp.obj[mapNum][i].setLoot(potionRed);
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 31;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Axe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 63;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Boots(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 68;
        gp.obj[mapNum][i].worldY = gp.tileSize * 59;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 19;
        gp.obj[mapNum][i].worldY = gp.tileSize * 31;


        // PLACE OBJECTS ON MAP 5                             DUNGEON
        mapNum = 5;   i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);              // POTIONS
        gp.obj[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 29;
        gp.obj[mapNum][i].worldY = gp.tileSize * 54;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);              // PICKAXE
        gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 76;
        gp.obj[mapNum][i].worldY = gp.tileSize * 59;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);                // SWORD
        gp.obj[mapNum][i].setLoot(new OBJ_Sword_Iridium(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 46;
        i++;
        OBJ_Potion_Red potionRed5 = new OBJ_Potion_Red(gp);
        potionRed5.amount = 2;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);              // POTIONS
        gp.obj[mapNum][i].setLoot(potionRed5);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[mapNum][i] = new OBJ_IronDoor (gp);           // BOSS DOOR
        gp.obj[mapNum][i].worldX = gp.tileSize * 79;
        gp.obj[mapNum][i].worldY = gp.tileSize * 36;


    }
    public void setNPC() {

        // PLACE NPC ON MAP 0

        int i = 0;
        int mapNum = 0;


        // PLACE NPC ON MAP 2
        mapNum = 2;
        i = 0;

        gp.npc[mapNum][i] = new NPC_Seller(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 16;
        gp.npc[mapNum][i].worldY = gp.tileSize * 20;

        // PLACE OBJECTS ON MAP 5                             DUNGEON
        mapNum = 5;   i = 0;

        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 65;
        gp.npc[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 60;
        gp.npc[mapNum][i].worldY = gp.tileSize * 61;
        i++;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 16;
        gp.npc[mapNum][i].worldY = gp.tileSize * 58;
    }
    public void setMonster(){

        // PLACE MONSTERS ON MAP 0
        int i = 0;   int mapNum = 0;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 19;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 32;
        gp.monster[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 56;
        i++;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 17;
        gp.monster[mapNum][i].worldY = gp.tileSize * 62;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 48;
        i++;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 30;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;
        gp.monster[mapNum][i] = new MON_Spider(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 53;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;
        i++;
        gp.monster[mapNum][i] = new MON_Spider(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 53;
        gp.monster[mapNum][i].worldY = gp.tileSize * 55;
        i++;
        i++;
        gp.monster[mapNum][i] = new MON_Spider(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 46;
        gp.monster[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        i++;
        gp.monster[mapNum][i] = new MON_Spider(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 47;
        gp.monster[mapNum][i].worldY = gp.tileSize * 54;
        i++;
        i++;
        gp.monster[mapNum][i] = new MON_Spider(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 50;
        gp.monster[mapNum][i].worldY = gp.tileSize * 48;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 55;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 46;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;

        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 48;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 54;
        gp.monster[mapNum][i].worldY = gp.tileSize * 28;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 54;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;

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
        int i = 0;  int mapNum = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 67, 60); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 66, 60); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 65, 60); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 64, 60); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 63, 60); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 62, 60); i++;
        gp.iTile[mapNum][i] = new IT_Trunk(gp, 61, 60); i++;

        // PLACE OBJECTS ON MAP 5                             DUNGEON
        mapNum = 5;   i = 0;

        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 28, 48); i++;   // WALL before BossKEY
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 27, 48); i++;   // WALL before BossKEY
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 27, 49); i++;   // WALL before BossKEY
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 28, 49); i++;   // WALL before BossKEY
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 37, 60); i++;   // WALL before IRIDIUM SWORD
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 60); i++;   // WALL before IRIDIUM SWORD
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 37, 59); i++;   // WALL before IRIDIUM SWORD
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 37, 59); i++;   // WALL before IRIDIUM SWORD
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 72, 35); i++;   // PETAL PLATES
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 55, 58); i++;   // PETAL PLATES
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 15, 64); i++;   // PETAL PLATES

    }

}
