/******************************************************************************
 * FileName: Player.java
 * Purpose: REPRESENTS THE PLAYER CHARACTER WITH MOVEMENT, COLLISION, AND ANIMATION.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - INHERITS FROM ENTITY
 * - HANDLES KEYBOARD INPUT AND CHARACTER SPRITE ANIMATION
 * - USES COLLISION DETECTION AND SUPPORTS OBJECT INTERACTIONS
 *******************************************************************************/
package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.KeyHandler;
import adventuregame.objects.OBJ_Axe;
import adventuregame.objects.OBJ_FireBall;
import adventuregame.objects.OBJ_Shield_Wood;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;               // PLAYER'S X POSITION ON SCREEN (CENTERED)
    public final int screenY;               // PLAYER'S Y POSITION ON SCREEN (CENTERED)
    int standCounter = 0;                   // COUNTER FOR IDLE ANIMATION WHEN STANDING STILL
    public boolean attackCanceled = false;  // FLAG TO CANCEL ATTACK DURING NPC INTERACTION
    public boolean alreadyHitTile = false;  // PREVENT MULTIPLE HITS ON SAME TILE IN ONE ATTACK



    /**************************************************************************
     * Constructor: Player(GamePanel gp, KeyHandler keyH)
     * Purpose: INITIALIZES THE PLAYER OBJECT, DEFINES SCREEN POSITION, COLLISION AREAS,
     *          DEFAULT VALUES, SPRITES, AND STARTING EQUIPMENT.
     ***************************************************************************/
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        type = type_player;
        this.keyH = keyH;

        // PLACE PLAYER IN CENTER OF SCREEN
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // DEFINE THE PLAYER'S COLLISION BOX
        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 22;
        solidArea.height = 15;

        // DEFINE PLAYER ATTACK AREA SIZE
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();       // INITIALIZE PLAYER STATS AND POSITION
        getPlayerImages();        // LOAD PLAYER SPRITE IMAGES
        getPlayerAttachImages();  // LOAD ATTACK SPRITE IMAGES
        setItems();               // ADD STARTING ITEMS TO INVENTORY
    }

    /**************************************************************************
     * Method: setDefaultValues()
     * Purpose: SETS DEFAULT PLAYER STATS, EQUIPMENT, AND POSITION IN THE WORLD.
     ***************************************************************************/
    public void setDefaultValues() {
        worldX = gp.tileSize * 20;
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
        gold = 500;

        // SET DEFAULT EQUIPMENT
        currentWeapon = new OBJ_Axe(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_FireBall(gp); // projectile = new OBJ_Rock_Projectile(gp);

        attack = getAttack();   // CALCULATE INITIAL ATTACK VALUE
        defense = getDefense(); // CALCULATE INITIAL DEFENSE VALUE
        coolDownMagicCounter = 100; // TIME BETWEEN PROJECTILE USE
    }

    /**************************************************************************
     * Method: update()
     * Purpose: UPDATES PLAYER STATE, MOVEMENT, COLLISIONS, ATTACKING, SHOOTING,
     *          INVINCIBILITY, AND ANIMATION ON EACH GAME LOOP TICK.
     ***************************************************************************/
    public void update() {

        // HANDLE ATTACKING
        if (attacking == true) {
            attaching(); // EXECUTE ATTACK LOGIC
        }

        // HANDLE MOVEMENT/INPUT
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

            // 1. UPDATE PLAYER DIRECTION
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
                spriteNumber = 1; // START WALKING ANIMATION
            }

            // 2. HANDLE COLLISIONS

            collisionOn = false;

            // CHECK TILE COLLISION
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION AND PICKUP
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC INTERACTION
            int ncpIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(ncpIndex);

            // TRIGGER ATTACK IF NOT TALKING
            if (ncpIndex == 999) {
                handleAttack();
            }

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.collisionChecker.checkEntity(this, gp.iTile);

            // CHECK EVENT ZONES (PIT, HEAL, ETC.)
            gp.eventHandler.checkEvent();

            // 3. HANDLE MOVEMENT IF NO COLLISION
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // INITIATE ATTACK
            if (keyH.enterPressed == true && attackCanceled == false) {
                gp.playSE(8);        // PLAY ATTACK START SOUND
                attacking = true;    // ENABLE ATTACKING
                spriteCounter = 0;   // RESET ANIMATION COUNTER
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false; // RESET ENTER INPUT

            // ANIMATION COUNTER INCREMENT
            spriteCounter++;

            // SWITCH SPRITES EVERY 13 FRAMES
            if (spriteCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }

        // PLAYER STANDING STILL
        else {
            standCounter++;
            if (standCounter > 20) {
                spriteNumber = 0; // RESET TO IDLE FRAME
                standCounter = 0;
            }
        }

        // HANDLE PROJECTILE SHOOTING (E.G. FIREBALL)
        if (gp.keyH.shotKeyPressed == true
                && projectile.alive == false
                && shotAvailableCounter == coolDownMagicCounter
                && projectile.haveResource(this) == true) {

            projectile.set(worldX, worldY, direction, true, this); // INITIALIZE PROJECTILE
            projectile.subtractResource(this);                     // DEDUCT COST (MANA/AMMO)
            gp.projectileList.add(projectile);                     // ADD TO ACTIVE PROJECTILES
            shotAvailableCounter = 0;                              // RESET COOLDOWN
            gp.playSE(13);                                         // PLAY SHOOT SOUND
        }

        // HANDLE INVINCIBILITY TIMER
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 100) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // INCREMENT COOLDOWN FOR NEXT SHOT
        if (shotAvailableCounter < coolDownMagicCounter) {
            shotAvailableCounter++;
        }

        // CLAMP LIFE TO MAX
        if (life > maxLife) {
            life = maxLife;
        }

        // CLAMP MANA TO MAX
        if (maxMana > mana) {
            mana = maxMana;
        }

        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNumber = -1;
            gp.stopMusic();
            gp.playSE(14);
        }

    }

    public void setDefaultPositions(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 22;
        direction = "down";
        invincible = false;
        invincibleCounter = 0;
    }

    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
        invincibleCounter = 0;
    }
    /**************************************************************************
     * Method: setItems()
     * Purpose: ADDS STARTING ITEMS (WEAPON AND SHIELD) TO PLAYER INVENTORY.
     ***************************************************************************/
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon); // ADD STARTING WEAPON
        inventory.add(currentShield); // ADD STARTING SHIELD
    }

    /**************************************************************************
     * Method: getAttack()
     * Purpose: CALCULATES PLAYER ATTACK BASED ON STRENGTH AND EQUIPPED WEAPON.
     ***************************************************************************/
    public int getAttack() {
        attackArea = currentWeapon.attackArea; // SET ATTACK AREA BASED ON WEAPON
        return attack = strength * currentWeapon.attackValue; // RETURN CALCULATED ATTACK VALUE
    }

    /**************************************************************************
     * Method: getDefense()
     * Purpose: CALCULATES THE PLAYER'S DEFENSE VALUE BASED ON THEIR DEXTERITY
     *          AND THE CURRENTLY EQUIPPED SHIELD'S DEFENSE VALUE.
     ***************************************************************************/
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue; // CALCULATE TOTAL DEFENSE
    }

    /**************************************************************************
     * Method: getPlayerImages()
     * Purpose: LOAD AND ASSIGN ALL BASE MOVEMENT SPRITE IMAGES FOR THE PLAYER
     *          INCLUDING ALL DIRECTIONS AND WALKING FRAMES.
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
     * Method: getPlayerAttachImages()
     * Purpose: LOADS THE PLAYER'S ATTACK SPRITE IMAGES BASED ON DIRECTION.
     *          CURRENTLY LOADS UNIVERSAL SWING IMAGES FOR ALL WEAPONS.
     ***************************************************************************/
    public void getPlayerAttachImages() {

        /** IF USING SWORD-SPECIFIC IMAGES
         if (currentWeapon.type == type_sword){
         attachUp = setup("/images/player/attach-up.png", gp.tileSize, gp.tileSize *2);
         attachDown = setup("/images/player/attach-down.png", gp.tileSize, gp.tileSize *2);
         attachLeft = setup("/images/player/attach-left.png", gp.tileSize *2, gp.tileSize);
         attachRight = setup("/images/player/attach-right.png", gp.tileSize *2, gp.tileSize);
         }
         **/

        // UNIVERSAL ATTACK SWING IMAGES FOR ALL WEAPON TYPES
        attachUp = setup("/images/player/attack-up.png", gp.tileSize, gp.tileSize * 2);
        attachDown = setup("/images/player/attack-down.png", gp.tileSize, gp.tileSize * 2);
        attachLeft = setup("/images/player/attack-left.png", gp.tileSize * 2, gp.tileSize);
        attachRight = setup("/images/player/attack-right.png", gp.tileSize * 2, gp.tileSize);
    }

    /**************************************************************************
     * Method: pickUpObject(int i)
     * Purpose: HANDLES OBJECT PICKUP LOGIC WHEN THE PLAYER COLLIDES WITH AN OBJECT.
     *          USES PICKUP-ONLY ITEMS IMMEDIATELY OR ADDS TO INVENTORY IF SPACE.
     * Inputs: i - INDEX OF THE OBJECT COLLIDED WITH
     ***************************************************************************/
    public void pickUpObject(int i) {
        if (i != 999) {
            String text = "";

            // PICKUP-ONLY ITEMS (E.G. HEARTS, POWER-UPS)
            if (gp.obj[gp.currentMap][i].type == type_pickUpOnly) {
                gp.obj[gp.currentMap][i].use(this); // USE THE OBJECT IMMEDIATELY
                gp.obj[gp.currentMap][i] = null; // REMOVE OBJECT FROM WORLD
            }
            // PICKUP ITEMS THAT GO TO INVENTORY
            else {
                if (inventory.size() <= maxInventorySize) {
                    inventory.add(gp.obj[gp.currentMap][i]); // ADD TO INVENTORY
                    gp.playSE(2); // PLAY PICKUP SOUND
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "Your inventory is full!"; // INVENTORY LIMIT REACHED
                }
                gp.ui.addMessage(text); // DISPLAY FEEDBACK TO PLAYER
                gp.obj[gp.currentMap][i] = null; // REMOVE OBJECT FROM WORLD
            }
        }
    }

    /**************************************************************************
     * Method: attaching()
     * Purpose: HANDLES THE ATTACK ANIMATION, TEMPORARILY EXPANDS ATTACK AREA,
     *          AND CHECKS FOR COLLISIONS WITH MONSTERS AND INTERACTIVE TILES.
     * Inputs: NONE (USES INSTANCE VARIABLES FOR STATE AND DIRECTION)
     ***************************************************************************/
    public void attaching() {
        spriteCounter++; // INCREMENT SPRITE COUNTER EACH FRAME

        if (spriteCounter >= 3) {
            spriteNumber = 1; // FIRST ATTACK FRAME
        }

        if (spriteCounter > 3 && spriteCounter < 12) {
            spriteNumber = 2; // SECOND ATTACK FRAME

            // SAVE THE CURRENT POSITION AND COLLISION AREA
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // SHIFT PLAYER POSITION TEMPORARILY TO ATTACK AREA BASED ON DIRECTION
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
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

            // TEMPORARILY REPLACE COLLISION AREA WITH ATTACK AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // CHECK IF ATTACK AREA HITS A MONSTER
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            // CHECK IF ATTACK AREA HITS AN INTERACTIVE TILE (ONCE PER ATTACK)
            int iTileIndex = gp.collisionChecker.checkEntity(this, gp.iTile);
            if (!alreadyHitTile && iTileIndex != 999) {
                damageInteractiveTile(iTileIndex);
                alreadyHitTile = true; // PREVENT MULTIPLE HITS IN ONE ATTACK CYCLE
            }

            // RESTORE ORIGINAL POSITION AND COLLISION AREA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter >= 12 && spriteCounter < 15) {
            spriteNumber = 1; // ATTACK COOLDOWN FRAME
        }

        if (spriteCounter >= 15 && spriteCounter < 24) {
            spriteNumber = 2; // FINAL ATTACK FRAME
        }

        if (spriteCounter >= 24) {
            spriteNumber = 1; // RESET TO DEFAULT
            spriteCounter = 0; // RESET COUNTER
            attacking = false; // EXIT ATTACK STATE
            alreadyHitTile = false; // RESET TILE HIT FLAG
        }
    }

    /**************************************************************************
     * Method: interactNPC()
     * Purpose: INITIATES INTERACTION WITH AN NPC IF THE PLAYER IS NEARBY AND PRESSES ENTER.
     * Inputs: i - NPC INDEX
     ***************************************************************************/
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed && i != 999) {
            attackCanceled = true; // CANCEL ATTACK IF INTERACTING
            gp.gameState = gp.dialogueState; // SWITCH TO DIALOGUE MODE
            gp.npc[gp.currentMap][i].speak(); // CALL NPC SPEAK METHOD
        }
    }

    /**************************************************************************
     * Method: contactMonster()
     * Purpose: HANDLES DAMAGE TO PLAYER WHEN IN CONTACT WITH A MONSTER.
     * Inputs: i - MONSTER INDEX
     ***************************************************************************/
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && gp.monster[gp.currentMap][i].dying == false) {
                int damage = gp.monster[gp.currentMap][i].attack - defense; // CALCULATE DAMAGE
                if (damage < 0) {
                    damage = 1; // MINIMUM DAMAGE OF 1
                }

                gp.playSE(6); // PLAY DAMAGE SOUND
                life -= damage; // REDUCE PLAYER LIFE
                invincible = true; // MAKE PLAYER TEMPORARILY INVINCIBLE
            }
        }
    }

    /**************************************************************************
     * Method: handleAttack()
     * Purpose: INITIATES AN ATTACK WHEN THE ENTER KEY IS PRESSED AND THE PLAYER IS NOT CURRENTLY ATTACKING.
     * Inputs: NONE (USES INPUT HANDLER AND PLAYER STATE)
     ***************************************************************************/
    public void handleAttack() {
        if (gp.keyH.enterPressed && !attacking) {
            gp.playSE(9); // PLAY ATTACK SOUND EFFECT
            attacking = true; // SET PLAYER TO ATTACKING STATE
            spriteCounter = 0; // RESET SPRITE COUNTER FOR ATTACK ANIMATION
        }
    }

    /**************************************************************************
     * Method: damageMonster()
     * Purpose: CALCULATES AND APPLIES DAMAGE TO A MONSTER IF IT IS NOT INVINCIBLE.
     *          HANDLES MONSTER DEATH, EXP GAIN, AND LEVEL-UP CHECK.
     * Inputs: i - MONSTER INDEX; attack - PLAYER'S ATTACK VALUE
     ***************************************************************************/
    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (gp.monster[gp.currentMap][i].invincible == false) {

                int damage = attack - gp.monster[gp.currentMap][i].defense; // CALCULATE DAMAGE
                if (damage < 0) {
                    damage = 0; // ENSURE DAMAGE IS NOT NEGATIVE
                }

                gp.monster[gp.currentMap][i].life -= damage; // APPLY DAMAGE TO MONSTER
                gp.ui.addMessage(damage + " DAMAGE!"); // DISPLAY DAMAGE MESSAGE
                gp.monster[gp.currentMap][i].invincible = true; // SET MONSTER TO TEMPORARILY INVINCIBLE
                gp.monster[gp.currentMap][i].damageReaction(); // TRIGGER MONSTER'S DAMAGE REACTION

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.playSE(10); // PLAY MONSTER DEATH SOUND
                    gp.monster[gp.currentMap][i].dying = true; // SET MONSTER TO DYING STATE
                    gp.player.exp += gp.monster[gp.currentMap][i].exp; // GIVE PLAYER EXPERIENCE POINTS
                    gp.ui.addMessage("KILLED THE " + gp.monster[gp.currentMap][i].name + "!"); // DISPLAY KILL MESSAGE
                    gp.ui.addMessage("GAIN " + gp.monster[gp.currentMap][i].exp + " EXP!"); // DISPLAY EXP GAINED
                    checkLevelUp(); // CHECK IF PLAYER LEVELS UP
                }
            }
        }
    }

    /**************************************************************************
     * Method: damageInteractiveTile()
     * Purpose: DESTROYS AN INTERACTIVE TILE IF IT IS DESTRUCTIBLE AND PLAYER IS USING A VALID ITEM.
     *          PLAYS SOUND, REPLACES WITH DESTROYED FORM, AND LOGS TILE STATE TO CONSOLE.
     * Inputs: i - INTERACTIVE TILE INDEX
     ***************************************************************************/
    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true
                && gp.iTile[gp.currentMap][i].isCurrentItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {

            gp.iTile[gp.currentMap][i].playSE(); // PLAY TILE DESTRUCTION SOUND
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            generatePartical(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life <= 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm(); // REPLACE TILE WITH DESTROYED VERSION
            }

            // CAN IMPLEMENT SPECIAL WEAPON DAMAGE MECHANICS HERE
        }
    }

    /**************************************************************************
     * Method: checkLevelUp()
     * Purpose: CHECKS IF PLAYER'S EXPERIENCE REACHES THE NEXT LEVEL THRESHOLD,
     *          THEN LEVELS UP PLAYER STATS, UPDATES ATTACK/DEFENSE, AND PLAYS SOUND.
     ***************************************************************************/
    public void checkLevelUp() {
        if (gp.player.exp >= gp.player.nextLevelExp) {
            gp.player.level++;
            gp.player.nextLevelExp += gp.player.nextLevelExp * 2;
            gp.player.maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack(); // UPDATE ATTACK BASED ON NEW STATS
            defense = getDefense(); // UPDATE DEFENSE BASED ON NEW STATS
            gp.playSE(11); // PLAY LEVEL-UP SOUND
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + gp.player.level + " now! \n" +
                    "You feel stronger!";
        }
    }

    /**************************************************************************
     * Method: selectItem()
     * Purpose: SELECTS AN ITEM FROM THE INVENTORY BASED ON CURRENT UI SELECTION.
     *          EQUIPS WEAPONS, SHIELDS, OR USES CONSUMABLE ITEMS.
     ***************************************************************************/
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_spell) {
                currentWeapon = selectedItem; // EQUIP NEW WEAPON
                getAttack(); // UPDATE ATTACK
                getPlayerAttachImages(); // UPDATE ATTACK ANIMATION
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem; // EQUIP NEW SHIELD
                getDefense(); // UPDATE DEFENSE
                getPlayerAttachImages(); // UPDATE BLOCK ANIMATION
            }
            if (selectedItem.type == type_consumable) {
                selectedItem.use(this); // USE CONSUMABLE ITEM
                inventory.remove(itemIndex); // REMOVE USED ITEM
            }
        }
    }

    /**************************************************************************
     * Method: draw(Graphics2D g2)
     * Purpose: DRAWS THE PLAYER CHARACTER ON SCREEN BASED ON CURRENT STATE AND DIRECTION.
     * Inputs: g2 - THE GRAPHICS CONTEXT FOR RENDERING.
     ***************************************************************************/
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // TEMPORARY SCREEN COORDINATES FOR ATTACK IMAGE OFFSET FIXES
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "down":
                if (!attacking) {
                    if (spriteNumber == 0) image = downStill;
                    if (spriteNumber == 1) image = down1;
                    if (spriteNumber == 2) image = down2;
                } else {
                    if (spriteNumber == 1) image = down1;
                    if (spriteNumber == 2) image = attachDown;
                }
                break;
            case "up":
                if (!attacking) {
                    if (spriteNumber == 0) image = upStill;
                    if (spriteNumber == 1) image = up1;
                    if (spriteNumber == 2) image = up2;
                } else {
                    if (spriteNumber == 1) image = up1;
                    if (spriteNumber == 2) {
                        image = attachUp;
                        tempScreenY -= gp.tileSize; // OFFSET ATTACK SPRITE VERTICALLY
                    }
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNumber == 0) image = rightStill;
                    if (spriteNumber == 1) image = right1;
                    if (spriteNumber == 2) image = right2;
                } else {
                    if (spriteNumber == 1) image = right1;
                    if (spriteNumber == 2) image = attachRight;
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNumber == 0) image = leftStill;
                    if (spriteNumber == 1) image = left1;
                    if (spriteNumber == 2) image = left2;
                } else {
                    if (spriteNumber == 1) image = left1;
                    if (spriteNumber == 2) {
                        image = attachLeft;
                        tempScreenX -= gp.tileSize; // OFFSET ATTACK SPRITE HORIZONTALLY
                    }
                }
                break;
            default:
                if (!attacking) image = downStill;
                else {
                    if (spriteNumber == 0) image = downStill;
                    if (spriteNumber == 1) image = down1;
                    if (spriteNumber == 2) image = attachDown;
                }
                break;
        }

        // SET PLAYER TRANSPARENCY IF INVINCIBLE
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null); // DRAW PLAYER SPRITE
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // RESET TRANSPARENCY
    }
}
