package adventuregame.data;

import adventuregame.GamePanel;

import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();
            // PLAYER STATS
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.speed = gp.player.speed;
            ds.maxMana = gp.player.maxMana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.magic = gp.player.magic;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.gold = gp.player.gold;
            // PLAYER INVENTORY
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemName.add(gp.player.inventory.get(i).name);
                ds.itemAmount.add(gp.player.inventory.get(i).amount);

            }

            // Progress milestones
            ds.grassDefeated = Progress.grassDefeated;
            ds.dungeonDefeated = Progress.dungeonDefeated;
            ds.bossDefeated = Progress.bossDefeated;

            // PLAYER EQUIPMENT SLOT NUMBER
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
            // OBJECT ON MAP
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootName = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NA";
                    } else {
                        String objName = gp.obj[mapNum][i].name;
                        ds.mapObjectNames[mapNum][i] = objName;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        String lootName = (gp.obj[mapNum][i].loot != null) ? gp.obj[mapNum][i].loot.name : null;
                        ds.mapObjectLootName[mapNum][i] = lootName;
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            // WRITE TO DATASTORAGE OBJECT
            oos.writeObject(ds);
            oos.close();
        } catch (Exception e) {
            System.out.println("Save Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            // read the dataStorage object
            DataStorage ds = (DataStorage) ois.readObject();
            ois.close();

            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.speed = ds.speed;
            gp.player.maxMana = ds.maxMana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.magic = ds.magic;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.gold = ds.gold;

            // PLAYER INVENTORY
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemName.size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemName.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmount.get(i);
            }

            // Restore progress milestones
            Progress.grassDefeated = ds.grassDefeated;
            Progress.dungeonDefeated = ds.dungeonDefeated;
            Progress.bossDefeated = ds.bossDefeated;

            // PLAYER EQUIPMENT SLOT NUMBER
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImages();
            // OBJECT ON MAP
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                        gp.obj[mapNum][i] = null;
                    } else {
                        gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if (ds.mapObjectLootName[mapNum][i] != null) {
                            gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectLootName[mapNum][i]));
                        }
                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if (gp.obj[mapNum][i].opened == true) {
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Load Error: " + e.getMessage());
            e.printStackTrace();  // Print full stack for debugging
        }
    }
}