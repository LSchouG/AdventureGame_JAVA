package dev.adventuregame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import dev.adventuregame.objects.OBJ_Key;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_30, arial860B;
//  BufferedImage keyImage;
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
//      OBJ_Key key = new OBJ_Key(gp);
//      keyImage = key.image;
    } // END UI CONSTRUCTOR

    public void showMessage(String text) {
        message = text;
        messageOn = true;

    } // END showMessage

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_30);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.playState) {
            //DO STUFF LATER
        }
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
        }


    } // END draw


    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenter(text);
        int y = getYForCenter();  // Adjusted below

        g2.drawString(text, x, y);
    }

    public int getXForCenter(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth / 2) - (length / 2);
    }

    public int getYForCenter(){
        FontMetrics fm = g2.getFontMetrics();
        return (gp.screenHeight / 2) - (fm.getHeight() / 2) + fm.getAscent();
    }






} // END class UI
