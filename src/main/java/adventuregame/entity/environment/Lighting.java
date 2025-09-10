package adventuregame.entity.environment;

import adventuregame.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting (GamePanel g, int circleSize){
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth,gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = darknessFilter.createGraphics();

        // making the dark screen
        Area screenArea = new Area(new Rectangle2D.Double(0,0, gp.screenWidth, gp.screenHeight));

        int centerX = gp.player.screenX + (gp.tileSize)/2;
        int centerY = gp.player.screenY + (gp.tileSize)/2;

        // finding x and y for the circle
        double x = centerX - (circleSize/2);
        double y = centerY - (circleSize/2);

        // Create a lighting circle
        Shape circleShape = new Ellipse2D.Double(x,y, circleSize, circleSize);
        Area lightArea = new Area(circleShape);

        // Subtrack the light area from the dark area.
        screenArea.subtract(lightArea);
        // Set the color
        g2.setColor(new Color(0, 0, 0, 0.95f));

        // Fill the screen area (excluding the light circle)
        g2.fill(screenArea);

        // Dispose of the graphics context to free resources
        g2.dispose();

    }
}
