package adventuregame.tile_interactive;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Coin_Bronze;
import adventuregame.objects.OBJ_Crystal;
import adventuregame.objects.OBJ_Heart;

import java.awt.*;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTile{

    GamePanel gp;
    public  IT_DestructibleWall (GamePanel gp, int col,int row) {
        super(gp, col, row);
        this.gp = gp;

        name = "Trunk";
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/images/objects_interactive/destructibleWall.png", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }
    public boolean isCurrentItem(Entity entity){
        boolean isCurrentItem = false;
        if (entity.currentWeapon.type == type_pickaxe){
            isCurrentItem = true;
        }
        return isCurrentItem;
    }
    public void playSE(){
        // TO DO
        // IMPLEMENT SOUND EFFECTS FOR TREES
        //  EXAMPLE
        gp.playSE(22);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
    public Color getParticalColor(){
        Color color = new Color(65,65,65);
        return color;
    }
    public int getParticleSize(){
        int size = 6;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 15;
        return maxLife;
    }
    /*   CAN BE USE TO MAKE IT DROP A ITEM
    public void checkDrop(){
        // CAST A DIE
        int i = new Random().nextInt(100)+1;

        // SET THE MONSTER DROP
        if (i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        } else if (i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        } else if (i >= 75){
            dropItem(new OBJ_Crystal(gp));
        }
    }*/
}

