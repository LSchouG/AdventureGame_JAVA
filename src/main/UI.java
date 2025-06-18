package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import objects.OBJ_Key;

public class UI {

    GamePanel gp;
    Font arial_30, arial860B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int massageCounter = 0;
    public boolean gameFinished = false;
    double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial860B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2) {

        if (gameFinished == true) {

            String text;
            int textLength;
            int x;
            int y;

            g2.setFont(arial_30);
            g2.setColor(Color.white);
            text = "Victory! You have found the final treasure!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getBounds().getWidth();
            x = gp.screenWidth / 2 - (textLength / 2);
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(arial_30);
            g2.setColor(Color.white);
            text = "You'r time is " + dFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getBounds().getWidth();
            x = gp.screenWidth / 2 - (textLength / 2);
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial860B);
            g2.setColor(Color.yellow);
            text = "Victory!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getBounds().getWidth();
            x = gp.screenWidth / 2 - (textLength / 2);
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            g2.setColor(Color.gray);
            g2.fillRect(10, 8, 100, 47);

            g2.setFont(arial_30);
            g2.setColor(Color.white);
            g2.setBackground(Color.black);
            g2.drawImage(keyImage, 10, 4, gp.tileSize, gp.tileSize, null);
            g2.drawString(" X " + gp.player.hasKey, 50, 42);

            // TIMER
            playTime += (double) 1 / 60;
            g2.setFont(arial_30);
            g2.setColor(Color.white);
            g2.setBackground(Color.black);
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 13 - (gp.tileSize / 3), gp.tileSize);

            // MESSAGE
            if (messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, 10, gp.tileSize * 2);

                massageCounter++;
                if (massageCounter > 150) {
                    messageOn = false;
                    massageCounter = 0;
                }
            }
        }
    }
}
