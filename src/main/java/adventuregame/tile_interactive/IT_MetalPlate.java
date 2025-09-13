package adventuregame.tile_interactive;

import adventuregame.GamePanel;

public class IT_MetalPlate extends InteractiveTile{

    GamePanel gp;
    public static final  String  itName = "Metal Plate";

    public  IT_MetalPlate(GamePanel gp, int col, int row){
        super(gp,col,row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        name = itName;
        down1 = setup("/images/objects_interactive/metal-plate.png", gp.tileSize, gp.tileSize);

        solidArea.x = 0;// goes 0 pixel in from the side
        solidArea.y = 0;// goes 16 pixel down from the top
        solidArea.width = 0;// the space left, 48 - 0  = 48
        solidArea.height = 0;// the space left, 48 - 16 = 32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
