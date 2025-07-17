package adventuregame.entity;

import adventuregame.GamePanel;

import java.awt.*;

public class Particale extends Entity{
    GamePanel gp;

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particale(Entity gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd){
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        worldX = generator.worldX;
        worldY = generator.worldY;
    }
}
