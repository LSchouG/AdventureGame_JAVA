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

import adventuregame.entity.*;
import adventuregame.monster.*;
import adventuregame.objects.*;
import adventuregame.data.Progress;
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
        int i = 0;    int mapNum = 0; /*
        gp.obj[mapNum][i] = new OBJ_Door(gp);           // DOOR
        gp.obj[mapNum][i].worldX = gp.tileSize * 59;
        gp.obj[mapNum][i].worldY = gp.tileSize * 16;
        i++;*/
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
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

        // PLACE OBJECTS ON MAP 1                       Home
        i = 0;    mapNum = 1;
        gp.obj[mapNum][i] = new OBJ_Chest(gp); // Starter items
        gp.obj[mapNum][i].setLoot(new OBJ_Shield_Wood(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 15;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp); // Starter items
        gp.obj[mapNum][i].setLoot(new OBJ_Sword_Wood(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 16;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;


        // PLACE OBJECTS ON MAP 3                            TOWN


        // PLACE OBJECTS ON MAP 4                                BOSS
        mapNum = 4;   i = 0;
        gp.obj[mapNum][i] = new OBJ_IronDoor(gp);                   // BOSS DOOR
        gp.obj[mapNum][i].worldX = gp.tileSize * 27;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;

        // PLACE OBJECTS ON MAP 5                             DUNGEON
        mapNum = 5;   i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);                       // IRIDIUM SHIELD
        gp.obj[mapNum][i].setLoot(new OBJ_Shield_Iridium(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 29;
        gp.obj[mapNum][i].worldY = gp.tileSize * 54;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);                       // PICKAXE
        gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 76;
        gp.obj[mapNum][i].worldY = gp.tileSize * 59;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);                       // IRIDIUM SHIELD
        gp.obj[mapNum][i].setLoot(new OBJ_Sword_Iridium(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 46;
        i++;/*
        gp.obj[mapNum][i] = new OBJ_IronDoor(gp);                   // BOSS DOOR
        gp.obj[mapNum][i].worldX = gp.tileSize * 79;
        gp.obj[mapNum][i].worldY = gp.tileSize * 36;
        i++;*/
        gp.obj[mapNum][i] = new OBJ_Chest(gp);                      // HEART
        gp.obj[mapNum][i].setLoot(new OBJ_Heart(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 45;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);                      // HEART
        gp.obj[mapNum][i].setLoot(new OBJ_Heart(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);                      // MANA
        gp.obj[mapNum][i].setLoot(new OBJ_Crystal(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;


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




        // PLACE NPC ON MAP 3                             Villages
        mapNum = 3;
        i = 0;

        gp.npc[mapNum][i] = new NPC_OldManStill(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 18;
        gp.npc[mapNum][i].worldY = gp.tileSize * 30;

        if(Progress.bossDeleated == true){
            gp.npc[mapNum][i] = new NPC_GirlBlue(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 18;
            gp.npc[mapNum][i].worldY = gp.tileSize * 35;
            i++;
            gp.npc[mapNum][i] = new NPC_GirlRed(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 25;
            gp.npc[mapNum][i].worldY = gp.tileSize * 35;
            i++;
            gp.npc[mapNum][i] = new NPC_GirlYellow(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 21;
            gp.npc[mapNum][i].worldY = gp.tileSize * 25;
            i++;
            gp.npc[mapNum][i] = new NPC_GirlGreen(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 24;
            gp.npc[mapNum][i].worldY = gp.tileSize * 20;
        }


        // PLACE OBJECTS ON MAP 4                            BOSS ROOM
        mapNum = 4;   i = 0;

        if(Progress.bossDeleated == false) {
            gp.npc[mapNum][i] = new NPC_GirlBlue(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 25;
            gp.npc[mapNum][i].worldY = gp.tileSize * 12;
            i++;
            gp.npc[mapNum][i] = new NPC_GirlRed(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 26;
            gp.npc[mapNum][i].worldY = gp.tileSize * 12;
            i++;
            gp.npc[mapNum][i] = new NPC_GirlYellow(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 27;
            gp.npc[mapNum][i].worldY = gp.tileSize * 12;
            i++;
            gp.npc[mapNum][i] = new NPC_GirlGreen(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * 28;
            gp.npc[mapNum][i].worldY = gp.tileSize * 12;
        }
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


        // PLACE OBJECTS ON MAP OldWoman.csv", 6                            DUNGEON
        mapNum = 6;   i = 0;
        gp.npc[mapNum][i] = new NPC_OldWoman(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 17;
        gp.npc[mapNum][i].worldY = gp.tileSize * 22;

        // PLACE OBJECTS ON MAP OldMan.csv", 7                          DUNGEON
        mapNum = 7;   i = 0;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 15;
        gp.npc[mapNum][i].worldY = gp.tileSize * 22;

        // PLACE OBJECTS ON MAP OldWomanAndMan.csv", 8                            DUNGEON
        mapNum = 8;   i = 0;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 15;
        gp.npc[mapNum][i].worldY = gp.tileSize * 22;
        i++;
        gp.npc[mapNum][i] = new NPC_OldWoman(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 17;
        gp.npc[mapNum][i].worldY = gp.tileSize * 22;
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

        // PLACE MONSTER ON MAP 4                             Boss
        mapNum = 4;   i = 0;
        if(Progress.bossDeleated == false){
            gp.monster[mapNum][i] = new MON_BOSS(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 28;
            gp.monster[mapNum][i].worldY = gp.tileSize * 21;
        }

        // PLACE MONSTER ON MAP 5                             DUNGEON
        mapNum = 5;   i = 0;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 42; // before IRIDIUM SHIELD
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 44; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 48; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 46;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 21; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 54;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 63;
        i++;
        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 76;  // before PickAxe
        gp.monster[mapNum][i].worldY = gp.tileSize * 57;
        i++;
        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 72; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 58;
        i++;
        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 72; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 40;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 20; // before IRIDIUM SWORD
        gp.monster[mapNum][i].worldY = gp.tileSize * 54;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 63;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 79; // RANDOM
        gp.monster[mapNum][i].worldY = gp.tileSize * 32;
        i++;
        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 79; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 57; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 18;
        i++;
        gp.monster[mapNum][i] = new MON_Ghost(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 15; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 18;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 19; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 41;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 21; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 36; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 58;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 66; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 28;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 58; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 50;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 38; //
        gp.monster[mapNum][i].worldY = gp.tileSize * 49;
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
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 60, 23); i++;   // WALL before IRIDIUM SHIELD
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 61, 23); i++;   // WALL before IRIDIUM SHIELD
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 61, 24); i++;   // WALL before IRIDIUM SHIELD
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 63, 63); i++;   // WALL before IRIDIUM ROCK
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 63, 63); i++;   // WALL before IRIDIUM ROCK
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 63, 64); i++;   // WALL before IRIDIUM ROCK
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 72, 35); i++;   // PETAL PLATES
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 55, 58); i++;   // PETAL PLATES
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 15, 64); i++;   // PETAL PLATES
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 14, 53); i++;   // PETAL PLATES
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 15, 53); i++;   // PETAL PLATES

    }

}
