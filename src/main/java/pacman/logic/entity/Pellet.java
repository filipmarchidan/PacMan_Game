package pacman.logic.entity;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.sprite.PelletSprite;
import pacman.graphics.sprite.Sprite;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

/**
 * Represents a pellet PacMan can eat.
 */
public class Pellet extends Entity {

    private static final Sprite<Pellet> SPRITE = new PelletSprite();

    /**
     * Creates a pellet.
     *
     * @param board  The board the pellet is on
     * @param square The square the pellet is on
     */
    public Pellet(@NotNull Board board, Square square) {
        super(board, square, SPRITE);
    }

    @Override
    public void update(double dtSmall) {
    }

    @Override
    protected boolean isWithinBound(double dx, double dy) {
        return dx * dx + dy * dy < 0.25;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    /**
     * {@inheritDoc}
     * When PacMan collides with a Pellet,
     * it adds 10 to the score and is removed.
     */
    @Override
    public void collideWithPacMan(PacMan pacMan) {
        board.addTickScore(10);
        setAlive(false);
    }
}
