package adventuregame.tile_interactive;

import adventuregame.GamePanel;

public class IT_DryTree extends InteractiveTile{

    GamePanel gp;
    public IT_DryTree(GamePanel gp) {
        super(gp);
        this.gp = gp;

        down1 = setup("/objects_interactive/dry_tree.png", gp.tileSize, gp.tileSize);
        destructible = true;
    }
}
