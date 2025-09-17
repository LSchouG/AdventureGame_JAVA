package adventuregame.environment;

import adventuregame.GamePanel;
// If your Entity class is elsewhere, import it; otherwise remove this.
import adventuregame.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    // DAY STATE
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;


    // DURATION IN FRAMES (assuming 60 FPS)
    final int DAY_DURATION = 10800; //  3 minutes
    final int DUSK_DURATION  = 1800;  // 30 seconds
    final int NIGHT_DURATION = 3600;  // 1 minute
    final int DAWN_DURATION  = 1800;  // 30 seconds

    public Lighting(GamePanel gp){
        this.gp = gp;
        setLightSource();
    }

    public void setLightSource() {

        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1) Fill with base darkness (this is what we will punch holes in)
        g2.setComposite(AlphaComposite.Src); // write solid pixels
        g2.setColor(new Color(0, 0, 0.1f, 0.94f));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // 2) Prepare a "remove darkness" gradient (alpha 1.0 at center -> 0.0 at edge)
        // Using DST_OUT, source alpha removes destination alpha.
        Color[] holeColors = {
                new Color(0f, 0f, 0f, 1f),  // center: fully remove darkness
                new Color(0f, 0f, 0f, 0f)   // edge: no removal
        };
        float[] holeFractions = {0f, 1f};

        // Switch to subtraction mode
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT));

        // 3) Player light (if any)
        if (gp.player != null && gp.player.currentLight != null) {
            int centerX = gp.player.screenX + gp.tileSize / 2;
            int centerY = gp.player.screenY + gp.tileSize / 2;
            int radius  = gp.player.currentLight.lightRadius;

            RadialGradientPaint playerPaint = new RadialGradientPaint(
                    centerX, centerY, radius, holeFractions, holeColors
            );
            g2.setPaint(playerPaint);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        } else {
        }

        // 4) Torches (objects with lightRadius > 0)
        int torchCount = 0;
        if (gp.obj != null && gp.currentMap >= 0 && gp.currentMap < gp.obj.length && gp.obj[gp.currentMap] != null) {
            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                var e = gp.obj[gp.currentMap][i];
                if (e != null && e.lightRadius > 0) {
                    torchCount++;

                    int screenX = e.worldX - gp.player.worldX + gp.player.screenX + gp.tileSize / 2;
                    int screenY = e.worldY - gp.player.worldY + gp.player.screenY + gp.tileSize / 2;

                    RadialGradientPaint torchPaint = new RadialGradientPaint(
                            screenX, screenY, e.lightRadius, holeFractions, holeColors
                    );
                    g2.setPaint(torchPaint);
                    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

                }
            }
        }

        // 5) Cleanup
        g2.setComposite(AlphaComposite.SrcOver);
        g2.dispose();
    }


    public void update() {

        boolean needsRebuild = false;

        // Player light changed
        if (gp.player != null && gp.player.lightUpdated) {
            needsRebuild = true;
            gp.player.lightUpdated = false;
        }

        // In dungeon: check if any torch is near enough to matter
        if (gp.currentArea == gp.dungeon) {
            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].lightRadius > 0) {
                    int dx = gp.obj[gp.currentMap][i].worldX - gp.player.worldX;
                    int dy = gp.obj[gp.currentMap][i].worldY - gp.player.worldY;
                    int distSq = dx*dx + dy*dy;

                    // If torch is within screen (or within radius + margin), rebuild
                    int maxDist = gp.obj[gp.currentMap][i].lightRadius + gp.screenWidth/2;
                    if (distSq < maxDist * maxDist) {
                        needsRebuild = true;
                        break;
                    }
                }
            }
        }
        if (needsRebuild) {
            setLightSource();
        }

        if (dayState == day){
            dayCounter++;
            if (dayCounter > DAY_DURATION){
                dayState = dusk;
                dayCounter = 0;
            }
        } else if (dayState == dusk){
            filterAlpha += 1.0f / DUSK_DURATION;
            if (filterAlpha > 1f){
                filterAlpha = 1f;
                dayState = night;
            }
        } else if (dayState == night){
            dayCounter++;
            if (dayCounter > NIGHT_DURATION){
                dayState = dawn;
                dayCounter = 0;
            }
        } else if (dayState == dawn){
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
        // Outside: only show darkness during dusk/night/dawn (fades with filterAlpha)
        if (gp.currentArea == gp.outSide) {
            if (dayState == day) {
                return; // full daylight, no darkness filter
            } else {
                // Fade in the darkness filter (with lantern holes) outside
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
                g2.drawImage(darknessFilter, 0, 0, null);
            }
        }

        // Dungeon: always draw full darkness with light holes
        if (gp.currentArea == gp.dungeon){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        // Reset composite
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Debug HUD
        String dayTime = switch (dayState){
            case day -> "Time: Day";
            case dusk -> "Time: Dusk";
            case night -> "Time: Night";
            case dawn -> "Time: Dawn";
            default -> "";
        };

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.black);
        g2.drawString(dayTime, (gp.tileSize * 18) - (gp.tileSize/2), gp.tileSize);
        g2.setColor(Color.white);
        g2.drawString(dayTime, (gp.tileSize * 18) - (gp.tileSize/2) - 2, gp.tileSize - 2);
    }
}
