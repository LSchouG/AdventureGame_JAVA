package adventuregame.environment;

import adventuregame.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    public int dayCounter;
    public float filterAlpha = 0f;

    // DAY STATE
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;
    // DURATION IN FRAMES (assuming 60 FPS)
    final int DAY_DURATION   = 36000; // 1 min
    final int DUSK_DURATION  = 36000;// 1 min
    final int NIGHT_DURATION = 36000; // 1 min
    final int DAWN_DURATION  = 36000; // 1 min

    public Lighting (GamePanel gp){
        this.gp = gp;
    }

    public  void update(){
        if (dayState == day){
            dayCounter++;
            if (dayCounter > DAY_DURATION){
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if (dayState == dusk){
            filterAlpha += 1.0f / DUSK_DURATION;
            if (filterAlpha > 1f){
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if (dayState == night){
            dayCounter++;
            if (dayCounter > NIGHT_DURATION){
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if (dayState == dawn){
            filterAlpha -= 1.0f / DAWN_DURATION;
            if (filterAlpha < 0f){
                filterAlpha = 0f;
                dayState = day;
            }
        }
    }

    public void resetDay(){
        dayState = day;
        filterAlpha = 0f;
    }

    public void draw(Graphics2D g2){
        // 1) Remember the original composite so we can restore it.
        Composite origComposite = g2.getComposite();

        // 2) Day/Night filter: only on the outside map
        if (gp.currentArea == gp.outSide) {
            float alpha = filterAlpha;  // 0.0–1.0
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(new Color(0f, 0f, 0f));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setComposite(origComposite);
        }

        // 3) Dungeon darkness: only in the dungeon map
        if (gp.currentArea == gp.dungeon) {
            int cx = gp.screenWidth  / 2;
            int cy = gp.screenHeight / 2;
            int radius = (gp.player.currentLight != null)
                    ? gp.player.currentLight.lightRadius
                    : 0;

            if (radius == 0) {
                // Full darkness
                g2.setColor(new Color(0f, 0f, 0.1f, 0.94f));
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            } else {
                // Radial light gradient
                float[] fractions = {0f, 0.8f, 1f};
                Color[]  colors    = {
                        new Color(0f, 0f, 0.1f, 0.1f),
                        new Color(0f, 0f, 0.1f, 0.7f),
                        new Color(0f, 0f, 0.1f, 0.94f)
                };

                RadialGradientPaint paint =
                        new RadialGradientPaint(cx, cy, radius, fractions, colors);
                g2.setPaint(paint);
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            }

            // Restore default composite & paint
            g2.setComposite(origComposite);
            g2.setPaint(null);
        }

        // 4) Draw UI text (always after overlays)
        String dayTime;
        switch (dayState) {
            case day:  dayTime = "Time: Day";  break;
            case dusk: dayTime = "Time: Dusk"; break;
            case night:dayTime = "Time: Night";break;
            default:   dayTime = "Time: Dawn"; break;
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.black);
        g2.drawString(dayTime, gp.tileSize*18 - gp.tileSize/2, gp.tileSize);
        g2.setColor(Color.white);
        g2.drawString(dayTime, gp.tileSize*18 - gp.tileSize/2 - 1, gp.tileSize - 1);
    }

}