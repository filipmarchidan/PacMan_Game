package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.entity.Pellet;

/**
 * Sprite for a pellet.
 */
public class PelletSprite extends Sprite<Pellet> {

    @Override
    public void draw(@NotNull Pellet entity, @NotNull GraphicsContext g, @NotNull Style style,
                     double t) {
    }

    @Override
    public void drawBackground(@NotNull Pellet entity, @NotNull GraphicsContext g,
                               @NotNull Style style, double t) {
        g.setFill(style.getPelletColor());
        g.drawImage(SpriteStore.getPellet(),-0.1, -0.1, 0.2, 0.2);
    }
}
