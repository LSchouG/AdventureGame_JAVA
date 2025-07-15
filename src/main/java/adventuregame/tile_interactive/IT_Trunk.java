package adventuregame.tile_interactive;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

public class IT_Trunk extends InteractiveTile{

    GamePanel gp;
    public  IT_Trunk(GamePanel gp, int col,int row) {
        super(gp, col, row);
        this.gp = gp;

        name = "Trunk";
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/images/objects_interactive/trunk.png", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 2;
    }
    public boolean isCurrentItem(Entity entity){
        boolean isCurrentItem = false;
        if (entity.currentWeapon.type == type_axe){
            isCurrentItem = true;
        }
        return isCurrentItem;
    }
    public void playSE(){
        // TO DO
        // IMPLEMENT SOUND EFFECTS FOR TREES
        //  EXAMPLE  gp.playSE(1);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
}
