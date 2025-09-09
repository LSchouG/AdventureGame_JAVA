/**
 * *****************************************************************************
 * FileName: UI.java
 * Purpose: Handles user interface rendering, such as messages and pause screen.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Renders in-game text using Graphics2D
 * - Supports center alignment, play/pause states, and messages
 *******************************************************************************/

package adventuregame;

import adventuregame.entity.Entity;
import adventuregame.objects.OBJ_Coin_Bronze;
import adventuregame.objects.OBJ_Crystal;
import adventuregame.objects.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static javax.swing.plaf.basic.BasicGraphicsUtils.drawString;

public class UI {

    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;
    public int titleScreenState = 0; // 0: first screen 1: is the second screen ect.
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0; // subState 0 = option menu, 1 = full screen notification 2 = control 3 = end game confirmation
    int counter = 0;
    public Entity npc;

    // UI States
    GamePanel gp;
    Graphics2D g2;
    Font titelFont, menuFontP, menuFontB, dialogueFont, onScreenPopUpFont;
    Color menuAndDialogFontColor, blackColor, grayColor;
    BufferedImage fullHeart, halfHeart, blankHeart, fullCrystal, halfCrystal, blankCrystal, coin;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    /**************************************************************************
     * Constructor: UI(GamePanel gp)
     * Purpose: Initializes fonts and sets reference to the main game panel.
     ***************************************************************************/
    public UI(GamePanel gp) {
        this.gp = gp;
        // FONTS
        titelFont = new Font("Arial", Font.BOLD, 70);
        menuFontB = new Font("Arial", Font.BOLD, 30);
        menuFontP = new Font("Arial", Font.PLAIN, 30);
        dialogueFont = new Font("Arial", Font.PLAIN, 20);
        onScreenPopUpFont = new Font("Arial", Font.BOLD, 20);


        // SET FONT COLOR TO WHITE
        menuAndDialogFontColor = Color.WHITE;
        blackColor = Color.BLACK;
        grayColor = Color.GRAY;

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        fullHeart = heart.image1;
        halfHeart = heart.image2;
        blankHeart = heart.image3;
        Entity crystal = new OBJ_Crystal(gp);
        fullCrystal = crystal.image3;
        halfCrystal = crystal.image2;
        blankCrystal = crystal.image1;
        blankCrystal = crystal.image1;
        Entity coinBronze = new OBJ_Coin_Bronze(gp);
        coin = coinBronze.down1;


    }
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    public void trade_buy(){

        // DRAW TWO INVENTORY SLOTS
        drawInventory(gp.player, false);
        drawInventory(npc, true);

        // DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = (gp.tileSize * 9) + 20;
        int width = gp.tileSize * 6;
        int height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ENTER] Buy - [ESC] Back", x + 24, y + 55);

        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Gold: " + gp.player.gold, x + 24, y + 55);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){

            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin, x+10, y+9, 32,32, null);

            int price = npc.inventory.get(itemIndex).Price;
            String text = "" + price;
            x = getXForAlignTextToRight(text, gp.tileSize*8-20);
            g2.drawString(text, x, y+33);

            // BUY AN ITEM
            if (gp.keyH.enterPressed == true) {
                if(npc.inventory.get(itemIndex).Price > gp.player.gold){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more gold to buy that";
                    drawDialogueScreen();
                }
                else if (gp.player.inventory.size() >= gp.player.maxInventorySize){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You inventory is full";
                    drawDialogueScreen();
                } else {
                    gp.player.gold -= npc.inventory.get(itemIndex).Price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                    npc.inventory.remove(itemIndex);
                }
            }

        }
    }
    public void trade_sell(){

        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // DRAW HINT WINDOW
        x = gp.tileSize * 2;
        y = (gp.tileSize * 9) + 20;
        width = gp.tileSize * 6;
        height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ENTER] Sell - [ESC] Back", x + 24, y + 55);

        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Gold: " + gp.player.gold, x + 24, y + 55);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){

            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin, x+10, y+9, 32,32, null);

            int price = gp.player.inventory.get(itemIndex).Price /3;
            String text = "" + price;
            x = getXForAlignTextToRight(text, gp.tileSize*18-20);
            g2.drawString(text, x, y+33);

            // BUY AN ITEM
            if (gp.keyH.enterPressed == true) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentShield || gp.player.inventory.get(itemIndex) == gp.player.currentWeapon )
                {
                    commandNumber = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot sell an equipped item";
                } else {
                    gp.player.inventory.remove(itemIndex);
                    gp.player.gold += price;
                }

            }
        }
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(dialogueFont);
        g2.setColor(menuAndDialogFontColor);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(gp);
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLifeAndMana();
            drawMessage();
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPlayerLifeAndMana();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterAttributes();
            drawInventory(gp.player, true);
        }
        // OPTION STATE
        if (gp.gameState == gp.optionState) {
            drawOptionMenuScreen();
        }
        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        // TRANSITION STATE
        if (gp.gameState == gp.transitionState) {
            drawTransition();
        }
        // SHOP STATE
        if (gp.gameState == gp.shopState) {
            drawShopScreen();
        }
    }
    public void drawShopScreen(){

        switch (subState) {
            case 0: trade_select(); break;
            case 1: trade_buy();    break;
            case 2: trade_sell();   break;
        }
        gp.keyH.enterPressed = false;

    }
    public void trade_select() {
        drawDialogueScreen();

        // DRAW WINDOW
        int x = gp.tileSize * 14;
        int y = gp.tileSize * 5;
        int width = gp.tileSize * 3;
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        // DRAW TEXT
        g2.setFont(menuFontP);
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNumber == 0){
            g2.drawString(">", x - (gp.tileSize / 2), y);
            if(gp.keyH.enterPressed){
                subState = 1;
                gp.keyH.enterPressed = false;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNumber == 1){
            g2.drawString(">", x - (gp.tileSize / 2), y);
            if(gp.keyH.enterPressed){
                subState = 2;
                gp.keyH.enterPressed = false;
            }
        }
        y += gp.tileSize;
        g2.drawString("Back", x, y);
        if(commandNumber == 2){
            g2.drawString(">", x - (gp.tileSize / 2), y);
            if(gp.keyH.enterPressed){
                gp.gameState = gp.playState;
                commandNumber = 0;
                gp.keyH.enterPressed = false;
                currentDialogue = "Come Again soon lad";
            }
        }
    }
    private void drawInventory(Entity entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            frameX = (gp.screenWidth / 2) + gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = (gp.screenWidth / 2) - (gp.tileSize * 4);
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX =  gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = (gp.screenWidth / 2) - (gp.tileSize * 4);
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSizeX = gp.tileSize + 2;
        int slotSizeY = gp.tileSize + 3;

        // DRAW PLAYERS ITEMS
        for (int i = 0; i < entity.inventory.size(); i++) {

            // EQUIP CURSOR
            if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, slotSizeX, slotSizeY, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSizeX;

            if (i == 4 || i == 9 || i == 14 || i == 19) {
                slotY += slotSizeY;   // MOVE DOWN ONE ROW
                slotX = slotXstart;   // RESET X
            }
        }

        //CURSOR
        if(cursor == true){
            int cursorX = slotXstart + (slotSizeX * slotCol);
            int cursorY = slotYstart + (slotSizeY * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;
            // DRAW CURSOR
            g2.setColor(menuAndDialogFontColor);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = (gp.tileSize * 3) + 20 ;
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                // DRAW DESCRIPTION TEXT
                int textX = dFrameX + 20;
                int textY = dFrameY + 40;
                g2.setFont(g2.getFont().deriveFont(20f));

                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                // Draw TITLE AND PRICE
                String text = entity.inventory.get(itemIndex).itemTitle;

                g2.drawString(text, textX, textY);
                textY += 32;

                // Draw DESCRIPTION
                for (String line : entity.inventory.get(itemIndex).itemDescription.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }
    public void drawTransition() {

        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eventHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eventHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eventHandler.tempRow;
            gp.eventHandler.previousEventX = gp.player.worldX;
            gp.eventHandler.previousEventY = gp.player.worldY;

        }

    }
    public void drawGameOverScreen() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(titelFont);

        // SHADOW
        text = "GAME OVER";
        g2.setColor(Color.black);
        x = getXForCenter(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // MAIN TEXT
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // RETRY
        g2.setFont(menuFontB);
        text = "Try again";
        x = getXForCenter(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNumber == 0) {
            g2.drawString(">", x - (gp.tileSize / 2), y);
        }

        // BACK TO TITLE
        text = "Back to Title";
        x = getXForCenter(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNumber == 1) {
            g2.drawString(">", x - (gp.tileSize / 2), y);
        }


    }
    public void drawPlayerLifeAndMana() {
        //DRAW LIFE
        // DRAW BLANK HEARTS FIRST
        int x = gp.tileSize / 2;
        int y = (gp.tileSize / 2) - 10;
        int i = 0;

        while (i < gp.player.maxLife / 2) {
            g2.drawImage(blankHeart, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize / 2;
        y = (gp.tileSize / 2) - 10;
        i = 0;

        // DRAW HALF HEARTS
        while (i < gp.player.life) {
            g2.drawImage(halfHeart, x, y, null);
            i++;
            // DRAW FULL HEARTS
            if (i < gp.player.life) {
                g2.drawImage(fullHeart, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }


        //DRAW MANA
        // DRAW BLANK CRYSTALS FIRST
        x = gp.tileSize / 2;
        y = (gp.tileSize * 2) - 25;
        i = 0;

        while (i < gp.player.maxMana / 2) {
            g2.drawImage(blankCrystal, x, y, null);
            i++;
            x += 35;
        }

        //RESET
        x = gp.tileSize / 2;
        y = (gp.tileSize * 2) - 25;
        i = 0;

        while (i < gp.player.mana) {
            g2.drawImage(halfCrystal, x, y, null);
            i++;
            // DRAW FULL HEARTS
            if (i < gp.player.mana) {
                g2.drawImage(fullCrystal, x, y, null);
            }
            i++;
            x += 35;
        }

    }
    public void drawMessage() {

        int massageX = gp.tileSize;
        int massageY = gp.tileSize * 4;
        g2.setFont(onScreenPopUpFont);

        //DEBUG
        // System.out.println("message.size(): " + message.size());

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {

                // SHADOW
                g2.setColor(blackColor);
                g2.drawString(message.get(i), massageX + 3, massageY + 3);

                // TEXT
                g2.setColor(menuAndDialogFontColor);
                g2.drawString(message.get(i), massageX, massageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter); // set the counter to the array
                massageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }

            }

        }

    }
    public void drawTitleScreen(GamePanel gp) {

        // First screen
        if (titleScreenState == 0) {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(titelFont);
            String text = "ADVENTURE GAME";
            int x = getXForCenter(text);
            int y = gp.tileSize * 3;
            //SHADOW
            g2.setColor(grayColor);
            g2.drawString(text, x + 3, y + 3);

            // MAIN COLOR
            g2.setColor(menuAndDialogFontColor);
            g2.drawString(text, x, y);

            // IMAGEs
            x = (gp.screenWidth / 2) - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.downStill, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU
            g2.setFont(menuFontP);
            text = "NEW GAME";
            x = getXForCenter(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "LOAD GAME";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if (commandNumber == 1) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "QUIT";
            x = getXForCenter(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if (commandNumber == 2) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }
        }
        /*else if (titleScreenState == 1) {
            g2.setColor(menuAndDialogFontColor);
            g2.setFont(titelFont);

            String text = "Select your class!";
            int x = getXForCenter(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);


            g2.setFont(menuFontP);
            text = "fighter";
            x = getXForCenter(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "Theif";
            x = getXForCenter(text);
            y += gp.tileSize * 1;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "Sorcerer";
            x = getXForCenter(text);
            y += gp.tileSize * 1;
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }

            g2.setFont(menuFontP);
            text = "Back";
            x = getXForCenter(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNumber == 3) {
                g2.setFont(menuFontB);
                g2.drawString(">", x - (gp.tileSize / 2), y);
            }
        }*/

    }
    public void drawDialogueScreen() {
        // window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(dialogueFont);
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 30;

            gp.player.attackCanceled = true;
        }

    }
    public void drawOptionMenuScreen() {
        g2.setColor(menuAndDialogFontColor);
        g2.setFont(menuFontP);

        // Full SCREEN OPTION MENU
        //g2.setColor(new Color(0, 0, 0));
        //g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // SUBWINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                option_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
                break;
        }

        gp.keyH.enterPressed = false;

    }
    public void drawCharacterAttributes() {
        // CREATE A FRAME FOR VIEWING THE ATTRIBUTES OF THE CHARACTER
        final int frameX = gp.tileSize * 1;
        final int frameY = gp.tileSize * 1;
        final int frameWidth = (gp.screenWidth / 2) - (gp.tileSize * 3);
        final int frameHeight = gp.screenHeight - (gp.tileSize * 2);
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(menuAndDialogFontColor);
        g2.setFont(menuFontP);

        int textX = frameX + 20;
        int textY = frameY + 45;
        final int lineHeight = 36;

        // NAME
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Gold", textX, textY);
        textY += lineHeight + 5;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 8;
        g2.drawString("Shield", textX, textY);

        // RESET
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + 45;
        String value;

        // VALUES
        value = String.valueOf(gp.player.level);
        textX = getXForAlignTextToRight(value, tailX);

        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.gold);
        textX = getXForAlignTextToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += 5;

        // weapon images
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
        textY += gp.tileSize - 5;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);


    }
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 180);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(menuAndDialogFontColor);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public void drawPauseScreen() {

        g2.setFont(titelFont);
        String text = "PAUSED";
        int x = getXForCenter(text);
        int y = getYForCenter();
        g2.drawString(text, x, y);
    }
    public void option_top(int frameX, int frameY) {
        int textX;
        int textY;
        final int lineHeight = 36;

        g2.setFont(menuFontB);
        // TITLE
        String text = "Options";
        textX = getXForCenter(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        g2.setFont(menuFontP);
        // FULL SCREEN ON/OFF
        text = "Full Screen";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - (gp.tileSize / 2), textY);
            if (gp.keyH.enterPressed == true) {
                if (gp.fullScreen == false) {
                    gp.fullScreen = true;
                } else if (gp.fullScreen == true) {
                    gp.fullScreen = false;
                }
                subState = 1;
            }
        }

        // MUSIC
        text = "Music";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - (gp.tileSize / 2), textY);
        }

        // SE
        text = "SE";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNumber == 2) {
            g2.drawString(">", textX - (gp.tileSize / 2), textY);
        }

        // CONTROL
        text = "Control";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNumber == 3) {
            g2.drawString(">", textX - (gp.tileSize / 2), textY);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
                commandNumber = 0;
            }
        }

        // END GAME
        text = "End game";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNumber == 4) {
            g2.drawString(">", textX - (gp.tileSize / 2), textY);
           if (gp.keyH.enterPressed == true) {
                subState = 3;
                commandNumber = 0;
            }
        }

        // BACK
        text = "Back";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);
        if (commandNumber == 5) {
            g2.drawString(">", textX - (gp.tileSize / 2), textY);
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNumber = 0;
            }
        }

        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize * 4.8);
        textY = frameY + gp.tileSize * 3 - 22;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreen == true) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // MUCIS VOLUME CHECK BOX
        textY = textY + gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SE VOLUME CHECK BOX
        textY = textY + gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int seWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, seWidth, 24);

        // SAVE TO CONFIG FILE
        gp.config.saveConfig();
    }
    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;
        int divnumb = 2;

        // TITLE
        String text = "Control";
        textX = getXForCenter(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Inventory", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textY += gp.tileSize;


        textX = frameX + (gp.tileSize * 6) - gp.tileSize / 2;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Space", textX, textY);
        textY += gp.tileSize;
        g2.drawString("E", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Esc", textX, textY);

        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + (gp.tileSize * 9);
        g2.drawString("Back", textX, textY);
        g2.drawString(">", textX - (gp.tileSize / 2), textY);
            if (gp.keyH.enterPressed == true){
                subState = 0;
                commandNumber = 3;
         }
    }
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game and  \nreturn to the \ntitle screen?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        // Yes
        String text = "Yes";
        textX = getXForCenter(text);
        textY = frameY + (gp.tileSize * 6);
        g2.drawString(text, textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX - gp.tileSize / 2, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }


        // NO
        String text2 = "No";
        textX = getXForCenter(text2);
        textY = frameY + (gp.tileSize * 8);
        g2.drawString(text2, textX, textY);
        if (commandNumber == 1) {
            g2.drawString(">", textX - gp.tileSize / 2, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNumber = 4;
            }
        }
        System.out.println(gp.ui.commandNumber);
    }
    public void options_fullScreenNotification(int frameX, int frameY) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        currentDialogue = "The change will take \neffect after you restart \nthe game.";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY = frameY + (gp.tileSize * 9);
        g2.drawString("Back", textX, textY);
        g2.drawString(">", textX - (gp.tileSize / 2), textY);
        if (gp.keyH.enterPressed == true) {
                if (gp.keyH.enterPressed == true) {
                    subState = 0;
                    commandNumber = 0;
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }
    public int getXForCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth / 2) - (length / 2);
    }
    public int getXForAlignTextToRight(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (tailX - length);
    }
    public int getYForCenter() {
        FontMetrics fm = g2.getFontMetrics();
        return (gp.screenHeight / 2) - (fm.getHeight() / 2) + fm.getAscent();
    }
}
