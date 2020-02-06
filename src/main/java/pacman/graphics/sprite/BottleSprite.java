package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.entity.Bottle;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Not a bean.
public class BottleSprite extends Sprite<Bottle> {

    private final Image bottleImage;

    public BottleSprite() {
        bottleImage = new Image(getClass().getResourceAsStream("/images/bottle.png"));
    }

    @Override
    public void draw(@NotNull Bottle entity, @NotNull GraphicsContext g, @NotNull Style style,
                     double t) {
    }

    @Override
    public void drawBackground(@NotNull Bottle entity, @NotNull GraphicsContext g,
                               @NotNull Style style, double t) {
        g.setFill(style.getDrinkColor());
        g.drawImage(bottleImage, -0.5, -0.5, 1, 1);
    }
}
