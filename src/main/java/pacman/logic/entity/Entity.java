package pacman.logic.entity;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pacman.graphics.sprite.Sprite;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

/**
 * Represents an entity with a position, velocity and a sprite.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public abstract class Entity {

    Board board;
    protected Square square;
    double posX = -1; // Not on board.
    double posY = -1; // Not on board.
    private Sprite<? extends Entity> sprite;

    Direction direction = null;
    Direction nextDirection = null;

    private boolean alive = true;

    /**
     * Creates an entity at the specified position with the specified sprite.
     * If square is null, it's position is undefined but may be set by adding it to a square later.
     * If the square is defined, the entity will be added to the square.
     *
     * @see Square#addEntity(Entity)
     * @param board  The board the entity belongs to
     * @param square The square the entity belongs to, if applicable
     * @param sprite The sprite for rendering
     */
    public Entity(@NotNull Board board, Square square,
                  @NotNull Sprite<? extends Entity> sprite) {
        this.board = board;
        this.sprite = sprite;
        this.square = square;
        if (square != null) {
            square.addEntity(this);
        }
    }

    /**
     * Checks whether this entity collides with another entity.
     *
     * @param other The other entity to check collision with
     * @return Whether the two entities collide
     * @see this#isWithinBound(double, double)
     */
    public boolean collide(Entity other) {
        double dx = distanceX(other.getX()); // NOPMD variable is used
        double dy = distanceY(other.getY()); // NOPMD variable is used
        return other.isWithinBound(dx, dy);
    }

    /**
     * Checks whether an entity at distance dx, dy is inside this entity's bounds.
     * In which case it should collide.
     *
     * @param dx the horizontal distance to the other entity.
     * @param dy the vertical distance to the other entity.
     * @return whether this distance means the other entity is within this entity's bounds.
     * @see this#collide(Entity)
     */
    protected abstract boolean isWithinBound(double dx, double dy);

    /**
     * Updates the entity each game cycle.
     */
    public abstract void update(double dtSmall);

    /**
     * Defines what code is run if PacMan collides with this entity.
     *
     * @param pacMan The PacMan that was collided with.
     */
    public abstract void collideWithPacMan(PacMan pacMan);

    /**
     * Checks for collisions with the entities around this entity.
     *
     * @return The set of entities this entity collides with
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // known bug of pmd with foreach loops.
    public Set<Entity> checkCollision() {
        Set<Entity> collisions = new HashSet<>();
        for (Entity entity : getSquare().getEntities()) {
            if (this != entity && collide(entity)) {
                collisions.add(entity);
            }
        }
        if (direction != null) {
            for (Entity entity : getSquare().getNeighbour(direction).getEntities()) {
                if (collide(entity)) {
                    collisions.add(entity);
                }
            }
        }
        return collisions;
    }

    public void moveToSquare(Square newSquare) {
        square.moveEntity(this, newSquare);
    }

    /**
     * Gets the x position of this entity.
     *
     * @return The x position
     */
    public double getX() {
        return posX;
    }

    /**
     * Gets the y position of this entity.
     *
     * @return The y position
     */
    public double getY() {
        return posY;
    }

    /**
     * Sets the x position of this entity.
     *
     * @param x The x position
     */
    public void setX(double x) {
        this.posX = x;
    }

    /**
     * Sets the y position of this entity.
     *
     * @param y The y position
     */
    public void setY(double y) {
        this.posY = y;
    }

    /**
     * Gets the direction this entity is moving in.
     *
     * @return The direction, null if entity has no direction
     */
    @Nullable
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction of this entity.
     *
     * @param direction The new direction or null if no direction
     */
    public void setDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    /**
     * Sets the direction this entity will go in at the next intersection.
     *
     * @param nextDirection The next direction
     */
    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    /**
     * Gets the sprite of this entity.
     *
     * @return The sprite for rendering
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Gets whether this entity is solid an cannot be passed though.
     *
     * @return Whether this entity is solid
     */
    public abstract boolean isSolid();

    /**
     * Gets the board this entity is on.
     *
     * @return The board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the absolute distance to the specified position.
     *
     * @param x The x position
     * @return The distance to the position
     */
    protected double distanceX(double x) {
        double dx = Math.abs(posX - x);
        if (2 * dx > board.getWidth()) {
            return board.getWidth() - dx;
        } else {
            return dx;
        }
    }

    /**
     * Gets the absolute distance to the specified position.
     *
     * @param y The y position
     * @return The distance to the position
     */
    protected double distanceY(double y) {
        double dy = Math.abs(posY - y);
        if (2 * dy > board.getHeight()) {
            return board.getHeight() - dy;
        }
        return dy;
    }

    /**
     * Gets whether the entity is alive.
     *
     * @return The alive status of this entity
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the alive status of this entity.
     *
     * @param alive The new alive status
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Gets the square this entity is on.
     *
     * @return The current square
     */
    @NotNull
    public Square getSquare() {
        return square;
    }

    /**
     * Sets the square of this entity.
     *
     * @param square the new square.
     */
    public void setSquare(Square square) {
        this.square = square;
    }
}
