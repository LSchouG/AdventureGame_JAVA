package adventuregame.data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // PLAYER STATS
    int level;
    int maxLife;
    int speed;
    int maxMana;
    int strength;
    int dexterity;
    int magic;
    int exp;
    int nextLevelExp;
    int gold;

    // PLAYER INVENTORY
    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<Integer> itemAmount = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    // OBJECT ON MAP
    String mapObjectNames[][];
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    String mapObjectLootName[][];
    boolean mapObjectOpened[][];

    // In DataStorage.java (add these fields)
    public boolean grassDefeated;
    public boolean dungeonDefeated;
    public boolean bossDefeated;

}
