
package adventuregame.entity;
import adventuregame.GamePanel;
import java.util.Random;

public class NPC_OldMan extends Entity {

    public static final  String  npcName = "Old Man";

    public NPC_OldMan(GamePanel gp) {
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
        downStill = setup("/images/npc/oldMan-down-still.png", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/oldMan-down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/oldMan-down2.png", gp.tileSize, gp.tileSize);
        upStill = setup("/images/npc/oldMan-up-still.png", gp.tileSize, gp.tileSize);
        up1 = setup("/images/npc/oldMan-up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/oldMan-up2.png", gp.tileSize, gp.tileSize);
        leftStill = setup("/images/npc/oldMan-left-still.png", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/oldMan-left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/oldMan-left2.png", gp.tileSize, gp.tileSize);
        rightStill = setup("/images/npc/oldMan-right-still.png", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/oldMan-right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/oldMan-right2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {

        dialogues[0][0] = "Hail, stout-hearted wanderer! I've seen better days,\nbut ne'er a darker one than this: those wretched monsters have filched our grandkiddies\nand our glittering hoard to boot.";
        dialogues[0][1] = "Aye, in my youth, I'd have chased 'em down with naught but a walking stick\nand a grudge. Now? These bones creak louder than a tavern door at last call.";
        dialogues[0][2] = "Rescue the little scamps from yon goblin lair, and the treasure's yours—every cursed coin.\nJust mind ye don't trip over yer own heroism on the way back.";
        dialogues[0][3] = "Catnaps in the haystack'll mend yer wounds, but beware: while ye snore,\nthose green fiends sharpen their pointy sticks and plot more nonsense.";
        dialogues[0][4] = "Begone now, lad! And if the wind whispers doubts, tell it to stuff 'em—I've got faith in ye.";

        dialogues[1][0] = "By the saints' stubbed toes, ye've wrought a miracle! Our wee ones are scamperin' about,\nterrorizin' the chickens anew—bless their sticky hearts.";
        dialogues[1][1] = "Gratitude? Bah, words are cheap as monsters ale. Take the lot: gold, gems, that dodgy chalice\nwhat turns milk to vinegar. Spend it wisely... or on folly, as I did.";
        dialogues[1][2] = "I'd bake ye a pie in thanks, but my oven's as reliable as a politician's promise.\nInstead, here's a pat on the back—ow, me shoulder's out again.";
        dialogues[1][3] = "Ye've earned yerself a tale for the ages, hero. Just spare me the details o' the monsters guts;\nI've a weak constitution and weaker bladder.";
        dialogues[1][4] = "Fare thee well, savior o' sprogs. Next quest? Leave it to the young 'uns—\nI'll stick to yarn-spinnin' and complainin' 'bout the weather.";

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
