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
            case OBJ_Boots.objName: obj = new OBJ_Axe(gp); break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
            case OBJ_Coin_Bronze.objName: obj = new OBJ_Coin_Bronze(gp); break;
            case OBJ_Crystal.objName: obj = new OBJ_Crystal(gp); break;
            case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
            case OBJ_FireBall.objName: obj = new OBJ_FireBall(gp); break;
            case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
            case OBJ_Iron_Shield.objName: obj = new OBJ_Iron_Shield(gp); break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
            case OBJ_Potion_Blue.objName: obj = new OBJ_Potion_Blue(gp); break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp); break;
            case OBJ_Rock_Projectile.objName: obj = new OBJ_Rock_Projectile(gp); break;
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp); break;
            case OBJ_BossKey.objName: obj = new OBJ_BossKey(gp); break;
            case OBJ_Sword_Iridium.objName: obj = new OBJ_Sword_Iridium(gp); break;
            case OBJ_MetalDoor.objName: obj = new OBJ_MetalDoor(gp); break;
        }
        return obj;
    }
}
