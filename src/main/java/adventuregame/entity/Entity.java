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
import java.util.ArrayList;
import java.util.Random;

public class  Entity {
    protected GamePanel gp;

    public BufferedImage downStill, down1, down2, upStill, up1, up2, leftStill, left1, left2, rightStill,
                          right1, right2, dead;
    public BufferedImage attackUp1, attackDown1, attackLeft1, attackRight1, attackUp2, attackDown2, attackLeft2,
                         attackRight2, guardUp, guardDown, guardRight, guardLeft ;
    public BufferedImage image1, image2, image3;      // Object image/sprite
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // collision bounds
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public String dialogues[][] = new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;
    public boolean temp = false;

    /** STATE **/
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNumber = 1;
    public  int dialogueIndex = 0;
    public int dialogueSet = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockback = false;
    public String knockbackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;
    public boolean inRage = false;
    public boolean boss = false;
    public boolean sleep = false;
    public boolean drawing = true;

    /** COUNTER **/
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int coolDownMagicCounter = 100;
    int knockbackCounter;
    int guardCounter = 0;
    int offBalanceCounter = 0;

    /** CHARACTER ATTRIBUTES **/
    public String name; // Object name identifier
    public int defaultSpeed;
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
    public int exp;
    public int baseXP;
    public int nextLevelExp;
    public int  gold;
    public int Price;
    public int motion1_duration;
    public int motion2_duration;
    public Entity user;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;

