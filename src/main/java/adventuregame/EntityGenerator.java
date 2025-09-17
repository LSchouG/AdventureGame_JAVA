package adventuregame;

import adventuregame.entity.Entity;
import adventuregame.objects.*;

public class EntityGenerator {
    GamePanel gp;
    public  EntityGenerator(GamePanel gp){
        this.gp = gp;
    }
    public Entity getObject(String itemName){
        Entity obj = null;
        switch (itemName){
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
            case OBJ_Coin_Bronze.objName: obj = new OBJ_Coin_Bronze(gp); break;
            case OBJ_Coin_Gold.objName: obj = new OBJ_Coin_Gold(gp); break;
            case OBJ_Crystal.objName: obj = new OBJ_Crystal(gp); break;
            case OBJ_DoorIron.objName: obj = new OBJ_DoorIron(gp); break;
            case OBJ_DoorWood.objName: obj = new OBJ_DoorWood(gp); break;
            case OBJ_FireBall.objName: obj = new OBJ_FireBall(gp); break;
            case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
            case OBJ_KeyGold.objName: obj = new OBJ_KeyGold(gp); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp); break;
            case OBJ_Plasma_Projectile.objName: obj = new OBJ_Plasma_Projectile(gp); break;
            case OBJ_Potion_Blue.objName: obj = new OBJ_Potion_Blue(gp); break;
            case OBJ_Potion_BlueBig.objName: obj = new OBJ_Potion_BlueBig(gp); break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp); break;
            case OBJ_Potion_RedBig.objName: obj = new OBJ_Potion_RedBig(gp); break;
            case OBJ_Rock_Projectile.objName: obj = new OBJ_Rock_Projectile(gp); break;
            case OBJ_Shield_Iridium.objName: obj = new OBJ_Shield_Iridium(gp); break;
            case OBJ_Shield_Iron.objName: obj = new OBJ_Shield_Iron(gp); break;
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Sword_Iridium.objName: obj = new OBJ_Sword_Iridium(gp); break;
            case OBJ_Sword_Iron.objName: obj = new OBJ_Sword_Iron(gp); break;
            case OBJ_Sword_Wood.objName: obj = new OBJ_Sword_Wood(gp); break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
            case OBJ_Torch.objName: obj = new OBJ_Torch(gp); break;
            case OBJ_TownChest.objName: obj = new OBJ_TownChest(gp); break;


        }
        return obj;
    }
}
