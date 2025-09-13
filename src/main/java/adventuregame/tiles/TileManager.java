/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adventuregame.tiles;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import adventuregame.GamePanel;
import adventuregame.UtilityTool;


public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][][];
    boolean drawpath = true;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNumber = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldMapNew.csv", 0);
        loadMap("/maps/interiorHome.csv", 1);
        loadMap("/maps/interiorSeller.csv", 2);
        loadMap("/maps/cityMap.csv", 3);
        loadMap("/maps/bossMap.csv", 4);
        loadMap("/maps/dungeon.csv", 5);

    }
    public void getTileImage() {

        //GRASS
        setup(0, "grass", false);


        //WALL
        setup(1, "wall", true);
        //interior
        setup(2, "floor", false);
        setup(3, "bed", false);
        setup(4, "table", true);
        // On GRASS
        setup(5, "bush", true);
        setup(6, "tree", true);
        setup(7, "rock", true);
        setup(8, "house", false);
        setup(9, "grass-pit", false);
        setup(10, "grass-teleport", false);
        setup(11, "grass", false);

        //WATER
        setup(11, "water", true);
        //SAND
        setup(12, "sand", false);
        setup(14, "sand", false);
        // Black
        setup(13, "black", true);
        // dungeon
        setup(14, "stairs-down-left", false);
        setup(15, "dirt", false);
        setup(16, "stairs-down-right", false);
        setup(17, "stairs-up-left", false);
        setup(18, "stairs-up-right", false);

    }
    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath, int map) {
    try {
        // Convert resource path to actual file path
        InputStream mapFile = getClass().getResourceAsStream(filePath);

        BufferedReader br = new BufferedReader(new InputStreamReader(mapFile));

        int col = 0;
        int row = 0;

        while (row < gp.maxWorldRow && row < gp.maxWorldRow) {
            String line = br.readLine();

            if (line == null) break;

            String[] numbers = line.split(",");

            for (col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                try {
                    String rawCol = numbers[col];
                    if (rawCol == null || rawCol.trim().isEmpty()) {
                        mapTileNumber[map][col][row] = 0;
                    } else {
                        String cleaned = rawCol.trim().replaceAll("[^0-9]", "");
                        mapTileNumber[map][col][row] = cleaned.isEmpty() ? 0 : Integer.parseInt(cleaned);
                    }
                } catch(NumberFormatException nfe) {
                    System.err.println("Invalid value at row " + row + ", col " + col + ": '" + numbers[col] + "'");
                    mapTileNumber[map][col][row] = 0;
                }
            }
            row++;
        }

        br.close();
        //System.out.println("Map loaded successfully from: " + actualPath);
        
    } catch (IOException e) {
        System.err.println("Error loading map file: " + filePath);
        e.printStackTrace();
    }
}
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNumber = mapTileNumber[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNumber].image, screenX, screenY, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        if (drawpath = true){
            g2.setColor(new Color(255,0,0,70));

            for(int i = 0; i < gp.pfinder.pathList.size(); i++){

                int worldX = gp.pfinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pfinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }

    }

}