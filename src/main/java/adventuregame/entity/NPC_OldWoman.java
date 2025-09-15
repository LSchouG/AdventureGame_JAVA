package adventuregame.entity;

import adventuregame.GamePanel;

import java.util.Random;

public class NPC_OldWoman extends Entity {

    public static final  String  npcName = "Old Woman Still";
    public NPC_OldWoman(GamePanel gp) {
        super(gp);
        type = type_npc;
        name = npcName;
        direction = "down"; // default facing direction
        speed = 3;           // slower than player
        solidArea.x = 0;// goes 0 pixel in from the side
        solidArea.y = 16;// goes 16 pixel down from the top
        solidArea.width = 48;// the space left, 48 - 0  = 48
        solidArea.height = 32;// the space left, 48 - 16 = 32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImages();         // load sprite images
        setDialogue();
        dialogueSet = -1;

    }
    public void getImages() {
        downStill = setup("/images/npc/girl-old-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/girl-old-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/girl-old-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/girl-old-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/girl-old-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/girl-old-up2.png", gp.tileSize, gp.tileSize);
        leftStill =setup("/images/npc/girl-old-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/girl-old-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/girl-old-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/girl-old-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/girl-old-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/girl-old-right2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {

        dialogues[0][0] = "Oh, bless yer brave young bones, dearie! These old eyes've seen many a storm,\nbut none so foul as these monsters whiskin' off our precious lambs\nand our wee bit o' shinies to their filthy nests.";
        dialogues[0][1] = "In me heyday, I'd have hexed 'em with me best glare and a pinch o' salt.\nNow? Me spells fizzle like wet fireworks, and me hips protest every step.";
        dialogues[0][2] = "Snatch back them darlin' mites from the monster's grasp, and the treasure's yers—\nall the gold, the baubles, even that locket what pinches the neck.";
        dialogues[0][3] = "A wee doze in the cottage bed'll knit yer scrapes right up, but hark:\nwhilst ye slumber like a log, them beasties rally their rabble and rattle their pots.";
        dialogues[0][4] = "Away with ye now, me fine feathered fool! And if the shadows nip at yer heels,\nfling 'em a rude gesture—I've taught ye better than to simper.";

        dialogues[1][0] = "Mercy me and all the saints' bloomers, ye've pulled it off! Our little whirlwinds are home,\ntrackin' mud and mischief everywhere—music to these weary ears.";
        dialogues[1][1] = "Thanks? Pish-posh, take the whole shebang: coffers, crowns, that cursed ring\nwhat makes yer toes itch like a bad rash. Lavish it on sweets... or sensible shoes.";
        dialogues[1][2] = "I'd knit ye a scarf o' gratitude, but me needles are bent as me temper.\nHave a hearty embrace instead—mind the flour on me apron, it's from dreamin' o' victory.";
        dialogues[1][3] = "Ye'll be the stuff o' legends, mark me words, ye plucky pudding.\nBut hush on the gorey bits; me constitution's frailer than autumn leaves.";
        dialogues[1][4] = "Godspeed, rescuer o' rascals. Chase more glory if ye must—\nI'll content meself with gossip and a nap what lasts till noon.";
    }
    public void setAction() {

        if (onPath == true){
            // GOAL TO WALK
            //int goalCol = 67; Walk strait to the goal
            //int goalRow = 23; Walk strait to the goal
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize; // player follow
            int goalRow = (gp.player.worldY + gp.player.solidArea.x) / gp.tileSize; // player follow
            searchPath(goalCol,goalRow);
        } else {
            actionLockCounter++;
            if (actionLockCounter > 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // Generate number between 1–100

                if (i <= 25) {
                    direction = "up";
                } else if (i <= 50) {
                    direction = "down";
                } else if (i <= 75) {
                    direction = "left";
                } else {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
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
}
