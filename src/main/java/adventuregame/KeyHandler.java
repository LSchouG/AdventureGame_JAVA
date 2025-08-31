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

package adventuregame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // Movement keys
    public boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, enterPressed = false, shotKeyPressed = false;
    // Debugging
    public boolean showDebugText = false;
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
        if (gp.gameState == gp.titleState) {titleState(code);}

        // PLAY STATE
        else if (gp.gameState == gp.playState) {playState(code);}

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {pauseState(code);}

        // DIALOGUE STATE
       else if (gp.gameState == gp.dialogueState) {dialogueState(code);}

        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) {characterState(code);}

        // OPTION STATE
        else if (gp.gameState == gp.optionState) {optionState(code);}
    }
    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void titleState(int code){
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

                    gp.gameState = gp.playState;
                    gp.playMusic(0);

                } else if (gp.ui.commandNumber == 1) {
                        // ADD LATER Load GAME save
                } else if (gp.ui.commandNumber == 2) {
                    System.exit(0);
                }
            }
        }


        /* else if (gp.ui.titleScreenState == 1) {
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

        }*/
    }
    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void playState( int code){
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
        // opens up CharacterSTATE
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
            // System.exit(0); old exit on escape key
        }
        if (code == KeyEvent.VK_E) {
            shotKeyPressed = true;
        }



        // DEBUG
        if (code == KeyEvent.VK_T) {
            if(showDebugText == false){
                showDebugText = true;
            } else if (showDebugText == true){
                showDebugText = false;
            }
        }
        if (code == KeyEvent.VK_R) {
            gp.tileM.mapTileNumber = null;
            gp.tileM.mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];
            gp.tileM.loadMap("/images/tiles/worldMapNew.csv");
            //System.out.println("Map Reloaded" );
            gp.repaint();  // Force immediate repaint
        }
    }
    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void pauseState(int code){
        if (code == KeyEvent.VK_ESCAPE) {
               gp.gameState = gp.playState;
        }
    }
    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void optionState(int code){

        if (code == KeyEvent.VK_ESCAPE) {
            if (gp.ui.subState == 0){
                gp.gameState = gp.playState;
            } else if (gp.ui.subState == 1){
                gp.ui.subState = 0;
                gp.ui.commandNumber = 0;
            } else if (gp.ui.subState == 2){
                gp.ui.subState = 0;
                gp.ui.commandNumber = 3;
            } else if (gp.ui.subState == 3) {
                gp.ui.subState = 0;
                gp.ui.commandNumber = 4;
            }
        }

        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            enterPressed = true;
        }


        // Press up or down to change option
        if (code == KeyEvent.VK_W ) {
            gp.ui.commandNumber--;
            gp.playSE(12);
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = 5;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            gp.playSE(12);
            if (gp.ui.commandNumber > 5) {
                gp.ui.commandNumber = 0;
            }
        }
         if (code == KeyEvent.VK_A){
            if (gp.ui.subState == 0){
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(12);
                }
                if (gp.ui.commandNumber == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(12);
                }
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.ui.subState == 0){
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(12);
                }
                if (gp.ui.commandNumber == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(12);
                }
            }
        }

    }
    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            gp.gameState = gp.playState;
        }
    }
    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void characterState(int code){
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_W){
            if(gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSE(12);
                System.out.println("Row: " + gp.ui.slotRow);
            }

        }
        if (code == KeyEvent.VK_S){
            if(gp.ui.slotRow != 3) {
            gp.ui.slotRow++;
            gp.playSE(12);
            System.out.println("Row: " + gp.ui.slotRow);
            }
        }
        if (code == KeyEvent.VK_D){
            if(gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSE(12);
                System.out.println("Col: " + gp.ui.slotCol);
            }
        }
        if (code == KeyEvent.VK_A){
            if(gp.ui.slotCol != 0) {
            gp.ui.slotCol--;
            gp.playSE(12);
            System.out.println("Col: " + gp.ui.slotCol);
            }
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
            gp.player.selectItem();
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
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_E) {
            shotKeyPressed = false;
        }
    }
}
