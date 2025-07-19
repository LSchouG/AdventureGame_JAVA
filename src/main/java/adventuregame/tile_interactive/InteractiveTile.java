package adventuregame.tile_interactive;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    public void draw(Graphics2D g2) {

        // Calculate screen position based on world coordinates relative to the player
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw if within visible screen bounds
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {


            g2.drawImage(down1, screenX, screenY, null);
        }
    }
}
