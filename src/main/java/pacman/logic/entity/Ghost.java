package pacman.logic.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pacman.graphics.sprite.Sprite;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

/**
 * Represents a ghost.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class not a Bean.
public abstract class Ghost extends MovingEntity {

    public enum Mode {
        CHASE,
        SCATTER,
        EATEN,
        SCARED;

        /**
         * Switching between CHASE and SCATTER modes.
         *
         * @return the other one of those two.
         */
        public Mode invertScatter() {
            if (this == CHASE) {
                return SCATTER;
            } else {
                return CHASE;
            }
        }
    }

    protected Mode mode = Mode.CHASE;
    protected Square oldSquare;

    protected double homeX;
    protected double homeY;
    private Square homeCorner;

    private double scatterChaseTimer = 0.0;
    private static final double chaseDuration = 18.0;
    private static final double scatterDuration = 7.0;

    /**
     * Creates a ghost.
     *
     * @param board  The board the ghost is on
     * @param square the square the ghost is on
     * @param sprite The sprite of the ghost
     */
    public Ghost(Board board, Square square, Sprite<? extends Ghost> sprite) {
        super(board, square, sprite);
        direction = Direction.RIGHT;
        oldSquare = square;
        homeCorner = square;
        this.homeX = getX();
        this.homeY = getY();
    }

    @Override
    public void update(double dt) {
        // if frightened, our ghost moves 2 times slower
        if (mode == Mode.SCARED) {
            super.update(dt / 2);
        } else if (mode == Mode.EATEN) {
            super.update(2 * dt);
        } else {
            super.update(dt);
        }

        updateTimers(dt);

        if (square != oldSquare) { // Update choice when a new square is reached.
            updateChoice();
            oldSquare = square; // must be called after getOptions, as this information is used.
        }
    }

    @Override
    protected void updateTimers(double dt) {
        scatterChaseTimer = scatterChaseTimer + dt;
        // Alternating between chase mode and scatter mode according to the timers.
        if (mode == Mode.CHASE && scatterChaseTimer > chaseDuration || mode == Mode.SCATTER
                && scatterChaseTimer > scatterDuration) {
            mode = mode.invertScatter();
            scatterChaseTimer = 0.0;
            nextDirection = direction.getInverse();
            oldSquare = square;
        }
    }

    /**
     * Updates the next direction choice of the Ghost.
     */
    private final void updateChoice() {
        List<Square> options = getOptions();
        if (options.size() > 0) {
            Square target = chooseTarget(options);
            if (target == null) {
                return;
            }
            if (options.size() == 1) { //NOPMD logical literal.
                this.nextDirection = square.directionOf(options.get(0));
            } else {
                this.nextDirection = square.directionOf(closestNeighbour(target, options));
            }
        }
    }

    /**
     * Returns a list of all options a ghost has as next squares.
     *
     * @return list of target square options.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") //NOPMD for loop, not part of code.
    protected List<Square> getOptions() {
        List<Square> neighbours = this.getSquare().getNeighbours();

        List<Square> nonSolidNeighbours = new ArrayList<>(4);
        for (Square square : neighbours) {
            if (neighbours.size() == 1 || !square.hasSolid()) {
                nonSolidNeighbours.add(square);
            }
        }

        List<Square> options = new ArrayList<>(4);
        for (Square square : nonSolidNeighbours) {
            if (nonSolidNeighbours.size() == 1
                    || !square.equals(oldSquare) && !square.equals(this.square)) {
                options.add(square);
            }
        }
        return options;
    }

    /**
     * Gives the closest option to the target.
     * Uses breadth first, defaults to manhatten if target is unreachable.
     *
     * @param target  the square to find the closest option to.
     * @param options the options we can pick from.
     * @return the closest option to the target.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // Foreach loop incorrectly marked.
    protected Square closestNeighbour(Square target, List<Square> options) {
        if (options.size() == 0) {
            throw new IllegalArgumentException("Cannot choose target from empty list of options.");
        }

        Square closest = square.breadthFirstSearch(target, options);
        if (closest != null) {
            return closest;
        } else {
            return Square.manhattenDistance(target, options);
        }
    }

    /**
     * Choose what direction the ghost should go in.
     *
     * @param nextOptions the neighbouring squares the ghost can go to.
     * @return the square the ghost wants to go towards.
     * @see this#chaseTarget(List)
     * @see this#scatterTarget(List)
     * @see this#frightenedTarget(List)
     */
    protected Square chooseTarget(List<Square> nextOptions) {
        switch (mode) {
            case CHASE:
                return chaseTarget(nextOptions);
            case SCATTER:
                return scatterTarget(nextOptions);
            case SCARED:
                return frightenedTarget(nextOptions);
            case EATEN:
                return spawnTarget(nextOptions);
            default:
                throw new IllegalStateException("No target behavior implemented for: "
                        + mode.toString());
        }
    }

    /**
     * Chooses the default chase mode target of the ghost.
     *
     * @param options list of squares the ghost can pick from.
     * @return the target
     * @see this#chooseTarget(List)
     */
    protected abstract Square chaseTarget(List<Square> options);

    /**
     * Getting the "home square' of each ghost, while in scattered mode.
     *
     * @param options list of squares the ghost can pick.
     * @return That specific home_square, the starting point of each ghost.
     */
    protected final Square scatterTarget(List<Square> options) {
        if (square == homeCorner) {
            Random rand = new Random();
            return options.get(rand.nextInt(options.size()));
        } else {
            return homeCorner;
        }
    }

    /**
     * Chooses the frightened mode target of the ghost.
     *
     * @param options the neighbouring options the ghost can access.
     * @return the target (should be randomly picked from intersection options)
     * @see this#chooseTarget(List)
     */
    protected final Square frightenedTarget(List<Square> options) {
        Random random = new Random();
        int a = random.nextInt(options.size());
        return options.get(a);
    }

    /**
     * Return the home square of each ghost, in order to
     * respawn at that location.
     *
     * @param options List of squares the ghosts move to.
     * @return that home square
     */
    private Square spawnTarget(List<Square> options) {
        if (square == homeCorner) {
            mode = Mode.CHASE;
            scatterChaseTimer = 0.0;
            return chaseTarget(options);
        } else {
            return homeCorner;
        }
    }

    public boolean isScared() {
        return mode == Mode.SCARED;
    }


    public void setScared(boolean active) {
        mode = active ? Mode.SCARED : Mode.CHASE;
        scatterChaseTimer = 0.0;
    }

    /**
     * Sets ghost to eaten. Also enables single turnaround.
     */
    public void setEaten() {
        mode = Mode.EATEN;
        oldSquare = null; //NOPMD Insures the ghost can turn around.
        update(0);
        direction = nextDirection;
    }

    public boolean isEaten() {
        return mode == Mode.EATEN;
    }

    public double getHomeX() {
        return homeX;
    }

    public double getHomeY() {
        return homeY;
    }

    @Override
    public void collideWithPacMan(PacMan pacMan) {
        switch (mode) {
            case CHASE:
            case SCATTER:
                pacMan.setAlive(false);
                return;
            case SCARED:
                board.addTickScore(30);
                setEaten();
                return;
            default:
                return;
        }
    }
}
