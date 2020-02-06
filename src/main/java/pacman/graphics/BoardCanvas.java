package pacman.graphics;

import java.util.Deque;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import org.jetbrains.annotations.NotNull;
import pacman.Main;
import pacman.graphics.sprite.PacmanSprite;
import pacman.logic.entity.Entity;
import pacman.logic.entity.Ghost;
import pacman.logic.entity.PacMan;
import pacman.logic.level.Board;

/**
 * Class for drawing the game board with everything on it.
 *
 * @author Ruben
 */// entities has access methods (though PMD does not recognize them),
// additionally class is not a bean.
@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class BoardCanvas extends Canvas {

    /**
     * The style to draw in.
     */
    private Style drawStyle = Style.CLASSIC;

    private boolean stopped;
    private AnimationTimer timer;
    /**
     * The board that should be drawn.
     */
    private Board board;

    private double scaleX;
    private double scaleY;

    /**
     * Creates a new board canvas with specified dimensions.
     */
    public BoardCanvas(Board board, int width, int height) {
        super(width, height);
        this.board = board;
        scaleX = width / (double) board.getWidth();
        scaleY = height / (double) board.getHeight();
        stopped = false;

        long start = System.nanoTime(); //NOPMD needed in order to
        //keep track of frames while timers is going on
        if (timer == null) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    double t = (now - start) / 1E9;
                    draw(t);
                }
            };
        }
        timer.start();
    }

    /**
     * Clears the canvas with the background colour.
     */
    private void clear() {
        Color bg = drawStyle.getBackgroundColor();
        getGraphicsContext2D().setFill(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 1.0));
        getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());
    }


    /**
     * Draws everything on the canvas.
     * @param t The time since started in seconds.
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "unchecked"})
    /*
     * known bug of pmd with foreach loops
     * it is always safe to draw an entity using its own sprite
     */
    public void draw(double t) {
        /*
         * Suppress warnings:
         *  - known bug of pmd with foreach loops
         *  - it is always safe to draw an entity using its own sprite
         */
        clear();
        getGraphicsContext2D().setLineWidth(1 / scaleX);

        for (Entity e : board.getEntities()) {
            getGraphicsContext2D().scale(scaleX, scaleY);
            getGraphicsContext2D().translate(e.getX(), e.getY());
            e.getSprite().drawBackground(e, getGraphicsContext2D(), drawStyle, t);
            getGraphicsContext2D().setTransform(new Affine());
        }

        for (Entity e : getOtherEntititesInOrder()) {
            getGraphicsContext2D().scale(scaleX, scaleY);
            getGraphicsContext2D().translate(e.getX(), e.getY());
            if (e instanceof PacMan && stopped) {
                end_animations((PacMan) e);
                timer.stop();
            } else {
                e.getSprite().draw(e, getGraphicsContext2D(), drawStyle, t);
            }
            getGraphicsContext2D().setTransform(new Affine());
        }
    }

    /**
     * Some death animations for PacMan.
     */
    public void end_animations(PacMan player) {
        PacmanSprite ps = (PacmanSprite)player.getSprite();
        ps.animation(getGraphicsContext2D(), drawStyle, player.isAlive());
    }

    /**
     * Sets the style that is used to draw with.
     *
     * @param drawStyle The new draw style
     */
    public void setDrawStyle(@NotNull Style drawStyle) {
        this.drawStyle = drawStyle;
    }

    /**
     * Gets the style that is used to draw with.
     * @return The current draw style
     */
    public Style getDrawStyle() {
        return drawStyle;
    }

    /**
     * After a new level is created, sets that level's board to
     * canvas' board.
     * @param board the new board created for the next level
     */
    public void setBoard(Board board) {
        clear();
        this.board = board;
        scaleX = Main.width / (double) board.getWidth();
        scaleY = Main.height / (double) board.getHeight();
    }

    /**
     * Method for stopping the timer and rendering the last scene of the game.
     */
    public void stopGame() {
        stopped = true;
    }

    public void pauseGame() {
        timer.stop();
    }

    public void unPauseGame() {
        timer.start();
    }

    public void start() {
        stopped = false;
        timer.start();
    }

    public boolean isStopped() {
        return stopped;
    }

    /**
     * Returns a deque of sorted entities in order of which they should be drawn.
     * @return sorted entities.
     */
    private Deque<Entity> getOtherEntititesInOrder() { //NOPMD just iterating over collection
        Deque<Entity> orderedMovingEntitiesAndWalls = new LinkedList<>();
        orderedMovingEntitiesAndWalls.addLast(board.pacman);
        for (Ghost g : board.getGhosts()) {
            if (g.isEaten()) {
                orderedMovingEntitiesAndWalls.addFirst(g);
            } else {
                orderedMovingEntitiesAndWalls.addLast(g);
            }
        }
        board.getWalls().forEach(orderedMovingEntitiesAndWalls::addLast);
        return orderedMovingEntitiesAndWalls;
    }
}
