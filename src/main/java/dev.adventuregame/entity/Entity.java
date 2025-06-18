/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.adventuregame.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author lars.s.g
 */
public class Entity {

    public int worldX, worldY;
    public int screenX, screenY;
    public int speed;
    public BufferedImage downStill, down1, down2, upStill, up1, up2, leftStill, left1, left2, rightStill, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
