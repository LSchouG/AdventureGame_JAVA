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

    GamePanel gp;
    // Movement keys
    public boolean upPressed = false, downPressed = false, leftPressed = false,
            rightPressed = false, enterPressed = false, shotKeyPressed = false,
            qPress = false;
    // Debugging
    public boolean showDebugText = false;
    public boolean godModeOn;


    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutSceneState) {
            dialogueState(code);
        }
        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
        // OPTION STATE
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }
        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        // SHOP STATE
        else if (gp.gameState == gp.shopState) {
            shopState(code);
        }
        // MAP STATE
        else if(gp.gameState == gp.mapState){
            mapState(code);
        }

    }
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
        if (code == KeyEvent.VK_Q) {
            qPress = false;
        }

        // MINI MAP STATE
        else if(code == KeyEvent.VK_N){
            if(gp.map.minMapOn == false){
                gp.map.minMapOn = true;
            } else {
                gp.map.minMapOn = false;
            }
        }
    }
    public void mapState(int code){
        if (code == KeyEvent.VK_M){
            gp.gameState = gp.playState;
        }
    }
    public void shopState(int code) {
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            enterPressed = true;
        }

        if(gp.ui.subState == 0){

            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 2;
                }
                gp.playSE(12);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 2) {
                    gp.ui.commandNumber = 0;
                }
                gp.playSE(12);
            }
        }
        if (gp.ui.subState == 0){
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
                gp.ui.commandNumber = 0;
            }
        }
        if (gp.ui.subState == 1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
                gp.ui.commandNumber = 0;
            }
        }
        if (gp.ui.subState == 2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
                gp.ui.commandNumber = 1;
            }
        }

    }
    public void titleState(int code) {
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 2;
                }
                gp.playSE(12);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 2) {
                    gp.ui.commandNumber = 0;
                }
                gp.playSE(12);
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNumber == 0) {

                    gp.gameState = gp.playState;
                    gp.playMusic(0);

                } else if (gp.ui.commandNumber == 1) {
                    // ADD LATER Load GAME save
                    gp.saveLoad.load();
                    gp.gameState = gp.playState;
                    gp.playMusic(0);

                } else if (gp.ui.commandNumber == 2) {
                    System.exit(0);
                }
            }
        }


    }
    public void playState(int code) {
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
        if (code == KeyEvent.VK_Q) {
            qPress = true;
        }
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }


        // DEBUG AND TESTING
        // SHOW MAP DATA ON SCREEN WITH T KEY
        if (code == KeyEvent.VK_T) {
            if (showDebugText == false) {
                showDebugText = true;
            } else if (showDebugText == true) {
                showDebugText = false;
            }
        }
        // RELOAD MAP WITH R KEY
        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0: gp.tileM.loadMap("/images/maps/worldMapNew.csv", 0); break;
                case 1: gp.tileM.loadMap("/images/maps/interiorHome.csv", 1); break;
                case 2: gp.tileM.loadMap("/images/maps/interiorSeller.csv", 2); break;
                case 3: gp.tileM.loadMap("/images/maps/cityMap.csv", 3); break;
                case 4: gp.tileM.loadMap("/images/maps/bossMap.csv", 4); break;
                case 5: gp.tileM.loadMap("/images/maps/dungeon.csv", 5); break;
            }
            gp.repaint(); // Force immediate repaint
        }
        if(code == KeyEvent.VK_G){
            if (godModeOn == false) {
                godModeOn = true;
            } else if (godModeOn == true) {
                godModeOn = false;
            }
        }

    }
    public void pauseState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }
    public void optionState(int code) {

        if (code == KeyEvent.VK_ESCAPE) {
            if (gp.ui.subState == 0) {
                gp.gameState = gp.playState;
                gp.ui.commandNumber = 0;
            } // goes from fullscreen dialog back to menu
            else if (gp.ui.subState == 1) {
                gp.ui.subState = 0;
                gp.ui.commandNumber = 0;
            } // goes from control screen back to menu
            else if (gp.ui.subState == 2) {
                gp.ui.subState = 0;
                gp.ui.commandNumber = 3;
            } // goes from endgame back to menu
            else if (gp.ui.subState == 3) {
                gp.ui.subState = 0;
                gp.ui.commandNumber = 4;
            }
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            enterPressed = true;
        }

        // KEYHANDLER FOR SUBSTATE 0 = option menu
        if (gp.ui.subState == 0) {
            // Press up or down to change option
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                gp.playSE(12);
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 6;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                gp.playSE(12);
                if (gp.ui.commandNumber > 6) {
                    gp.ui.commandNumber = 0;
                }
            }
            if (code == KeyEvent.VK_A) {
                if (gp.ui.subState == 0) {
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
            if (code == KeyEvent.VK_D) {
                if (gp.ui.subState == 0) {
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

        // KEYHANDLER FOR SUBSTATE 1 = fullscreen notification
        if (gp.ui.subState == 1) {
            // Press up
            if (code == KeyEvent.VK_W) {gp.playSE(12);}
            // Press down
            if (code == KeyEvent.VK_S) {gp.playSE(12);}
        }

        // KEYHANDLER FOR SUBSTATE 2 = control notification
        if (gp.ui.subState == 2) {
            // Press up
            if (code == KeyEvent.VK_W) {gp.playSE(12);}
            // Press down
            if (code == KeyEvent.VK_S) {gp.playSE(12);}
        }

        // KEYHANDLER FOR SUBSTATE 3 = end game confirmation
        if (gp.ui.subState == 3) {
            // Press up
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                gp.playSE(12);
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 1; // wrap to bottom
                }
            }
            // Press down
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                gp.playSE(12);
                if (gp.ui.commandNumber > 1) {
                    gp.ui.commandNumber = 0; // wrap to top
                }
            }
        }
        // KEYHANDLER FOR SUBSTATE 3 = end game confirmation
        if (gp.ui.subState == 4) {
            // Press up
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                gp.playSE(12);
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 1; // wrap to bottom
                }
            }
            // Press down
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                gp.playSE(12);
                if (gp.ui.commandNumber > 1) {
                    gp.ui.commandNumber = 0; // wrap to top
                }
            }
        }
    }
    public void gameOverState(int code){
            // Press up
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                gp.playSE(12);
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 1; // wrap to bottom
                }
            }
            // Press down
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                gp.playSE(12);
                if (gp.ui.commandNumber > 1) {
                    gp.ui.commandNumber = 0; // wrap to top
                }
            }

            // if enter or space is pressed, go back to play state
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
               if(gp.ui.commandNumber == 0) {
                   gp.gameState = gp.playState;
                   gp.resetGame(false);
                   gp.playMusic(0);
               } else if (gp.ui.commandNumber == 1) {
                   gp.gameState = gp.titleState;
                   gp.resetGame(true);
               }
            }

            // IF ESC IS PRESSED, GO BACK TO PLAYSTATE
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.titleState;
            }
    }
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            enterPressed = true;
        }
    }
    public void characterState(int code) {
        if (code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        playerInventory(code);
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            gp.player.selectItem();
        }
    }
    public void playerInventory(int code){
        if (code == KeyEvent.VK_W && gp.ui.playerSlotRow > 0) {
            gp.ui.playerSlotRow--;
            gp.playSE(12);
        }
        if (code == KeyEvent.VK_S && gp.ui.playerSlotRow < 3) {
            gp.ui.playerSlotRow++;
            gp.playSE(12);
        }
        if (code == KeyEvent.VK_D && gp.ui.playerSlotCol < 4) {
            gp.ui.playerSlotCol++;
            gp.playSE(12);
        }
        if (code == KeyEvent.VK_A && gp.ui.playerSlotCol > 0) {
            gp.ui.playerSlotCol--;
            gp.playSE(12);
        }
    }
    public void npcInventory(int code){
        // UP
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow > 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(12);
            }
        }

        // DOWN
        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow < 3) { // max row = 3
                gp.ui.npcSlotRow++;
                gp.playSE(12);
            }
        }

        // RIGHT
        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol < 4) { // max col = 4
                gp.ui.npcSlotCol++;
                gp.playSE(12);
            }
        }

        // LEFT
        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol > 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(12);
            }
        }
    }
}
