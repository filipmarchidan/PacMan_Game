package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.entity.Ghost;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Not a bean.
public class PinkySprite extends GhostSprite {

    /**
     * Creating a new PinkySprite that holds the three different skins for Pinky
     * and deals with drawing them accordingly.
     */
    public PinkySprite() {
        Image image = new Image(getClass().getResourceAsStream("/images/pinky.png"));
        Image scared = new Image(getClass().getResourceAsStream("/images/darkPinky.png"));
        Image eyes = new Image(getClass().getResourceAsStream("/images/eyes.png"));
        setImages(image, scared, eyes);
    }

    @Override
    public void drawBackground(@NotNull Ghost entity, @NotNull GraphicsContext g,
                               @NotNull Style style, double t) {
        drawHome(entity, g, style.getPinkyColor(), style.getBackgroundColor());
    }

    @Override
    public void draw(@NotNull Ghost entity, @NotNull GraphicsContext g, @NotNull Style style,
                     double t) {
        g.setFill(style.getPinkyColor());
        drawImage(entity, g);
    }
}