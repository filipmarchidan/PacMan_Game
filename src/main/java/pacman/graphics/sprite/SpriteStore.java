package pacman.graphics.sprite;

import javafx.scene.image.Image;

public class SpriteStore {

    private static final Image PELLET = new Image(PelletSprite.class  //NOPMD
            .getResourceAsStream("/images/pellet.gif"));
    private static final Image POWER_PELLET = new Image(PowerPelletSprite.class //NOPMD
            .getResourceAsStream("/images/power_pellet.png"));

    public static Image getPellet() {
        return PELLET;
    }

    public static Image getPowerPellet() {
        return POWER_PELLET;
    }
}
