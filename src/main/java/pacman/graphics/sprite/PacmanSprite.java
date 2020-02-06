package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.Direction;
import pacman.logic.entity.PacMan;

/**
 * Sprite for PacMan.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Not a bean.
public class PacmanSprite extends Sprite<PacMan> {

    private static final double MAX_ANGLE = 40.0;
    private static final double BLINK_TIME = 0.2;
    private static double time;

    @Override
    public void draw(@NotNull PacMan entity, @NotNull GraphicsContext g, @NotNull Style style,
                     double t) {
        if (entity.getDirection() != null) {
            g.rotate(-Math.toDegrees(entity.getDirection().getRotation()));
        }
        g.setFill(style.getPacmanColor());
        // Triangle wave, angle goes between 0 and 40.
        double angle = MAX_ANGLE * Math.abs(2 * t % 2 - 1); // NOPMD variable necessary
        if (!entity.isImmune()) {
            if (entity.isPumped()) {
                g.setFill(style.getPumpedColor());
            }
            time = 0.0;
            g.fillArc(-0.4, -0.4, 0.8, 0.8, angle, 360 - 2 * angle, ArcType.ROUND);
            drawEyes(entity, g);
        } else if (time > BLINK_TIME) {
            time = 0.0;
            g.fillArc(-0.4, -0.4, 0.8, 0.8, MAX_ANGLE / 2, 360 - MAX_ANGLE, ArcType.ROUND);
        } else {
            time = time + t;
        }
    }

    /**
     * draws pacman's eyes (one actually).
     * @param entity pacman
     * @param g graphics context of the canvas
     */
    public void drawEyes(@NotNull PacMan entity, @NotNull GraphicsContext g) {
        g.setFill(Color.BLACK);
        if (entity.getDirection() == Direction.LEFT) {
            g.fillOval(0.1, 0.15, 0.15, 0.15);
        } else {
            g.fillOval(0.1, -0.30, 0.15, 0.15);
        }
    }

    /**
     * Draws a pacman sad/happy face.
     *
     * @param gc    graphics of the board canvas
     * @param style style
     * @param alive happy/sad
     */
    public void animation(@NotNull GraphicsContext gc, @NotNull Style style, boolean alive) {
        gc.setFill(style.getPacmanColor());
        gc.fillOval(-0.4, -0.4, 0.8, 0.8);
        gc.setFill(Color.BLACK);
        gc.fillOval(-0.25, -0.25, 0.2, 0.2);
        gc.fillOval(0.08, -0.25, 0.2, 0.2);
        if (alive) {
            gc.fillArc(-0.15, -0.1, 0.3, 0.3, 180, 180, ArcType.ROUND);
        } else {
            gc.fillArc(-0.15, 0.05, 0.3, 0.3, 0, 180, ArcType.ROUND);
        }
    }
}