    // MONSTER ATTRIBUTES
    public int distanceToChase = 0; // Distance before chasing
    public int rate = 0;  // chance on actual chasing
    public  int shotInterval = 0;
    public  int directionInterval = 120; // Default value

    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>(); // PLAYER'S ITEM INVENTORY
    public final int maxInventorySize = 20; // MAXIMUM NUMBER OF ITEMS PLAYER CAN CARRY
    public int attackValue;
    public int  defenseValue;
    public int  restoreValue;
    public String itemDescription = "";
    public String itemTitle = "";
    public String itemSellPrice = "";
    public String itemBuyPrice = "";
    public int useCost; //
    public int value;
    public String lockKeyType;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius = 0;

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
    public final int type_obstacle = 9;
    public final int  type_light = 10;
    public final int type_pickaxe = 11;
    public final int type_NonUse = 12;



    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public int getLeftX(){
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getRBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol(){
        return (worldX + solidArea.x)/ gp.tileSize;
    }
    public int getRow(){
        return (worldY + solidArea.y)/ gp.tileSize;
    }
    public int getXDistance(Entity target){
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return  xDistance;
    }
    public int getYDistance(Entity target){
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return  yDistance;
    }
    public int getCenterX(){
        int CenterX = worldX + up1.getWidth()/2;
        return CenterX;
    }
    public int getCenterY(){
        int CenterY = worldY + up1.getHeight()/2;
        return CenterY;
    }
    public int getScreenX(){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }
    public int getScreenY(){
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }
    public int getTileDistance(Entity target){
        int tileDistance = ((getXDistance(target) + getYDistance(target)) / gp.tileSize);
        return tileDistance;
    }
    public String getOppositeDirection(String direction){

        String OppositeDirection = "";
        switch (direction){
            case "up": OppositeDirection = "down"; break;
            case "down": OppositeDirection = "up"; break;
            case "left": OppositeDirection = "right"; break;
            case "right": OppositeDirection = "left"; break;
        }
        return OppositeDirection;
    }
    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }
    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }
    public void getRandomDirection(int directionInterval) {
        actionLockCounter++;
        if (actionLockCounter > directionInterval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Generate number between 1–100

            if (i <= 25) { direction = "up";}
            else if (i <= 50) { direction = "down";}
            else if (i <= 75) { direction = "left";}
            else    { direction = "right";}
            actionLockCounter = 0;
        }
    }
    public Color getParticalColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public int getDetected(Entity user, Entity target[][], String targetName) {
        // Project the user's position forward based on direction
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        int index = 999;

        switch (user.direction) {
            case "up": nextWorldY = user.getTopY() - gp.player.speed; break;
            case "down": nextWorldY = user.getRBottomY() + gp.player.speed; break;
            case "left": nextWorldX = user.getLeftX() - gp.player.speed; break;
            case "right": nextWorldX = user.getRightX() + gp.player.speed; break;
        }

        // Calculate the exact adjacent tile (clamped to map bounds)
        int col = Math.max(0, Math.min(gp.maxWorldCol - 1, nextWorldX / gp.tileSize));
        int row = Math.max(0, Math.min(gp.maxWorldRow - 1, nextWorldY / gp.tileSize));  // No -1 offset

        // Log all potential targets in the current map for comparison
        for (int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null) {
            }
        }

        // Search only in the projected single tile
        for (int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null) {
                int targetCol = target[gp.currentMap][i].getCol();
                int targetRow = target[gp.currentMap][i].getRow();

                if (targetCol == col && targetRow == row && target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    public void setKnockback(Entity target, Entity attacker, int knockBackPower){
        this.attacker = attacker;
        target.knockbackDirection = attacker.direction;
        target.speed += attacker.knockBackPower;
        target.knockback = true;
    }
    public void setAction() {}
    public void resetCounter(){
        /** COUNTER **/
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockbackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }
    public void moveTowardPlayer(int interval){
        actionLockCounter++;

        if (actionLockCounter > interval){
            if(getXDistance(gp.player) > getYDistance(gp.player)){
                if(gp.player.getCenterX() < getCenterX()){
                    direction = "left";
                } else {
                    direction = "right";
                }
            }else if (getXDistance(gp.player) < getYDistance(gp.player)){
                if(gp.player.getCenterY() < getCenterY()){
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if (i == 0){
                onPath = true;
            }
        }
    }
    public void checkStopChasingOrNot(Entity target, int distance, int rate){
        if (getTileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if (i == 0){
                onPath = false;
            }
        }
    }
    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter > shotInterval){

            projectile.set(worldX, worldY, direction, true, this);
            //gp.projectileList.add(projectile);
            // ADD PROJECTILES TO LIST
            for (int j = 0; j < gp.projectile[1].length; j++) {
                if (gp.projectile[gp.currentMap][j] == null) {
                    gp.projectile[gp.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetInRange = false;
        int xDis = getXDistance(gp.player);
        int yDis = getYDistance(gp.player);

        switch (direction){
            case "up":
                if(gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if(gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if(gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
        }

        if(targetInRange == true) {
            // Check if it attacks
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                spriteNumber = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    public void checkCollision(){
        // Reset collision status before checking again
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monster);
        gp.collisionChecker.checkEntity(this, gp.iTile);

        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer == true && gp.player.invincible == false){
            damagePlayer(attack);
        }
    }
    public void update() {

        if (sleep == false){
            if (knockback == true){
                if (knockbackCounter > 4){
                    checkCollision();
                }
                if (collisionOn == true){
                    knockbackCounter = 0;
                    knockback = false;
                    speed = defaultSpeed;
                }
                else if(collisionOn == false){
                    switch (knockbackDirection){
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                    knockbackCounter++;
                    if(knockbackCounter == 10){
                        knockbackCounter = 0;
                        knockback = false;
                        speed = defaultSpeed;
                    }
                }

            }
            else if (attacking == true){
                attacking();
            }
            else {
                setAction();
                checkCollision();

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
                if (spriteCounter > 24) {
                    spriteNumber++;
                    if (downStill == null) {
                        // Two-image cycle: spriteNumber cycles between 1 and 2
                        if (spriteNumber > 2) {
                            spriteNumber = 1;
                        }
                    } else {
                        // Three-image cycle: spriteNumber cycles between 0 and 2
                        if (spriteNumber > 2) {
                            spriteNumber = 0;
                        }
                    }
                    spriteCounter = 0;
                }
            }

            // this needs to be outside of key if statment
            if (invincible == true) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    transparent = false;
                    invincibleCounter = 0;
                }
            }
            if(shotAvailableCounter < coolDownMagicCounter){
                shotAvailableCounter++;
            }
            if(offBalance == true ){
                offBalanceCounter++;
                if (offBalanceCounter > 60){
                    offBalance = false;
                    offBalanceCounter = 0;

                }
            }
        }
    }
    public boolean inCamera(){
        boolean inCamera = false;
        // Only draw if within visible screen bounds
        if (worldX + gp.tileSize*5 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize*5 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Only draw if within visible screen bounds
        if (inCamera() == true) {

            // Select sprite based on direction and sprite number
            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "down":
                    if (dying) {image = dead;}
                    else if (!attacking) {
                        if (spriteNumber == 0) image = downStill;
                        if (spriteNumber == 1) image = down1;
                        if (spriteNumber == 2) image = down2;
                    }
                    else {
                        if (spriteNumber == 1) image = attackDown1;
                        if (spriteNumber == 2) image = attackDown2;
                    }
                    break;
                case "up":
                    if (dying) {image = dead;}
                    else if (!attacking) {
                        if (spriteNumber == 0) image = upStill;
                        if (spriteNumber == 1) image = up1;
                        if (spriteNumber == 2) image = up2;
                    }
                    else {
                        tempScreenY = getScreenY() - up2.getHeight();
                        if (spriteNumber == 1) image = attackUp1;
                        if (spriteNumber == 2)  image = attackUp2;

                    }
                    break;
                case "right":
                    if (dying) {image = dead;}
                    else if (!attacking) {
                        if (spriteNumber == 0) image = rightStill;
                        if (spriteNumber == 1) image = right1;
                        if (spriteNumber == 2) image = right2;
                    }
                    else {
                        if (spriteNumber == 1) image = attackRight1;
                        if (spriteNumber == 2) image = attackRight2;
                    }
                    break;
                case "left":
                    if (dying) {image = dead;}
                    else if (!attacking) {
                        if (spriteNumber == 0) image = leftStill;
                        if (spriteNumber == 1) image = left1;
                        if (spriteNumber == 2) image = left2;
                    }
                    else {
                        tempScreenX = getScreenX() - left2.getWidth();
                        if (spriteNumber == 1) image = attackLeft1;
                        if (spriteNumber == 2) image = attackLeft2;
                    }
                    break;
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

            g2.drawImage(image, tempScreenX, tempScreenY, null); // draw sprite
            changeAlpha(g2, 1f);
        }
    }
    public void damageReaction() {}
    public void damagePlayer(int attack) {

        if (gp.player.invincible == false)
        {
           int  damage = attack - gp.player.defense;

            if (gp.player.guarding == true && gp.player.direction.equals(getOppositeDirection(direction))){
                // parry
                if (gp.player.guardCounter < 15){
                    damage = 0;
                    setKnockback(this, gp.player, gp.player.currentShield.knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60;
                    gp.playSE(17);
                } else {
                    // normal Guard
                    damage /= 3;
                    if (damage < 0) {damage = 0;}
                    gp.playSE(18);
                }
            }
            else
            {   // not guarding
                gp.playSE(9);
                if (damage < 1) {
                    damage = 1;
                }
            }
            if (damage != 0) {
                gp.player.transparent = true;
                setKnockback(gp.player,this,knockBackPower);
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void interact(){}
    public void speak() {
    }
    public void facePlayer(){
        switch (gp.player.direction) {
            case "up":direction = "down"; break;
            case "down":direction = "up"; break;
            case "left":  direction = "right"; break;
            case "right": direction = "left";
        }
    }
    public void startDialogue(Entity entity, int setNum){
        gp.previousState = gp.gameState;
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }
    public boolean use(Entity entity) { return false;}
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {

        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // monster's position
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void getDyingImages(){}
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
    public void attacking() {

        spriteCounter++; // INCREMENT SPRITE COUNTER EACH FRAME

        if (spriteCounter <= motion1_duration) {
            spriteNumber = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter < motion2_duration) {
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
            if(type == type_monster){
                if(gp.collisionChecker.checkPlayer(this) == true){
                    damagePlayer(attack);
                }
            }
            else {
                // CHECK IF ATTACK AREA HITS A MONSTER
                int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex,this, attack, gp.player.currentWeapon.knockBackPower);

                // CHECK IF ATTACK AREA HITS AN INTERACTIVE TILE (ONCE PER ATTACK)
                int iTileIndex = gp.collisionChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                // CHECK projectileIndex
                int projectileIndex = gp.collisionChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }


            // RESTORE ORIGINAL POSITION AND COLLISION AREA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > motion2_duration) {
            spriteNumber = 1; // RESET TO DEFAULT
            spriteCounter = 0; // RESET COUNTER
            attacking = false; // EXIT ATTACK STATE
        }
    }
    public  void move(String direction){}
    public  void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public void generatePartical(Entity generator, Entity target){

        Color color = generator.getParticalColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp,target,color,size,speed,maxLife, -2, -1); // TOP LEFT
        Particle p2 = new Particle(gp,target,color,size,speed,maxLife, 2, -1); // TOP RIGTH
        Particle p3 = new Particle(gp,target,color,size,speed,maxLife, -2, 1); // BOTTOM LEFT
        Particle p4 = new Particle(gp,target,color,size,speed,maxLife, 2, 1); // BOTTOM RIGHT
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pfinder.setNodes(startCol, startRow, goalCol,goalRow, this);
        if (gp.pfinder.search() == true){
            //next worldX & worldY
            int nextX = gp.pfinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pfinder.pathList.get(0).row * gp.tileSize;

            // ENTITY SolidArea Position
            int enLeftX = worldX + solidArea.x;
            int enRight = enLeftX + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = enTopY + solidArea.height;

            // Check if ok to go up down left right,
            if(enTopY > nextY && enLeftX >= nextX && enRight < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRight < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                if(enLeftX > nextX){
                    direction = "left";
                }
                if (enLeftX < nextX){
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collisionOn ==true){
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn == true){
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                //down or left
                direction = "down";
                checkCollision();
                if (collisionOn == true){
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                //down or right
                direction = "down";
                checkCollision();
                if (collisionOn == true){
                    direction = "right";
                }
            }
        }
    }
    public void setLoot(Entity loot) {
    }
    public int getParticalLife(){
        int life = 0;
        return life;
    }
    public void setItems() {
    }
}
