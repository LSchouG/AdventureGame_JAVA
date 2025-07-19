package adventuregame.entity;

import adventuregame.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd){
        super(gp);
        
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gp.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    public void update(){
        worldX += xd;
        worldY += yd;
        life--;
        if(life <= 0){
            alive = false;
        }
        if (life < maxLife / 2) {
        int counter = 0;
        if (counter < 2) {
            counter = 0;
            yd++;
        }
        }
    }

    public void draw(Graphics2D g){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g.setColor(color);
        g.fillRect(screenX, screenY, size, size);
    }
}