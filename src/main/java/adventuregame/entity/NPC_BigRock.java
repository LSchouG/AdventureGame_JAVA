package adventuregame.entity;

import adventuregame.GamePanel;
import adventuregame.objects.OBJ_IronDoor;
import adventuregame.tile_interactive.IT_MetalPlate;
import adventuregame.tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class NPC_BigRock extends Entity{

    public static final  String  npcName = "Big Rock";

    public NPC_BigRock(GamePanel gp) {
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 42;
        solidArea.height = 42;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dialogueSet = -1;
        getImages();
        setDialogue();
    }
    public void getImages() {
        downStill = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        down1= setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/objects_interactive/big-rock.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {
        dialogues[0][0] = "It is a giant rock!";
    }
    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);
        // Change the Dialogue Change later if want different logic
        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){
            dialogueSet = 0;
        }
    }
    public  void move(String d){
        this.direction = d;

        checkCollision();

        if(collisionOn == false){
            switch (direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        detectPlate();
    }
    public void detectPlate(){
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        // Create a plate liste
        for (int i = 0; i < gp.iTile[1].length; i++){
            if (gp.iTile[gp.currentMap][i] != null &&
                    gp.iTile[gp.currentMap][i].name != null &&
                gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)){
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }

        // Create a Entity liste
        for (int i = 0; i < gp.npc[1].length; i++){
            if (gp.npc[gp.currentMap][i] != null &&
                    gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)){
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        int count = 0;
        // SCAN THE TWO LIST
        for (int i = 0; i < plateList.size();i++){

            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);

            System.out.println("distance for " + distance + "  I  " + i);
            if (distance < 8){
                if(linkedEntity == null){
                    linkedEntity = plateList.get(i);
                    gp.playSE(3);
                    System.out.println("they a Linked!!");
                }
            } else {
                if(linkedEntity == plateList.get(i)){
                    linkedEntity = null;
                    System.out.println("they a notw Linked!!");
                }
            }
        }

        for (int i = 0; i < rockList.size(); i++){
            if (rockList.get(i).linkedEntity != null){
                count++;
            }
        }

        // IF ALL THE ROCK A ON THE PLATES the door opens
        if (count == rockList.size()) {

            for(int i = 0; i < gp.obj[1].length; i++){
                if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals((OBJ_IronDoor.objName))){
                    gp.obj[gp.currentMap][i] = null;
                    gp.playSE(21);
                }
            }
        }
    }
    public void setAction() {}
    public void update(){}
}

