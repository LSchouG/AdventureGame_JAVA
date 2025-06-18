/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {

    GamePanel gp;
    public OBJ_Key(GamePanel gp) {

        name = "Key";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
