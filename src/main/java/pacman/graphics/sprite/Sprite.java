package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.entity.Entity;

/**
 * Represents a drawable sprite.
 */
public abstract class Sprite<E extends Entity> {

    public void drawBackground(@NotNull E entity, @NotNull GraphicsContext g,
                               @NotNull Style style, double t) {
    }

    /**
     * Draws the sprite.
     *
     * @param entity The entity the sprite belongs to
     * @param g The graphics context to draw with
     * @param style The style to draw in
     * @param t The time in seconds
     */
    public abstract void draw(@NotNull E entity, @NotNull GraphicsContext g,
                              @NotNull Style style, double t);

}
