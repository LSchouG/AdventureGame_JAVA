/** ******************************************************************************
 * FileName: KeyHandler.java
 * Purpose: Handles keyboard input for player movement and game state toggling.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Maps WASD keys to movement flags
 * - Handles toggling between play and pause states
 * - Supports debug toggle with T key
 *******************************************************************************/

package dev.adventuregame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    // Movement keys
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    // Debugging
    public boolean checkDrawTime = false;

    /**************************************************************************
     * Constructor: KeyHandler(GamePanel gp)
     * Purpose: Initializes the key handler with reference to the game panel.
     ***************************************************************************/
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**************************************************************************
     * Method: keyTyped(KeyEvent e)
     * Purpose: Required by KeyListener but not used here.
     ***************************************************************************/
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    /**************************************************************************
     * Method: keyPressed(KeyEvent e)
     * Purpose: Responds to key press events for movement, pause, and debug.
     ***************************************************************************/
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Movement Controls (WASD)
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        // Toggle play/pause state with P key
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        // Debug: Toggle draw time display with T key
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    /**************************************************************************
     * Method: keyReleased(KeyEvent e)
     * Purpose: Responds to key release events to stop movement.
     ***************************************************************************/
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
