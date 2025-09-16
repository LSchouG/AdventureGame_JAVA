package adventuregame.objects;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class OBJ_Coin_Gold extends Entity {
    GamePanel gp;

    public static final String objName ="Gold Coin";

    public OBJ_Coin_Gold(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_pickUpOnly;
        value = 15;
        down1 = setup("/images/objects_pickup/coin-bronze.png", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Coin + " + name);
        gp.player.gold += value;
        return true;
    }
}
