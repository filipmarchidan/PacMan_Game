package pacman.logic.game;

import java.util.ArrayList;

import java.util.List;

import javafx.animation.AnimationTimer;

import pacman.database.User;
import pacman.logic.Player;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;


/**
 * Controller for the game, responsible for starting, pausing, updating and stopping the game.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class GameController {

    /**
     * The game controller singleton.
     */
    private static GameController controller = null;
    private static boolean toReset = false;

    /**
     * Gets the GameController instance. There is only one instance.
     *
     * @return The game controller
     */
    public static GameController getInstance() {
        if (controller == null || toReset) {
            controller = new GameController();
            toReset = false;
        }
        return controller;
    }

    private Game game;
    private LevelFactory levelFactory;
    private AnimationTimer timer;
    private User user;

    private double time;
    private boolean started = false;
    private final double maxTime = 0.5;

    /**
     * Creates the game controller. Initializes the game, player and levels.
     */
    private GameController() {
        List<Level> levels = new ArrayList<>();
        this.levelFactory = new LevelFactory();
        levels.add(levelFactory.createLevel("level_1"));
        levels.add(levelFactory.createLevel("level_2"));
        levels.add(levelFactory.createLevel("level_3"));
        this.game = new Game(new Player(), levels);
        this.time = 0.0;
    }

    /**
     * Starts the game and game update loop.
     */
    public void start() {
        startTimer();
        game.setState(GameState.RUNNING);
    }

    /**
     * Starts the timer for updating the game.
     */
    protected void startTimer() {
        long start = System.nanoTime(); //NOPMD no reasonable rule violations
        if (timer == null) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    double t = (now - start) / 1E9;
                    update(t);
                }
            };
        }
        timer.start();
    }

    /**
     * Pauses the game.
     */
    public void pause() {
        if (!game.isRunning()) {
            throw new IllegalStateException("Can not pause a game that is not running");
        }
        game.setState(GameState.PAUSED);
        timer.stop();
    }

    /**
     * Unpauses the game.
     */
    public void unpause() {
        if (game.getState().getValue() != GameState.PAUSED) {
            throw new IllegalStateException("Can not unpause a game that is not paused");
        }
        game.setState(GameState.RUNNING);
        timer.start();
    }

    /**
     * Stops the timer of this game.
     */
    public void stop() {
        if (game.getState().getValue() == GameState.RUNNING) {
            throw new IllegalStateException("Can not stop a game that is still running");
        }
        timer.stop();
    }

    /**
     * Updates the game.
     *
     * @param newTime The current time since start in seconds.
     */
    protected void update(double newTime) {
        double dt = Math.min(newTime - time, maxTime); // In exceptional cases use maxTime.
        if (dt < 0) {
            dt = maxTime; // overflow, if possible.
        }
        time = newTime;
        game.update(dt);
    }

    /**
     * creates a new level for the user who just won the current one.
     */
    public void nextLevel() {
        game.advanceLevel();
    }

    /**
     * Gets the game instance.
     *
     * @return The game
     */
    public Game getGame() {
        return game;
    }

    public void setUser(User user) {
        this.user = user;
        game.setPlayer(user);
    }

    public void setTimer(AnimationTimer at) {
        timer = at;
    }

    public void setGame(Game g) {
        game = g;
    }

    public User getUser() {
        return user;
    }

    public void reset() {
        toReset = true;
    }

}


