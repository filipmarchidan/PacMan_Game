package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.entity.PowerPellet;

public class PowerPelletSprite extends Sprite<PowerPellet> {


    @Override
    public void draw(@NotNull PowerPellet entity, @NotNull GraphicsContext g,
                     @NotNull Style style, double t) {
    }

    @Override
    public void drawBackground(@NotNull PowerPellet entity, @NotNull GraphicsContext g,
                               @NotNull Style style, double t) {
        double deltaTime = Math.abs(t % 2 - 1) * 2 / 3 + 0.3;
        g.setFill(style.getPowerPelletColor());
        g.drawImage(SpriteStore.getPowerPellet(), -0.3 * deltaTime, -0.3 * deltaTime,
                0.6 * deltaTime, 0.6 * deltaTime);
    }
}
