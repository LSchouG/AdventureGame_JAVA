package adventuregame.tile_interactive;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.awt.*;

public class IT_DryTree extends InteractiveTile{

    GamePanel gp;
    public IT_DryTree(GamePanel gp, int col,int row) {
        super(gp, col, row);
        this.gp = gp;

        name = "Dry Tree";
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/images/objects_interactive/dry-tree.png", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
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
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }
    public Color getParticalColor(){
        Color color = new Color(65,50,30);
        return color;
    }
    public int getParticalSize(){
        int size = 6;
        return size;
    }
    public int getParticalSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticalMaxLife(){
        int maxLife = 15;
        return maxLife;
    }
}
