/**
 * *****************************************************************************
 * FileName: Entity.java
 * Purpose: Base class for all moving/drawable entities (e.g., player, NPCs).
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Handles position, movement, sprite animation, and drawing
 * - Can be extended by more specific entities
 *******************************************************************************/

package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class  Entity {
    protected GamePanel gp;

    public BufferedImage downStill, down1, down2, upStill, up1, up2, leftStill, left1, left2, rightStill, right1, right2;
    public BufferedImage attachUp, attachDown, attachLeft, attachRight;
    public BufferedImage image1, image2, image3;      // Object image/sprite
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // collision bounds
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[20];

    /** STATE **/
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNumber = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    /** COUNTER **/
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int coolDownMagicCounter = 100;

    /** CHARACTER ATTRIBUTES **/
    public String name; // Object name identifier
    public int chosenClass;// 0 = fighter, 1 = Theif, 2 = Sorcerer
    public int speed;
    public int  maxLife;
    public  int  maxMana;
    public  int  mana;
    public int ammo;
    public int maxAmmo;
    public int  life;
    public  int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int  defense;
    public int  magic;
    public  int exp;
    public int nextLevelExp;
    public int  gold;
    public Entity user;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int  defenseValue;
    public int  restoreValue;
    public String itemDescription = "";
    public int useCost; //
    public int value;
    public String lockKeyType;

    // TYPE

    public int type;// 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_spell = 6;
    public final int type_consumable = 7;
    public final int type_pickUpOnly = 8;


    /**************************************************************************
     * Constructor: Entity(GamePanel gp)
     * Purpose: Initialize with reference to the game panel.
     ***************************************************************************/
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    /**************************************************************************
     * Method: setAction()
     * Purpose: Placeholder for subclasses to define specific behavior.
     * Notes: Empty in base class. Override in subclasses.
     ***************************************************************************/
    public void setAction() {}
    /**************************************************************************
     * Method: damageReaction()
     * Purpose:
     * Notes:
     ***************************************************************************/
    public void damageReaction() {}
    /**************************************************************************
     * Method: speak()
     * Purpose:
     * Notes:
     ***************************************************************************/
    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";

        }

    }
    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     * Outputs:
     ***************************************************************************/
    public void use(Entity entity) {}
    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     * Outputs:
     ***************************************************************************/
    public void checkDrop() {}
    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     * Outputs:
     ***************************************************************************/
    public void dropItem(Entity droppedItem) {

        for (int i = 0; i < gp.player.inventory.size(); i++) {
            if (gp.obj[i] == null) {
               gp.obj[i] = droppedItem;
               gp.obj[i].worldX = worldX;
               gp.obj[i].worldY = worldY;
               break;
            }
        }
    }
    /**************************************************************************
     * Method: update()
     * Purpose: Update logic (e.g., movement, collision). Called every frame.
     ***************************************************************************/
    public void update() {
        setAction();

        // Reset collision status before checking again
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer == true && gp.player.invincible == false){
            damageplayer(attack);
        }

        // 3. Move player if no collision
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        // 4. Handle sprite animation timing
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNumber = (spriteNumber == 1) ? 2 : 1;
            spriteCounter = 0;
        }
        // this needs to be outside of key if statment
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < coolDownMagicCounter){
            shotAvailableCounter++;
        }
    }
    /**************************************************************************
     * Method: draw(Graphics2D g2)
     * Purpose: Draws the entity to the screen with correct sprite based on direction.
     * Inputs: g2 - the graphics context
     ***************************************************************************/
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Calculate screen position based on world coordinates relative to the player
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw if within visible screen bounds
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Select sprite based on direction and sprite number
            switch (direction) {
                case "down" -> {
                    image = switch (spriteNumber) {
                        case 0 -> downStill;
                        case 1 -> down1;
                        case 2 -> down2;
                        default -> null;
                    };
                }
                case "up" -> {
                    image = switch (spriteNumber) {
                        case 0 -> upStill;
                        case 1 -> up1;
                        case 2 -> up2;
                        default -> null;
                    };
                }
                case "right" -> {
                    image = switch (spriteNumber) {
                        case 0 -> rightStill;
                        case 1 -> right1;
                        case 2 -> right2;
                        default -> null;
                    };
                }
                case "left" -> {
                    image = switch (spriteNumber) {
                        case 0 -> leftStill;
                        case 1 -> left1;
                        case 2 -> left2;
                        default -> null;
                    };
                }
            }

            // MONTER HEALTH BAR
            if (type == type_monster && hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 10 + 2);
                g2.setColor(new Color(250,0,30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
                hpBarCounter++;
                if (hpBarCounter > 600){
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }

            // Draw the image on screen
            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.3f);
            }
            if (dying == true){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, null); // draw sprite
            changeAlpha(g2, 1f);
        }
    }
    /**************************************************************************
     * Method: setup(String imagePath)
     * Purpose: Load and scale an image for entity sprite.
     * Inputs: imagePath - relative path to the image file
     * Outputs: Scaled BufferedImage
     ***************************************************************************/
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        BufferedImage scaledImage = null;

        try {
            // Load image
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            // Scale image to tile size
            scaledImage = uTool.scaleImage(image, width, height);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image: " + imagePath, e);
        }

        return scaledImage;
    }
    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     * Outputs:
     ***************************************************************************/
    public void  dyingAnimation(Graphics2D g2) {
        int i = 5;
        dyingCounter++;
        if (dyingCounter <= i) {changeAlpha(g2, 0.0f);}
        if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0.0f);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0.0f);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0.0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*8){alive = false;dyingCounter = 0;}
    }
    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     * Outputs:
     ***************************************************************************/
    public void  damageplayer(int attack){
        gp.playSE(9);

        int damage = attack - gp.player.defense;
        if (damage < 0){
            damage = 0;
        }
        gp.player.life -= damage;
        gp.player.invincible = true;
        gp.player.invincibleCounter = 0;


    }
    /**************************************************************************
     * Method:
     * Purpose:
     * Inputs:
     * Outputs:
     ***************************************************************************/
    public  void  changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}
