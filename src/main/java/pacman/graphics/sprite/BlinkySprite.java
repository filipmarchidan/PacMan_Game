package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.entity.Ghost;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Not a bean.
public class BlinkySprite extends GhostSprite {

    /**
     * Creates the three skins for Blinky and chooses from them according to
     * its current state.
     */
    public BlinkySprite() {
        Image image = new Image(getClass().getResourceAsStream("/images/blinky.png"));
        Image scared = new Image(getClass().getResourceAsStream("/images/darkBlinky.png"));
        Image eyes = new Image(getClass().getResourceAsStream("/images/eyes.png"));
        setImages(image, scared, eyes);
    }

    @Override
    public void drawBackground(@NotNull Ghost entity, @NotNull GraphicsContext g,
                               @NotNull Style style, double t) {
        drawHome(entity, g, style.getBlinkyColor(), style.getBackgroundColor());
    }

    @Override
    public void draw(@NotNull Ghost entity, @NotNull GraphicsContext g, @NotNull Style style,
                     double t) {
        g.setFill(style.getBlinkyColor());
        drawImage(entity, g);
    }
}
