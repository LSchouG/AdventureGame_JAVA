/** ******************************************************************************
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import adventuregame.entity.Entity;
import adventuregame.entity.Player;
import adventuregame.tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {

    /***************************** SCREEN SETTINGS ****************************/
    final int originalTileSize = 16; // original 16x16 tiles
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // final tile size = 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    /****************************** WORLD SETTINGS ****************************/
    public final int maxWorldCol = 150;
    public final int maxWorldRow = 100;

    /********************************* FPS ************************************/
    int FPS = 60;

    /******************************* SYSTEM ***********************************/
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    /************************* ENTITY AND OBJECTS *****************************/
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    /**************************** GAME STATE **********************************/
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;



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
        // playMusic(0); // play background music track 0
        //stopMusic();  // optionally stop it immediately
        gameState = titleState;
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
                repaint(); // render the screen
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
            // npc updates only if game is running
            for (int i =0; i < npc.length; i++)
            {
                if (npc[i] != null){
                    npc[i].update();
                }
            }
            // Monster updates only if game is running
            for (int i =0; i < monster.length; i++)
            {
                if (monster[i] != null){
                    if (monster[i].alive == true && monster[i].dying == false){
                        monster[i].update();
                    }
                    if (monster[i].alive == false){
                        monster[i] = null;
                    }
                }
            }
        } else if (gameState == pauseState) {
            // pause logic here if needed
        }
    }

    /**************************************************************************
     * Method: paintComponent(Graphics g)
     * Purpose: Handles all rendering (tiles, objects, entities, UI).
     * Inputs: g - the graphics context.
     ***************************************************************************/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG - Start draw time measurement
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState){
            ui.draw(g2);
        }
        // OTHERS
        else{
            // 1. Draw tiles
            tileM.draw(g2);

            entityList.add(player);

            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
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
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            // EMPTY LIST
           entityList.clear();


            // 5. Draw UI
            ui.draw(g2);
        }


        // DEBUG - End draw time measurement
        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose(); // dispose of the graphics context
    }

    /**************************************************************************
     * Method: playMusic(int i)
     * Purpose: Plays background music using given index.
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
