package adventuregame.entity;

import adventuregame.GamePanel;

import java.util.Random;

public class NPC_OldManStill extends Entity {

    public static final  String  npcName = "Old Man Still";
    public NPC_OldManStill(GamePanel gp) {
        super(gp);
        type = type_npc;
        name = npcName;
        direction = "down"; // default facing direction
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
        downStill = setup("/images/npc/old-man-not-moving1.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/old-man-not-moving2.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/old-man-not-moving3.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/old-man-not-moving1.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/old-man-not-moving2.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/old-man-not-moving3.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/npc/old-man-not-moving1.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/old-man-not-moving2.png", gp.tileSize, gp.tileSize);
        left2 =setup("/images/npc/old-man-not-moving3.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/old-man-not-moving1.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/old-man-not-moving2.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/old-man-not-moving3.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {
        dialogues[0][0] = "Hail, ye scrawny hero o' the realm!\nThe monsters done snatched our wee bairns\nand made off with the villages gold... and the good china.";
        dialogues[0][1] = "Back in me day, I'da walloped those monsters meself.\nBut these knees? They're older than the king's third wife.";
        dialogues[0][2] = "Fetch back our sprogs, and the loot's yours—fair and square.\nJust don't spend it all on elf ale and regret.";
        dialogues[0][3] = "Mind the nap spots, lad. They patch ye up fine,\nbut give those monsters time to brew more mischief.";
        dialogues[0][4] = "Off with ye now! And if ye trip on a root,\nblame the squirrels—they're in on it.";

        dialogues[1][0] = "Ahoy there, grass-stomper! That meadow's no picnic.\nFull o' weeds taller than yer ego and twice as prickly.";
        dialogues[1][1] = "Poke around for shiny bits—loots aplenty, if ye've the nose for it.\nAnd keep an eye peeled for that iron key; it's the dungeon's grumpy doorman.";
        dialogues[1][2] = "Watch fer the slime. Sneaky buggers, always nippin' at yer heels\nlike they own the deed to the whole field.";

        dialogues[2][0] = "Into the dungeon, eh? Bold as brass and twice as foolish.\nTorches flicker like they're scared o' the dark themselves.";
        dialogues[2][1] = "Scour them cobwebby corners for treasures untold.\nAnd that golden key? It's hidin' like a thief in a confessional.";
        dialogues[2][2] = "Mind the bats, ghost, the works. the Loot's callin' yer name, but so's the ghosts. They whisper sweet nothins'\nlike 'Turn back, ye daft clod.'";
        dialogues[2][4] = "Snagged the gold key? Polished and pretty, unlocks the boss's room.\nJust don't let it jingle—alerts the rats.";

        dialogues[3][0] = "Boss lair ahead, ye madcap! The goblin chief's lurkin',\npolishin' his pointy hat and dreamin' o' world domination... via turnips.";
        dialogues[3][1] = "Charge in, swing wild—save them kids afore they learn goblin limericks.\n'Twas a wee one from Nantucket...'";
        dialogues[3][2] = "That big brute's got more warts than wit,\nbut hit him hard; he'll crumple like wet parchment.";

        dialogues[4][0] = "By the gods' bearded chins, ye've done it!\nOur little terrors are back, safe and sassin' already.";
        dialogues[4][1] = "The treasure? Keep it all, ye noble numbskull.\nBuy yerself a proper helmet— that bucket's a joke.";
        dialogues[4][2] = "We'd throw a feast, but the larder’s bare\nthanks to yer monster pals' midnight snack run.";
        dialogues[4][3] = "Gratitude eternal, hero. Just... next time, knock first?\nThe door was unlocked, ye know.";
        dialogues[4][4] = "Off to yer next folly. And remember: heroes don't retire—they rust.";
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
