package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Tent extends Entity {
    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Tent";
        type = type_consumable;
        down1 = setup("/images/objects_pickup/tent.png", gp.tileSize, gp.tileSize);
        Price = 300;
        stackable = true;
        itemTitle = "[" + name + "]";
        itemDescription = "An old tent\nSleep anywhere" ;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.sleepState;
        gp.playSE(16);
        gp.playSE(15);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImages(down1);
        return true; // If TRUE tent use ONE use only
                     // if FALSE tent use if MANY times.
    }
}
