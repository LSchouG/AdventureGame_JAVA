/**
 * *****************************************************************************
 * FileName: Player.java
 * Purpose: Represents the player character with movement, collision, and animation.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Inherits from Entity
 * - Handles keyboard input and character sprite animation
 * - Uses collision detection and supports object interactions
 *******************************************************************************/
package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.KeyHandler;
import adventuregame.objects.OBJ_FireBall;
import adventuregame.objects.OBJ_Shield_Wood;
import adventuregame.objects.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;
    public final int maxInventorySize = 20;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    KeyHandler keyH;
    int standCounter = 0;

    /**************************************************************************
     * Constructor: Player(GamePanel gp, KeyHandler keyH)
     * Purpose: Initializes the player with key handler and position.
     ***************************************************************************/
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        type = type_player;
        this.keyH = keyH;

        // Place player in center of screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Define the player's collision box
        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 22;
        solidArea.height = 15;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImages();
        getPlayerAttachImages();
        setItems();
    }

    /**************************************************************************
     * Method: setDefaultValues()
     * Purpose: Set default player position, speed, and direction.
     ***************************************************************************/
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 22;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
        maxMana = 6;
        mana = maxMana;
        maxAmmo = 20;
        ammo = 5;
        level = 1;
        strength = 1;
        dexterity = 1;
        magic = 1;
        exp = 0;
        nextLevelExp = 5;
        gold = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_FireBall(gp);
