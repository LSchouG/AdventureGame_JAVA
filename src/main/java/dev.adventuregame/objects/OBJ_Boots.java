/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.adventuregame.objects;

import dev.adventuregame.GamePanel;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author lars.s.g
 */
public class OBJ_Boots extends SuperObject {
    GamePanel gp;
     public OBJ_Boots (GamePanel gp) {

        name = "Boots";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/boots.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
