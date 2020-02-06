package pacman.logic.entity;

import java.util.Set;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.sprite.PacmanSprite;
import pacman.graphics.sprite.Sprite;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

/**
 * Represents the PacMan entity on the board.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class PacMan extends MovingEntity {

    private static final Sprite<PacMan> SPRITE = new PacmanSprite();

    private boolean immune = false;
    private double immuneTimer = 0.0;
    public static final double immuneTime = 2.0;

    private boolean pumped = false;
    private double pumpedTimer = 0.0;
    public static final double pumpedTime = 8.0;

    private boolean drunk = false;
    private double drunkTimer = 0;
    public static final double drunkTime = 10.0;

    /**
     * Creates a new PacMan.
     *
     * @param board  The board PacMan is on
     * @param square The square PacMan is on
     */
    public PacMan(@NotNull Board board, Square square) {
        super(board, square, SPRITE);
        direction = Direction.RIGHT;
    }

    @Override
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // incorrect for loop UR anomaly
    public void update(double dt) {
        super.update(dt);

        updateTimers(dt);

        // Collisions
        for (Entity e : checkCollision()) {
            e.collideWithPacMan(this);
        }
    }

    /**
     * {@inheritDoc}
     * Filters out non-solid objects if pacman is immune.
     *
     * @return {@inheritDoc}
     */
    @Override
    public Set<Entity> checkCollision() {
        Set<Entity> collided = super.checkCollision();
        if (immune) {
            return collided.stream().filter(e -> e.isSolid()).collect(Collectors.toSet());
        } else {
            return collided;
        }
    }

    /**
     * Whether PacMan is immune and cannot collide with entities.
     * (except walls of course)
     *
     * @return whether PacMan is immune
     */
    public boolean isImmune() {
        return immune;
    }

    public void setImmune() {
        immune = true;
        immuneTimer = immuneTime;
    }

    public boolean isPumped() {
        return pumped;
    }

    /**
     * After eating a powerPellet, PacMAn enters a 'pumped' state and
     * is able to return the favor and eat any of the ghosts for bonus points.
     *
     * @param active whether to se pumped to active or inactive.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void setPumped(boolean active) {
        for (Ghost g : board.getGhosts()) {
            g.setScared(active);
        }
        pumped = active;
        pumpedTimer = pumpedTime;
    }

    /**
     * Returns if pacman is drunk and thus input is reversed.
     *
     * @return if pacman is drunk
     */
    public boolean isDrunk() {
        return drunk;
    }

    public void setDrunk() {
        drunk = true;
        drunkTimer = drunkTime;
    }

    @Override
    public void collideWithPacMan(PacMan pacMan) {
        // Do Nothing. Never Happens in SinglePlayer.
    }

    @Override
    protected void updateTimers(double dt) {
        if (immune) {
            immuneTimer -= dt;
            immune = immuneTimer > 0.0;
        }

        if (pumped) {
            pumpedTimer -= dt;
            if (pumpedTimer < 0.0) { //NOPMD normal constant in if statement.
                setPumped(false);
            }
        }

        if (drunk) {
            drunkTimer -= dt;
            drunk = drunkTimer > 0.0;
        }
    }
}
