package adventuregame.tile_interactive;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class InteractiveTile extends Entity{
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col,int row) {
        super(gp);
        this.gp = gp;
    }
    public boolean isCurrentItem(Entity entity){
        boolean isCurrentItem = false;
        return isCurrentItem;
    }
    public void playSE(){
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }

    public void update(){
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

}
