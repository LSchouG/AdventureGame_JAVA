/**
 * *****************************************************************************
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

    // Movement keys
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    // Debugging
    public boolean checkDrawTime = false;
    GamePanel gp;

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

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNumber--;
                    if (gp.ui.commandNumber < 0) {
                        gp.ui.commandNumber = 2;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNumber++;
                    if (gp.ui.commandNumber > 2) {
                        gp.ui.commandNumber = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                    if (gp.ui.commandNumber == 0) {
                        gp.ui.titleScreenState = 1;
                    } else if (gp.ui.commandNumber == 1) {


                        // ADD LATER


                    } else if (gp.ui.commandNumber == 2) {
                        System.exit(0);
                    }
                }
            } else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNumber--;
                    if (gp.ui.commandNumber < 0) {
                        gp.ui.commandNumber = 3;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNumber++;
                    if (gp.ui.commandNumber > 3) {
                        gp.ui.commandNumber = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                    if (gp.ui.commandNumber == 0) {
                        //Figther
                        gp.gameState = gp.playState;
                        gp.playMusic(0);

                    }
                    else if (gp.ui.commandNumber == 1) {
                        //Theif
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    else if (gp.ui.commandNumber == 2) {
                        //Sorcerer
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    else if (gp.ui.commandNumber == 3) {
                        gp.ui.titleScreenState = 0;
                    }
                }

            }else if (gp.ui.titleScreenState == 2) {

                // ADD LATER

            }



        }

        // PLAT STATE
        if (gp.gameState == gp.playState) {
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
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                enterPressed = true;
            }

            // Toggle play/pause state with P key
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }

            // Debug: Toggle draw time display with T key
            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }


        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.playState;
            }
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
