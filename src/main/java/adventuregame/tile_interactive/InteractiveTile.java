package adventuregame.tile_interactive;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class InteractiveTile extends Entity{
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }
    public void update(){

    }
}
