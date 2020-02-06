package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.Style;
import pacman.logic.Direction;
import pacman.logic.entity.Wall;
import pacman.logic.level.Square;

/**
 * Sprite for the walls.
 */
public class WallSprite extends Sprite<Wall> {

    @Override
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void draw(@NotNull Wall entity, @NotNull GraphicsContext g, @NotNull Style style,
                     double t) {
        g.setStroke(style.getWallColor());
        Square square = entity.getSquare();
        for (Direction dir : Direction.values()) {
            Square neighbour = square.getNeighbour(dir);
            boolean directSolidNeighbour = square.manhattenDistance(neighbour) == 1
                    && neighbour.hasSolid();
            if (!directSolidNeighbour) {
                drawSide(entity, g, style, dir);
            }
        }
    }

    /**
     * Draws the line of the wall on this side.
     *
     * @param entity the entity this sprite belongs to
     * @param g      the graphics context to use
     * @param style  the style to use
     * @param dir    the direction in which to draw the line
     */
    private void drawSide(@NotNull Wall entity, @NotNull GraphicsContext g, @NotNull Style style,
                          Direction dir) {
        double x1 = dir.getDx() == 0 ? -0.5 : dir.getDx() * 0.5;
        double x2 = dir.getDx() == 0 ? 0.5 : dir.getDx() * 0.5;
        double y1 = dir.getDy() == 0 ? -0.5 : dir.getDy() * 0.5;
        double y2 = dir.getDy() == 0 ? 0.5 : dir.getDy() * 0.5;
        g.strokeLine(x1, y1, x2, y2);
    }
}
