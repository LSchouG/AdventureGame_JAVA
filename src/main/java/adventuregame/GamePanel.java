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

import adventuregame.data.SaveLoad;
import adventuregame.entity.Entity;
import adventuregame.entity.Player;
import adventuregame.environment.EnvironmentManager;
import adventuregame.tile_interactive.InteractiveTile;
import adventuregame.tiles.Map;
import adventuregame.tiles.TileManager;
import adventuregame.ai.PathFinder;


public class GamePanel extends JPanel implements Runnable {

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    /****************************** WORLD SETTINGS ****************************/
    public final int maxWorldCol = 95;
    public final int maxWorldRow = 80;
    public final int maxMap = 10;
    public int currentMap = 0;
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
    public CutSceneManager csManager = new CutSceneManager(this);

    /************************* ENTITY AND OBJECTS *****************************/
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][55];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][23];
    public Entity projectile[][] = new Entity[maxMap][10];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();

    /**************************** GAME STATE **********************************/
    public int gameState;
    public int previousState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int shopState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutSceneState = 11;

    //OTHER
    public boolean bossBattleOn = false;

    // AREA
    public final int outSide = 50;
    public final int indoor = 51;
    public final int dungeon = 52;
    public int currentArea;
    public int nextArea;


    /********************************* FPS ************************************/
    int FPS = 60;
    /******************************* SYSTEM ***********************************/
    public TileManager tileM = new TileManager(this);
    public PathFinder pfinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    Map map = new Map(this);
    Config config = new Config(this);
    SaveLoad saveLoad = new SaveLoad(this);
    Thread gameThread;
    ArrayList<Entity> entityList = new ArrayList<>();
    BufferedImage tempScreen;
    Graphics2D g2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // allows the panel to receive keyboard input
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();
        eManager.setup();
        // playMusic(0); // play background music track 0
        //stopMusic();  // optionally stop it immediately
        gameState = titleState;
        currentArea = indoor;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if (fullScreen == true) {
            setFullScreen();
        }

    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        final double drawInterval = 1_000_000_000.0 / FPS; // 60 FPS in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        final int MAX_UPDATES_PER_FRAME = 5; // Cap to prevent lag spirals

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // Fixed updates (capped)
            int updateLoops = 0;
            boolean updated = false;
            while (delta >= 1 && updateLoops < MAX_UPDATES_PER_FRAME) {
                update();
                delta -= 1;
                updateLoops++;
                updated = true;
            }

            // Draw only if updated (reduces unnecessary repaints during idle)
            if (updated) {
                drawToTempScreen();
                drawToScreen();
                drawCount++;
            }

            // Optional: FPS logging every second
            if (timer >= 1_000_000_000) {
                timer = 0;
                drawCount = 0;
            }
        }
    }
    @Override
    public void update(Graphics g) {
        paint(g);  // Bypass default clear; directly invoke paint for manual buffering
    }
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
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    if (projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            // Particle updates only if game is running (reverse loop for safe removal)
            for (int i = particleList.size() - 1; i >= 0; i--) {
                Entity particle = particleList.get(i);
                if (particle != null) {
                    if (particle.alive == true) {
                        particle.update();
                    } else {
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

            // Interactive tiles updates only if game is running
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        } else if (gameState == pauseState) {
            // pause logic here if needed
        }
    }
    public void drawToTempScreen() {
        // Clear the tempScreen to prevent residual pixels
        g2.setColor(new Color(0, 0, 0, 0)); // Transparent black
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // MAP SCREEN
        else if (gameState == mapState) {
            map.drawFullMapScreen(g2);
        }
        // OTHERS
        else {
            // Tiles
            tileM.draw(g2);
            // ADD NPC TO LIST
            for (int i = 0; i < npc[currentMap].length; i++) {  // Fixed: Use currentMap
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            // Interactive Tiles
            for (int i = 0; i < iTile[currentMap].length; i++) {  // Fixed: Use currentMap
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // ADD PLAYER TO LIST
            entityList.add(player);

            // ADD OBJ TO LIST
            for (int i = 0; i < obj[currentMap].length; i++) {  // Fixed: Use currentMap
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            // ADD MONSTER TO LIST
            for (int i = 0; i < monster[currentMap].length; i++) {  // Fixed: Use currentMap
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            // ADD PROJECTILES TO LIST
            for (int i = 0; i < projectile[currentMap].length; i++) {  // Fixed: Use currentMap
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            // ADD PARTICLE TO LIST (simplified null check)
            for (Entity particle : particleList) {  // Enhanced: Use enhanced for-loop for clarity
                if (particle.alive) {  // Fixed: Check alive flag directly (more efficient)
                    entityList.add(particle);
                }
            }

            // SORT (unchanged; correct for y-sorting)
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // DRAW ENTITYLIST
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // EMPTY LIST
            entityList.clear();

            // Time overlay draw measurement
            long drawStart = System.nanoTime();  // Start before overlays

            // Environment
            eManager.draw(g2);

            // MINI MAP
            map.drawMiniMap(g2);

            // CUTSCENE
            csManager.draw(g2);

            // 5. Draw UI
            ui.draw(g2);

            long drawEnd = System.nanoTime();
            long passed = (drawEnd - drawStart) / 1_000_000;  // ms

            // DEBUG - End draw time measurement
            if (keyH.showDebugText) {
                g2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
                g2.setColor(Color.WHITE);
                int x = 10;
                int y = 400;
                int lineHeight = 20;
                int row = (player.worldY + player.solidArea.y) / tileSize;
                int col = (player.worldX + player.solidArea.x) / tileSize;

                String colLabel = convertToColumnLabel(col + 1); // +1 because Excel-style labels start at 1

                g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
                g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
                g2.drawString("Col: " + colLabel, x, y);  y += lineHeight;
                g2.drawString("World MAP Number: " + currentMap , x, y);  y += lineHeight;
                g2.drawString("WorldX - Col: " + (col), x, y); y += lineHeight;
                g2.drawString("WorldY - Row: " + (row ), x, y); y += lineHeight;
                g2.drawString("Draw Time (ms): " + passed, x, y); y += lineHeight;
                g2.drawString("God Mode: " + keyH.godModeOn, x, y);
            }
        }
    }
    public void drawToScreen() {
        repaint(); // Request the panel to call paintComponent()
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent’s painting logic (clears the screen)
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null); // Draw the buffered image
    }
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
    }
    public void resetGame(boolean restart){

        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPositions();
        player.restoreStatus();
        aSetter.setNPC();
        aSetter.setMonster();
        player.resetCounter();


        if (restart) {
            stopMusic();
            // Switch to Home map 1
            currentMap = 1;
            player.setItems();
            player.setDefaultValues();
            eManager.lighting.resetDay();
            aSetter.setObject();
            aSetter.setInteractiveTiles();
            gameState = titleState;
        }
    }
    public String convertToColumnLabel(int colNumber) {
        StringBuilder colName = new StringBuilder();
        while (colNumber > 0) {
            colNumber--; // Adjust for 0-based index
            colName.insert(0, (char) ('A' + (colNumber % 26)));
            colNumber /= 26;
        }
        return colName.toString();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop(); // loop the background music
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
    public void changeArea(){
        if (nextArea != currentArea){
             stopMusic();
             switch (nextArea){
                 case outSide: playMusic(0);break;
                 case indoor: playMusic(0);break;
                 case dungeon: playMusic(20);break;
             }
             aSetter.setNPC();
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }
    public void removeTempEntity(){

        for(int mapNum = 0; mapNum < maxMap; mapNum++){

            for(int i = 0; i < obj[1].length; i++){

                if(obj[mapNum][i] != null && obj[mapNum][i].temp == true){
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}