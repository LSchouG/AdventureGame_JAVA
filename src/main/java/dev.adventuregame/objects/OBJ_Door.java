package dev.adventuregame.objects;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {

    public OBJ_Door() {

        name = "Door";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
