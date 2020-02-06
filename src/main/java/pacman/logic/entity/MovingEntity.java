package pacman.logic.entity;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.sprite.Sprite;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

/**
 * Entity template for entities that move around the board. This includes Ghosts & PacMan.
 */
public abstract class MovingEntity extends Entity {

    /**
     * Creates an entity at the specified position with the specified sprite.
     *
     * @param board  The board the entity belongs to
     * @param square The square the entity belongs to
     * @param sprite The sprite for rendering
     */
    public MovingEntity(@NotNull Board board, Square square,
                        @NotNull Sprite<? extends Entity> sprite) {
        super(board, square, sprite);
    }

    /**
     * Updates the entity's position & direction during the game cycle.
     */
    public void update(double dtSmall) {
        //TODO: either add speed, or make this more *2 intuitive.
        double dt = 2 * dtSmall; //NOPMD incorrect DU anomaly.

        if (nextDirection != null && nextDirection != getDirection()
                && !square.getNeighbour(nextDirection).hasSolid()) {
            updateDirection();
        }

        if (direction != null) {
            updatePosition(dt * direction.getDx(), dt * direction.getDy());
        }
    }

    /**
     * Updates timers that moving entities use.
     * @param dt the time that passed.
     */
    protected abstract void updateTimers(double dt);

    /**
     * Updates the Entity's position given dx & dy.
     *
     * @param dx the amount to move horizontally.
     * @param dy the amount to move vertically.
     */
    private void updatePosition(double dx, double dy) {
        double oldX = posX;
        double oldY = posY;
        // Update position + wraparound.
        posX = board.getPosX(oldX + dx);
        posY = board.getPosY(oldY + dy);

        // If this results in collision, revert.
        if (checkCollision().stream().anyMatch(Entity::isSolid)) {
            posX = oldX;
            posY = oldY;
        } else {
            Square newSquare = board.getSquare(posX, posY);
            // Update Square.
            if (!square.equals(newSquare)) {
                moveToSquare(newSquare);
            }
        }
    }

    /**
     * Updates the Entity's direction.
     * This only if the next direction is backwards, or if at the center of a square.
     */
    private void updateDirection() {
        // Get distance to center of square
        double dx = Math.abs(posX - Math.floor(posX) - 0.5); //NOPMD false DU anomaly.
        double dy = Math.abs(posY - Math.floor(posY) - 0.5); //NOPMD false DU anomaly.

        // If nextDirection is opposite of current direction or if ~at center of square.
        if (getDirection() == nextDirection.getInverse()) {
            setDirection(nextDirection);
        } else if (dx < 0.05 && dy < 0.05) {
            // shift to center to prevent getting stuck.
            this.posX = square.getXs() + 0.5;
            this.posY = square.getYs() + 0.5;
            setDirection(nextDirection);
        }
    }

    @Override
    protected boolean isWithinBound(double dx, double dy) {
        return dx * dx + dy * dy < 0.75;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
