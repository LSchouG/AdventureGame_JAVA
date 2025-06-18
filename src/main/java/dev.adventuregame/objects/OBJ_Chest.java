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
 * @author IT Student
 */
public class OBJ_Chest extends SuperObject {

    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {

        name = "Chest";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/chest.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
