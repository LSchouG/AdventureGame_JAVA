/**
 * *****************************************************************************
 * FileName: UI.java
 * Purpose: Handles user interface rendering, such as messages and pause screen.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Renders in-game text using Graphics2D
 * - Supports center alignment, play/pause states, and messages
 *******************************************************************************/

package adventuregame;

import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {

    // UI States
    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;
    BufferedImage fullHeart, halfHeart, blankHeart;
//    public String currentDialogue = "";
//    public int commandNumber = 0;
    ArrayList<String> massage = new ArrayList<>();
    ArrayList<Integer> massageCounter = new ArrayList<>();
    public int titleScreenState = 0; // 0: first screen 1: is the second screen ect.
    GamePanel gp;
    Graphics2D g2;
    Font titelFont, menuFontP, menuFontB, textFont;
//    int massageCounter = 0;

    /**************************************************************************
     * Constructor: UI(GamePanel gp)
     * Purpose: Initializes fonts and sets reference to the main game panel.
     ***************************************************************************/
    public UI(GamePanel gp) {
        this.gp = gp;
        //Title Font
        titelFont = new Font("Arial", Font.BOLD, 70);
        // Menu PLAIN Font
        menuFontP = new Font("Arial", Font.PLAIN, 30);
        // Menu BOLD Font
        menuFontB = new Font("Arial", Font.BOLD, 30);
        // Text Font
        textFont = new Font("Arial", Font.PLAIN, 20);


        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        fullHeart = heart.image1;
        halfHeart = heart.image2;
        blankHeart = heart.image3;

    }

    /**************************************************************************
     * Method: showMessage(String text)
     * Purpose: Enables a temporary on-screen message to the player.
     * Inputs: text - the message string to display
     ***************************************************************************/
    public void AddMessage(String text) {
        massage.add(text);
        massageCounter.add(0);
    }

    /**************************************************************************
     * Method: draw(Graphics2D g2)
     * Purpose: Renders the UI elements depending on game state.
     * Inputs: g2 - the graphics context used for drawing
     ***************************************************************************/
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(textFont);
        g2.setColor(Color.WHITE);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(gp);
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }
    }
    /**************************************************************************
     * Method: drawPlayerLife()
     * Purpose:
     ***************************************************************************/
    public void drawPlayerLife(){


        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        while(i < gp.player.maxLife/2){
            g2.drawImage(blankHeart,x,y,null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        while(i < gp.player.life){
            g2.drawImage(halfHeart,x,y,null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(fullHeart,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void drawMessage(){

        int massageX = gp.tileSize;
        int massageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,32f));
    }


    /**************************************************************************
     * Method: drawTitleScreen()
     * Purpose:
     ***************************************************************************/
    public void drawTitleScreen(GamePanel gp) {

        // First screen
        if (titleScreenState == 0) {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(titelFont);
            String text = "ADVENTURE GAME";
            int x = getXForCenter(text);
            int y = gp.tileSize * 3;
            //SHADOW
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 3, y + 3);

            // MAIN COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // IMAGEs
            x = (gp.screenWidth / 2) - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.downStill, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU
            g2.setFont(menuFontP);
            text = "NEW GAME";
            x = getXForCenter(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "LOAD GAME";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if (commandNumber == 1) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "QUITE";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if (commandNumber == 2) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }
        }
        else if (titleScreenState == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(titelFont);

            String text = "Select your class!";
            int x = getXForCenter(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);


            g2.setFont(menuFontP);
            text = "fighter";
            x = getXForCenter(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "Theif";
            x = getXForCenter(text);
            y += gp.tileSize * 1;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "Sorcerer";
            x = getXForCenter(text);
            y += gp.tileSize * 1;
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "Back";
            x = getXForCenter(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNumber == 3) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }
        }

    }

    /**************************************************************************
     * Method: drawSDialogueScreen()
     * Purpose:
     ***************************************************************************/
    public void drawDialogueScreen() {
        // window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(textFont);
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 30;

            gp.player.attackCanceled = true;
        }

    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void drawCharacterScreen(){
        // CREATE A FRAME FOR VIEWING THE ATTRIBUTES OF THE CHARACTER
        final int frameX = gp.tileSize * 1;
        final int frameY = gp.tileSize * 1;
        final int frameWidth = (gp.screenWidth/2) - (gp.tileSize * 2);
        final int frameHeight = gp.screenHeight - (gp.tileSize * 2);
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + 45;
        final int lineHeight = 39;

        // NAME
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Gold", textX, textY);
        textY += lineHeight + 5;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 7;
        g2.drawString("Shield", textX, textY);

        // RESET
        int tailX = (frameX+frameWidth) -30;
        textY = frameY + 45;
        String value;

        // VALUES
        value = String.valueOf(gp.player.level);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.exp );
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf( gp.player.nextLevelExp);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.gold);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += 10;

        // weapon images
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);


    }

    /**************************************************************************
     * Method: drawSubWindow()
     * Purpose:
     ***************************************************************************/
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 180);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    /**************************************************************************
     * Method: drawPauseScreen()
     * Purpose: Displays the "PAUSED" screen centered on screen.
     ***************************************************************************/
    public void drawPauseScreen() {

        g2.setFont(titelFont);
        String text = "PAUSED";
        int x = getXForCenter(text);
        int y = getYForCenter();
        g2.drawString(text, x, y);
    }

    /**************************************************************************
     * Method: getXForCenter(String text)
     * Purpose: Calculates the X-coordinate to center text horizontally.
     * Inputs: text - the string to center
     * Outputs: x-coordinate for centered text
     ***************************************************************************/
    public int getXForCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth / 2) - (length / 2);
    }
    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     * Outputs:
     ***************************************************************************/
    public int getXForAlignTextToRight(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (tailX - length);
    }

    /**************************************************************************
     * Method: getYForCenter()
     * Purpose: Calculates the Y-coordinate to vertically center text.
     * Outputs: y-coordinate for vertically centered text
     ***************************************************************************/
    public int getYForCenter() {
        FontMetrics fm = g2.getFontMetrics();
        return (gp.screenHeight / 2) - (fm.getHeight() / 2) + fm.getAscent();
    }
}
