package pacman.logic.entity;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.sprite.Sprite;
import pacman.graphics.sprite.WallSprite;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

/**
 * Represents a wall.
 */
public class Wall extends Entity {

    private static final Sprite<Wall> SPRITE = new WallSprite();

    /**
     * Creates a wall.
     *
     * @param board  The board the wall is on
     * @param square The square the wall is on
     */
    public Wall(@NotNull Board board, Square square) {
        super(board, square, SPRITE);
    }

    @Override
    public void update(double dtSmall) {
    }

    @Override
    protected boolean isWithinBound(double dx, double dy) {
        return dx < 1.0 && dy < 1.0;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void collideWithPacMan(PacMan pacMan) {
        // Do Nothing.
    }
}
