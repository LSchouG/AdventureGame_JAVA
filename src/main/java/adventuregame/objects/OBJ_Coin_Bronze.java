package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.Random;

public class OBJ_Coin_Bronze extends Entity {
    GamePanel gp;

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Bronze Coin";
        type = type_pickUpOnly;
        value = 1;
        down1 = setup("/images/objects_pickup/coin-bronze.png", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Coin + " + name);
        gp.player.gold += value;
        return true;
    }
}
