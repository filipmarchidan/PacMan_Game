package pacman.logic;

import java.util.HashMap;

import javafx.scene.input.KeyCode;
import pacman.logic.entity.PacMan;

/**
 * .
 * Class for keeping track of directions of different sprites,
 * the orientation of their animation and their future moves.
 */
public enum Direction {

    /**
     * .
     * If the difference between the previous coordinate and the new one is
     * negative on Y-axis and constant on X-axis, then the sprite has
     * moved up
     */
    UP(0, -1, Math.PI / 2),

    /**
     * .
     * If the difference between the previous coordinate and the new one is
     * negative on X-axis and constant on Y-axis, then the sprite has
     * moved left
     */
    LEFT(-1, 0, Math.PI),

    /**
     * .
     * If the difference between the previous coordinate and the new one is
     * positive on Y-axis and constant on X-axis, then the sprite has
     * moved down
     */
    DOWN(0, 1, 3 * Math.PI / 2),

    /**
     * .
     * If the difference between the previous coordinate and the new one is
     * positive on X-axis and constant on Y-axis, then the sprite has
     * moved down
     */
    RIGHT(1, 0, 0.0);

    private final int dx;

    private final int dy;

    private final double rotation;

    public static HashMap<KeyCode, Direction> directionHashMap;

    static {
        directionHashMap = new HashMap<>();
        directionHashMap.put(KeyCode.A, Direction.LEFT);
        directionHashMap.put(KeyCode.LEFT, Direction.LEFT);
        directionHashMap.put(KeyCode.W, Direction.UP);
        directionHashMap.put(KeyCode.UP, Direction.UP);
        directionHashMap.put(KeyCode.S, Direction.DOWN);
        directionHashMap.put(KeyCode.DOWN, Direction.DOWN);
        directionHashMap.put(KeyCode.D, Direction.RIGHT);
        directionHashMap.put(KeyCode.RIGHT, Direction.RIGHT);
    }

    Direction(int x, int y, double rotation) {
        this.dx = x;
        this.dy = y;
        this.rotation = rotation;
    }

    public Direction getInverse() {
        return Direction.values()[(ordinal() + 2) % values().length];
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public double getRotation() {
        return rotation;
    }

    /**
     * Return the Direction given its x & y equivalent.
     *
     * @param x - x equivalent
     * @param y - y equivalent
     * @return the direction represented by (x,y).
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // known bug of pmd with foreach loops.
    public static Direction getDirection(int x, int y) {
        for (Direction dir : values()) {
            if (dir.dx == x && dir.dy == y) {
                return dir;
            }
        }
        throw new IllegalArgumentException("Direction (" + x + ":" + y + ") does not exist.");
    }

    /**
     * Returns the direction a key should be bound to.
     * Takes into account PacMan's drunk state.
     *
     * @param key the key of which we want the direction.
     * @return the direction that corresponds to this key.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static Direction keyToDirection(KeyCode key, PacMan pacMan) {

        Direction direction;
        direction = directionHashMap.get(key);
        if (pacMan.isDrunk()) {
            direction = directionHashMap.get(key).getInverse();
        }
        return direction;
    }
}
