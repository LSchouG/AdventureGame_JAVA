/**
 * *****************************************************************************
 * FileName: GamePanel.java
 * Purpose: Main game panel that handles rendering, game loop, and game state management.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Uses delta time method to control frame rate
 * - Draws tiles, entities, objects, and UI
 * - Manages input, collision, and sound
 *******************************************************************************/

package adventuregame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import adventuregame.entity.Entity;
import adventuregame.entity.Player;
import adventuregame.tile_interactive.InteractiveTile;
import adventuregame.tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    /****************************** WORLD SETTINGS ****************************/
    public final int maxWorldCol = 150;
    public final int maxWorldRow = 100;
    public final int maxMap = 10;
    public int currentMap = 1;
    /***************************** SCREEN SETTINGS ****************************/
    final int originalTileSize = 16; // original 16x16 tiles
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // final tile size = 48x48
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    int screenWidth2 = screenWidth;
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    int screenHeight2 = screenHeight;
    public boolean fullScreen = false;
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    /************************* ENTITY AND OBJECTS *****************************/
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][30];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][5];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    /**************************** GAME STATE **********************************/
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int shopState = 8;

    /********************************* FPS ************************************/
    int FPS = 60;
    /******************************* SYSTEM ***********************************/
    TileManager tileM = new TileManager(this);
    Config config = new Config(this);
    Thread gameThread;
    ArrayList<Entity> entityList = new ArrayList<>();
    BufferedImage tempScreen;
    Graphics2D g2;


    /**************************************************************************
     * Constructor: GamePanel()
     * Purpose: Initializes the game panel size, background, buffering, and input focus.
     ***************************************************************************/
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // allows the panel to receive keyboard input
    }


    /**************************************************************************
     * Method: setupGame()
     * Purpose: Prepares the game before starting by setting objects, NPCs, and music.
     ***************************************************************************/
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();
        // playMusic(0); // play background music track 0
        //stopMusic();  // optionally stop it immediately
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if (fullScreen == true) {
            setFullScreen();
        }

    }

    /**************************************************************************
     * Method: startGameThread()
     * Purpose: Starts the main game loop in a new thread.
     ***************************************************************************/
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**************************************************************************
     * Method: run()
     * Purpose: Main game loop. Uses delta time for consistent frame rate.
     ***************************************************************************/
    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS; // 60 FPS in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            while (delta >= 1) {
                update(); // update game logic
                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen(); // draw the buffered image to the screen
                delta--;
                drawCount++;
            }
        }
    }

    /**************************************************************************
     * Method: update()
     * Purpose: Update the game state, player movement, and other logic.
     ***************************************************************************/
    public void update() {

        if (gameState == playState) {

            // Player updates only if game is running
            player.update();

            // npc updates only if the game is running
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // Projectile updates only if the game is running
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }
                    if (projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }
            // Particale updates only if game is running
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }

            // Monster updates only if game is running
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            // Interactiv tiles updates only if game is running
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        } else if (gameState == pauseState) {
            // pause logic here if needed
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void drawToTempScreen() {
        // DEBUG - Start draw time measurement
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // OTHERS
        else {
            // Tiles
            tileM.draw(g2);

            // Interactiv Tiles
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // ADD PLAYER TO LIST
            entityList.add(player);

            // ADD NPC TI LIST
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            // ADD OBJ TO LIST
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            // ADD MONSTER TO LIST
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            // ADD PROJECTILES TO LIST
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            // ADD PARTICALE TO LIST
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // DRAW ENTITYLIST
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // EMPTY LIST
            entityList.clear();


            // 5. Draw UI
            ui.draw(g2);
        }


        // DEBUG - End draw time measurement
        if (keyH.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;


            g2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            int row = (player.worldY + player.solidArea.y) / tileSize;
            int col = (player.worldX + player.solidArea.x) / tileSize;

            String colLabel = convertToColumnLabel(col + 1); // +1 because Excel-style labels start at 1

            g2.drawString("WorldX: " + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Col: " + colLabel, x, y); // Column shown as letters
            y += lineHeight;
            g2.drawString("WorldX - Col: " + (col), x, y);
            y += lineHeight;
            g2.drawString("WorldY - Row: " + (row ), x, y);
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void drawToScreen() {
        repaint(); // Request the panel to call paintComponent()
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent’s painting logic (clears the screen)
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null); // Draw the buffered image
    }

    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void setFullScreen() {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        try {
            gd.setFullScreenWindow(AdventureGame.window);
        } catch (Exception e) {
            System.err.println("Error switching to full screen: " + e.getMessage());
            // Optional fallback: Set window size to screen size
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            AdventureGame.window.setSize(screenSize.width, screenSize.height);
        }

        // GET FULL WIDTH AND HEIGHT
        screenWidth2 = AdventureGame.window.getWidth();
        screenHeight2 = AdventureGame.window.getHeight();
        System.out.println("Fullscreen dimensions: " + screenWidth2 + "x" + screenHeight2);
    }

    public void retry(){

        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void restart(){

        player.setDefaultValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();
    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public String convertToColumnLabel(int colNumber) {
        StringBuilder colName = new StringBuilder();
        while (colNumber > 0) {
            colNumber--; // Adjust for 0-based index
            colName.insert(0, (char) ('A' + (colNumber % 26)));
            colNumber /= 26;
        }
        return colName.toString();
    }

    /**************************************************************************
     * Method: playMusic(int i)
     * Purpose: Plays background music using a given index.
     ***************************************************************************/
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop(); // loop the background music
    }

    /**************************************************************************
     * Method: stopMusic()
     * Purpose: Stops background music.
     ***************************************************************************/
    public void stopMusic() {
        music.stop();
    }

    /**************************************************************************
     * Method: playSE(int i)
     * Purpose: Plays a sound effect using given index.
     ***************************************************************************/
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}