//      projectile = new OBJ_Rock_Projectile(gp);
        attack = getAttack();
        defense = getDefense();
        coolDownMagicCounter = 100;
    }

    /**************************************************************************
     * Method: update()
     * Purpose: Handle player input, movement, collisions, and animation.
     ***************************************************************************/
    public void update() {

        // if attack attack
        if (attacking == true) {
            attaching();
        }

        // else if not attacking do something else.
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

            // 1. Update direction
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            if (spriteNumber == 0) {
                spriteNumber = 1;
            }

            // 2 CHECK
            // COLLISION CHECK TILE
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // COLLISION CHECK OBJECT
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // COLLISION CHECK NPC
            int ncpIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(ncpIndex);

            if (ncpIndex == 999) {
                handleAttack();
            }

            // COLLISION CHECK ENTITY
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            // interactNPC(monsterIndex);
            contactMonster(monsterIndex);


            // COLLISION CHECK TILE EVENT
            gp.eventHandler.checkEvent();

            // 3. Move player if no collision
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            if (keyH.enterPressed == true && attackCanceled == false) {

                gp.playSE(8);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

            // Increase the counter
            spriteCounter++;

            // Every 13 frames, switch the sprite between 1 and 2
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }

                // Reset the counter
                spriteCounter = 0;
            }
        }

        // else player is standing still.
        else {
            // Player standing still
            standCounter++;
            if (standCounter > 20) {
                spriteNumber = 0;
                standCounter = 0;
            }
        }

        // SHOT A OBJ LIKE FIREBALL OR ARROW
        if (gp.keyH.shotKeyPressed == true
                && projectile.alive == false
                && shotAvailableCounter == coolDownMagicCounter
                && projectile.haveResource(this) == true) {

            // SET DEFAULT COORDINATION DIRECTION AND USE
            projectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT COST OF PROJECTILE (MANA; AMMO ETC.)
            projectile.subtractResource(this);


            // ADD IT TO THE LIST
            gp.projectileList.add(projectile);

            shotAvailableCounter = 0;

            // PLAY SOUND
            gp.playSE(13);

        }

        // this needs to be outside of key if statment
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 100) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < coolDownMagicCounter) {
            shotAvailableCounter++;
        }

        if(life > maxLife){
            life = maxLife;
        }
        if(maxMana > mana){
            mana = maxMana;
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    /**************************************************************************
     * Method: getImages()
     * Purpose: Load and assign all directional sprite images.
     ***************************************************************************/
    public void getPlayerImages() {
        downStill = setup("/images/player/player-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/player/player-down-1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/player/player-down-2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/player/player-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/player/player-up-1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/player/player-up-2.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/player/player-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/player/player-left-1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/player/player-left-2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/player/player-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/player/player-right-1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/player/player-right-2.png", gp.tileSize, gp.tileSize);
    }

    /**************************************************************************
     * Method: getImages()
     * Purpose: Load and assign all directional sprite images.
     ***************************************************************************/
    public void getPlayerAttachImages() {

        /** IF WANT A SWORD IMAGES
         if (currentWeapon.type == type_sword){
         attachUp = setup("/images/player/attach-up.png", gp.tileSize, gp.tileSize *2);
         attachDown = setup("/images/player/attach-down.png", gp.tileSize, gp.tileSize *2);
         attachLeft = setup("/images/player/attach-left.png", gp.tileSize *2, gp.tileSize);
         attachRight = setup("/images/player/attach-right.png", gp.tileSize *2, gp.tileSize);
         }**/

        // UNIVERSAL ATTACK SWING
        attachUp = setup("/images/player/attack-up.png", gp.tileSize, gp.tileSize * 2);
        attachDown = setup("/images/player/attack-down.png", gp.tileSize, gp.tileSize * 2);
        attachLeft = setup("/images/player/attack-left.png", gp.tileSize * 2, gp.tileSize);
        attachRight = setup("/images/player/attack-right.png", gp.tileSize * 2, gp.tileSize);
    }

    /**************************************************************************
     * Method: pickUpObject(int i)
     * Purpose: Handle object pickup when player collides with one.
     * Inputs: i - index of the object collided with
     ***************************************************************************/
    public void pickUpObject(int i) {
        if (i != 999) {
            String text = "";
            //PICK UP ONLY ITEMS
            if (gp.obj[i].type == type_pickUpOnly) {
                gp.obj[i].use(this);
                gp.obj[i] = null;
            }
            // INVENTORY ITEMS
            else if (gp.obj[i].type == type_axe || gp.obj[i].type == type_sword ||
                    gp.obj[i].type == type_spell || gp.obj[i].type == type_consumable) {

                if (inventory.size() <= maxInventorySize) {
                    inventory.add(gp.obj[i]);
                    gp.playSE(2);
                    text = "Got a " + gp.obj[i].name + "!";
                } else {
                    text = "Your inventory is full!";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null;
            }
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void attaching() {
        spriteCounter++;
        if (spriteCounter >= 3) {
            spriteNumber = 1;
        }
        if (spriteCounter > 3 && spriteCounter < 12) {
            spriteNumber = 2;

            // save the current worldX, worldY and solidarea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust players worldX/Y for the attackArea
            switch (direction) {
                case "up":
                    worldX -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attack area become solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //check if solidArea(attackeArea) hit the monster
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);

            // After checking change back to  current worldX, worldY and solidarea
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

            damageMonster(monsterIndex, attack);

        }
        if (spriteCounter >= 12 && spriteCounter < 15) {
            spriteNumber = 1;
        }
        if (spriteCounter >= 15 && spriteCounter < 24) {
            spriteNumber = 2;
        }
        if (spriteCounter >= 24) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed && i != 999) {
            attackCanceled = true;
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
    }

    /**************************************************************************
     * Method: contactMonster()
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && gp.monster[i].dying == false) {

                int damage = gp.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 1;
                }

                gp.playSE(6);
                life -= damage;
                invincible = true;
            }
        }
    }

    /**************************************************************************
     * Method: handleAttack()
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void handleAttack() {
        if (gp.keyH.enterPressed && !attacking) {
            gp.playSE(9);
            attacking = true;
            spriteCounter = 0;  // Reset sprite counter for attack animation
        }
    }

    /**************************************************************************
     * Method: handleAttack()
     * Purpose:
     * Inputs:
     ***************************************************************************/
    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (gp.monster[i].invincible == false) {

                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " Damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.playSE(10);
                    gp.monster[i].dying = true;
                    gp.player.exp += gp.monster[i].exp;
                    gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Gain" + gp.monster[i].exp + " Exp!");
                    checkLevelUp();
                }
            }
        }
    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void checkLevelUp() {

        if (gp.player.exp >= gp.player.nextLevelExp) {
            gp.player.level++;
            gp.player.nextLevelExp += gp.player.nextLevelExp * 2;
            gp.player.maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(11);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + gp.player.level + " now! \n" +
                    "You feel stronger!";
        }

    }

    /**************************************************************************
     * Method:
     * Purpose:
     ***************************************************************************/
    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot();
        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_spell) {

                currentWeapon = selectedItem;
                getAttack();
                getPlayerAttachImages();
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                getDefense();
                getPlayerAttachImages();
            }
            if (selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    /**************************************************************************
     * Method: draw(Graphics2D g2)
     * Purpose: Draw the player's current sprite on screen.
     * Inputs: g2 - the graphics context
     ***************************************************************************/
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // VARIABLE FOR CHANGING X AND Y FOR TWO ATTACK IMAGES AS THEY A RENDERD WRONG
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "down":
                if (attacking == false) {
                    if (spriteNumber == 0) {
                        image = downStill;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                }
                if (attacking == true) {
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = attachDown;
                    }
                }
                break;
            case "up":
                if (attacking == false) {
                    if (spriteNumber == 0) {
                        image = upStill;
                    }
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                }
                if (attacking == true) {
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = attachUp;
                        tempScreenY -= gp.tileSize;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNumber == 0) {
                        image = rightStill;
                    }
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                }
                if (attacking == true) {
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = attachRight;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNumber == 0) {
                        image = leftStill;
                    }
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                }
                if (attacking == true) {
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = attachLeft;
                        tempScreenX -= gp.tileSize;
                    }
                }
                break;
            default:
                if (attacking == false) {
                    image = downStill;
                }
                if (attacking == true) {
                    if (spriteNumber == 0) {
                        image = downStill;
                    }
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = attachDown;
                    }
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null); // draw sprite
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
