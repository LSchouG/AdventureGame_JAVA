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
import adventuregame.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;               // PLAYER'S X POSITION ON SCREEN (CENTERED)
    public final int screenY;               // PLAYER'S Y POSITION ON SCREEN (CENTERED)
    int standCounter = 0;                   // COUNTER FOR IDLE ANIMATION WHEN STANDING STILL
    public boolean attackCanceled = false;  // FLAG TO CANCEL ATTACK DURING NPC INTERACTION
    public boolean alreadyHitTile = false;  // PREVENT MULTIPLE HITS ON SAME TILE IN ONE ATTACK
    public  boolean lightUpdated = false;



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
    public void setDefaultValues() {
        worldX = gp.tileSize * 60;
        worldY = gp.tileSize * 20;
        defaultSpeed = 4;
        speed = defaultSpeed;
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
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon); // ADD STARTING WEAPON
        inventory.add(currentShield); // ADD STARTING SHIELD
        inventory.add(new OBJ_Lantern(gp));
        OBJ_Tent tent = new OBJ_Tent(gp);
        tent.amount = 5;
        inventory.add(tent);
    }
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
            // ADD PROJECTILES TO LIST
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }                  // ADD TO ACTIVE PROJECTILES
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
    public int getAttack() {
        attackArea = currentWeapon.attackArea; // SET ATTACK AREA BASED ON WEAPON
        return attack = strength * currentWeapon.attackValue; // RETURN CALCULATED ATTACK VALUE
    }
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue; // CALCULATE TOTAL DEFENSE
    }
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
    public void getSleepingImages(BufferedImage image){
        downStill = image;
        down1 = image;
        down2 = image;
        upStill = image;
        up1 = image;
        up2 = image;
        leftStill = image;
        left1 = image;
        left2 = image;
        rightStill = image;
        right1 = image;
        right2 = image;
    }
    public void pickUpObject(int i) {
        if (i != 999) {
            String text = "";

            // PICKUP-ONLY ITEMS (E.G. HEARTS, POWER-UPS)
            if (gp.obj[gp.currentMap][i].type == type_pickUpOnly) {
                gp.obj[gp.currentMap][i].use(this); // USE THE OBJECT IMMEDIATELY
                gp.obj[gp.currentMap][i] = null; // REMOVE OBJECT FROM WORLD
            }
            // OBSTCLE
            else if (gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed == true){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            // PICKUP ITEMS THAT GO TO INVENTORY
            else {
                if (canObtainItem(gp.obj[gp.currentMap][i]) == true) {
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
    public void attaching() {

        spriteCounter++; // INCREMENT SPRITE COUNTER EACH FRAME

        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter < 25) {
            spriteNumber = 2;

            // SAVE THE CURRENT POSITION AND COLLISION AREA
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // SHIFT PLAYER POSITION TEMPORARILY TO ATTACK AREA BASED ON DIRECTION
            switch (direction) {
                case "up":worldY -= attackArea.height;break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right":  worldX += attackArea.width; break;
            }
            // TEMPORARILY REPLACE COLLISION AREA WITH ATTACK AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // CHECK IF ATTACK AREA HITS A MONSTER
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

            // CHECK IF ATTACK AREA HITS AN INTERACTIVE TILE (ONCE PER ATTACK)
            int iTileIndex = gp.collisionChecker.checkEntity(this, gp.iTile);
            if (!alreadyHitTile && iTileIndex != 999) {
                damageInteractiveTile(iTileIndex);
                alreadyHitTile = true;
            }

            // RESTORE ORIGINAL POSITION AND COLLISION AREA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        int projectileIndex = gp.collisionChecker.checkEntity(this, gp.projectile);
        damageProjectile(projectileIndex);


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
    public void damageProjectile(int index){
        if (index != 999){
            Entity projectile = gp.projectile[gp.currentMap][index];
            projectile.alive = false;
            generatePartical(projectile,projectile);
        }
    }
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed && i != 999) {
            attackCanceled = true; // CANCEL ATTACK IF INTERACTING
            gp.gameState = gp.dialogueState; // SWITCH TO DIALOGUE MODE
            gp.npc[gp.currentMap][i].speak(); // CALL NPC SPEAK METHOD
        }
    }
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
    public void handleAttack() {
        if (gp.keyH.enterPressed && !attackCanceled) {
            gp.playSE(9); // PLAY ATTACK SOUND EFFECT
            attacking = true; // SET PLAYER TO ATTACKING STATE
            spriteCounter = 0; // RESET SPRITE COUNTER FOR ATTACK ANIMATION
        }
    }
    public void knockback(Entity entity, int knockBackPower){
        entity.direction = direction;
        entity.speed += knockBackPower;
        entity.knockback = true;
    }
    public void damageMonster(int i, int attack, int knockBackPower) {
        if (i != 999) {
            if (gp.monster[gp.currentMap][i].invincible == false) {

                if (knockBackPower > 0){
                    knockback(gp.monster[gp.currentMap][i], knockBackPower);
                }
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
            if (selectedItem.type == type_consumable ) {
                if(selectedItem.use(this) == true){
                    if (selectedItem.amount > 1){
                        selectedItem.amount--;
                    }else {
                        inventory.remove(itemIndex);
                    }
                }
            }
            if (selectedItem.type == type_light) {
                    if (currentLight == selectedItem){
                        currentLight = null;
                    }else {
                        currentLight = selectedItem;
                    }
                    lightUpdated = true;
            }
        }
    }
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i++)
        {
            if (inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        // CHECK IF STACKABLE
        if(item.stackable == true)
        {
            int index = searchItemInInventory(item.name);
            if(index != 999){ // the item can be stack on another item
                inventory.get(index).amount++;
                canObtain = true;
            }else {// there is no other item in the inventory to stack on so a new slot is used
                if(inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        } else { // item is not stackable
            if(inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }
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