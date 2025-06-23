/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adventuregame.tiles;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import adventuregame.GamePanel;
import adventuregame.UtilityTool;

import java.io.BufferedReader;

public final class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/images/tiles/worlmap2.csv");
    }

    public void getTileImage() {

        //GRASS
        setup(0, "grass", false);
        setup(48, "grassPit", false);
        setup(50, "grassTeleport", false);


        //WALL
        setup(1, "wall", true);
        //FLOOR
        setup(2, "Floor", false);
        setup(49, "bed", false);
        //TREE AND BUSH
        setup(3, "bush", true);
        setup(4, "tree", true);
        setup(5, "rock", true);
        //WATER
        setup(6, "water", true);
        setup(7, "waterToGrassDown", true);
        setup(8, "waterToGrassLeft", true);
        setup(9, "waterToGrassRight", true);
        setup(10, "waterToGrassTop", true);
        setup(11, "waterToGrassDownLeftIn", true);
        setup(12, "waterToGrassDownLeftOut", true);
        setup(13, "waterToGrassDownRightIn", true);
        setup(14, "waterToGrassDownRightOut", true);
        setup(15, "waterToGrassTopLeftIn", true);
        setup(16, "waterToGrassTopLeftOut", true);
        setup(17, "waterToGrassTopRightIn", true);
        setup(18, "waterToGrassTopRightOut", true);
        //SAND
        setup(19, "sand", false);
        setup(20, "sandToGrassDown", false);
        setup(21, "sandToGrassDownLeft", false);
        setup(22, "sandToGrassDownOutLeft", false);
        setup(23, "sandToGrassDownOutRight", false);
        setup(24, "sandToGrassDownRight", false);
        setup(25, "sandToGrassTop", false);
        setup(26, "sandToGrassTopLeft", false);
        setup(27, "sandToGrassTopOutLeft", false);
        setup(28, "sandToGrassTopOutRight", false);
        setup(29, "sandToGrassTopRight", false);
        setup(46, "sandToGrassRight", false);
        setup(47, "sandToGrassLeft", false);


        setup(30, "sandToWaterDown1", true);
        setup(31, "sandToWaterDown2", false);
        setup(32, "sandToWaterDownLeft1", true);
        setup(33, "sandToWaterDownLeft2", false);
        setup(34, "sandToWaterDownRight1", true);
        setup(35, "sandToWaterDownRight2", false);
        setup(36, "sandToWaterLeft1", true);
        setup(37, "sandToWaterLeft2", false);
        setup(38, "sandToWaterRight1", true);
        setup(39, "sandToWaterRight2", false);
        setup(40, "sandToWaterTop1", true);
        setup(41, "sandToWaterTop2", false);
        setup(42, "sandToWaterTopLeft1", true);
        setup(43, "sandToWaterTopLeft2", false);
        setup(44, "sandToWaterTopRight1", true);
        setup(45, "sandToWaterTopRight2", false);

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


    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                String[] numbers = line.split(",");

                for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    try {
                        String rawCol = numbers[col];

                        if (rawCol == null || rawCol.trim().isEmpty()) {
                            mapTileNumber[col][row] = 0; // default Grass if null or empty
                        } else {
                            String cleaned = rawCol.trim().replaceAll("[^0-9]", "");
                            mapTileNumber[col][row] = cleaned.isEmpty() ? 0 : Integer.parseInt(cleaned);
                        }
                    } catch(NumberFormatException nfe)  {
                        System.err.println("Invalid value at row " + row + ", col " + col + ": '" + numbers[col] + "'");
                        mapTileNumber[col][row] = 0; // fallback to grass
                    }
                }

                row++;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNumber = mapTileNumber[worldCol][worldRow];

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

    }

}
