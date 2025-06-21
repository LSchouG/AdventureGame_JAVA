/** ******************************************************************************
 * FileName: UI.java
 * Purpose: Handles user interface rendering, such as messages and pause screen.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Renders in-game text using Graphics2D
 * - Supports center alignment, play/pause states, and messages
 *******************************************************************************/

package dev.adventuregame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_30, arial860B;

    // UI States
    public boolean messageOn = false;
    public String message = "";
    int massageCounter = 0;
    public boolean gameFinished = false;

    // Timer
    double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    /**************************************************************************
     * Constructor: UI(GamePanel gp)
     * Purpose: Initializes fonts and sets reference to the main game panel.
     ***************************************************************************/
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial860B = new Font("Arial", Font.BOLD, 80);

        // Example for displaying an image (commented out)
        // OBJ_Key key = new OBJ_Key(gp);
        // keyImage = key.image;
    }

    /**************************************************************************
     * Method: showMessage(String text)
     * Purpose: Enables a temporary on-screen message to the player.
     * Inputs: text - the message string to display
     ***************************************************************************/
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    /**************************************************************************
     * Method: draw(Graphics2D g2)
     * Purpose: Renders the UI elements depending on game state.
     * Inputs: g2 - the graphics context used for drawing
     ***************************************************************************/
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_30);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            // In-game UI rendering will go here (e.g., timer, inventory, etc.)
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    /**************************************************************************
     * Method: drawPauseScreen()
     * Purpose: Displays the "PAUSED" screen centered on screen.
     ***************************************************************************/
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
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
     * Method: getYForCenter()
     * Purpose: Calculates the Y-coordinate to vertically center text.
     * Outputs: y-coordinate for vertically centered text
     ***************************************************************************/
    public int getYForCenter() {
        FontMetrics fm = g2.getFontMetrics();
        return (gp.screenHeight / 2) - (fm.getHeight() / 2) + fm.getAscent();
    }
}
