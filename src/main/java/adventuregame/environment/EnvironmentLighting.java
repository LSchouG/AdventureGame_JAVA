package adventuregame.environment;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnvironmentLighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public EnvironmentLighting(GamePanel gp) {
        this.gp = gp;
        setLightSource();
    }

    public void setLightSource() {
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        // Fill with full darkness
        g2.setColor(new Color(0, 0, 0.1f, 0.94f));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Gradient setup
        Color[] color = new Color[12];
        float[] fraction = new float[12];
        color[0] = new Color(0, 0, 0.1f, 0.1f);
        color[1] = new Color(0, 0, 0.1f, 0.42f);
        color[2] = new Color(0, 0, 0.1f, 0.52f);
        color[3] = new Color(0, 0, 0.1f, 0.61f);
        color[4] = new Color(0, 0, 0.1f, 0.69f);
        color[5] = new Color(0, 0, 0.1f, 0.76f);
        color[6] = new Color(0, 0, 0.1f, 0.81f);
        color[7] = new Color(0, 0, 0.1f, 0.84f);
        color[8] = new Color(0, 0, 0.1f, 0.98f);
        color[9] = new Color(0, 0, 0.1f, 0.90f);
        color[10] = new Color(0, 0, 0.1f, 0.92f);
        color[11] = new Color(0, 0, 0.1f, 0.94f);
        for (int i = 0; i < 12; i++) fraction[i] = i / 11f;

        // Torch lights
        int torchCount = 0;
        for (Entity obj : gp.obj[gp.currentMap]) {
            if (obj != null && obj.lightRadius > 0) {
                int screenX = obj.worldX - gp.player.worldX + gp.player.screenX + gp.tileSize / 2;
                int screenY = obj.worldY - gp.player.worldY + gp.player.screenY + gp.tileSize / 2;
                RadialGradientPaint torchPaint = new RadialGradientPaint(screenX, screenY, obj.lightRadius, fraction, color);
                g2.setPaint(torchPaint);
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
                torchCount++;
            }
        }
        if (torchCount == 0) {
        } else {
        }
        g2.dispose();
    }

    public void update() {
        setLightSource();
        setLightSource();
    }

    public void draw(Graphics2D g2) {
        if (gp.currentArea == gp.outSide || gp.currentArea == gp.dungeon) {
            g2.drawImage(darknessFilter, 0, 0, null);
        }
    }
}
