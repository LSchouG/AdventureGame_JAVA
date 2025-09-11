package adventuregame.tiles;

import adventuregame.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map  extends TileManager{
    GamePanel gp;
    BufferedImage worldMap[];
    public boolean minMapOn = false;

    public Map(GamePanel gp){
        super(gp);
        this.gp = gp;
        createWorldMap();
    }
    public void createWorldMap(){

        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;

        for (int i = 0; i < gp.maxMap; i++){

            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                int tilNum = mapTileNumber[i][col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;
                g2.drawImage(tile[tilNum].image,x,y,null);

                col++;
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }
    public void drawFullMapScreen(Graphics2D g2){

        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.screenWidth,gp.screenHeight);

        // DRAW Map
        int width = (gp.maxScreenCol * gp.tileSize) -gp.tileSize * 2;
        int height = (gp.maxScreenRow * gp.tileSize) -gp.tileSize * 2;
        int x = (gp.screenWidth/2) - (width/2);
        int y = (gp.screenHeight/2) - (height/2);

       g2.drawImage(worldMap[gp.currentMap],x,y,width,height,null);

        // SCALE
        double scaleR = (double)(gp.tileSize * gp.maxWorldRow) / height;
        double scaleC = (double)(gp.tileSize * gp.maxWorldCol) / width;
        int playerSize = (int)((gp.tileSize / Math.max(scaleC, scaleR)) * 2);

        // USE CENTER OF PLAYER FOR ACCURACY
        int playerX = (int)((x + (gp.player.worldX + gp.tileSize/2) / scaleC) - (playerSize / 2));
        int playerY = (int)((y + (gp.player.worldY + gp.tileSize/2) / scaleR) - (playerSize / 2));

        // DRAW
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);


        // HINT
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawString("Press M to close", gp.screenWidth - gp.tileSize*5,gp.screenHeight - (gp.tileSize/2) );
    }
    public void drawMiniMap(Graphics2D g2){
        if (minMapOn == true){

            // --- MINIMAP SIZE & POSITION ---
            int width = gp.screenWidth / 5;   // 1/3 of screen width
            int height = (gp.tileSize * gp.maxWorldRow * width) / (gp.tileSize * gp.maxWorldCol); // keep aspect ratio
            int x = gp.screenWidth - width - 20; // 20px margin from right
            int y = 20;                           // 20px from top

            // --- BACKGROUND ---
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .9f));
            g2.setColor(new Color(0, 0, 0, 180)); // dark semi-transparent background
            g2.fillRoundRect(x - 4, y - 4, width + 8, height + 8, 10, 10);

            // --- DRAW FULL WORLD MAP ---
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

            // --- DRAW PLAYER ---
            double scaleX = (double)(gp.tileSize * gp.maxWorldCol) / width;
            double scaleY = (double)(gp.tileSize * gp.maxWorldRow) / height;
            int playerSize = (int)((gp.tileSize / Math.max(scaleX, scaleY)) * 2); // double size

            int playerX = (int)(x + (gp.player.worldX + gp.tileSize / 2) / scaleX - playerSize / 2);
            int playerY = (int)(y + (gp.player.worldY + gp.tileSize / 2) / scaleY - playerSize / 2);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.setColor(Color.red);
            g2.fillOval(playerX, playerY, playerSize, playerSize);
        }
}}
