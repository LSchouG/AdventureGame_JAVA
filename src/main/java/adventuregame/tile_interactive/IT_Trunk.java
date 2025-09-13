package adventuregame.tile_interactive;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.awt.*;

public class IT_Trunk extends InteractiveTile{

    GamePanel gp;
    public static final  String  itName = "Trunk";

    public  IT_Trunk(GamePanel gp, int col,int row) {
        super(gp, col, row);
        this.gp = gp;

        name = itName;
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
