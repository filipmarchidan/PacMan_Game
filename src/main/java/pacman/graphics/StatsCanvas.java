package pacman.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import pacman.logic.game.Game;
import pacman.logic.game.GameState;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class StatsCanvas extends Canvas {

    private GameState state;
    private int score;
    private int lives;

    /**
     * The style to draw in.
     */
    private Style drawStyle = Style.CLASSIC;

    /**
     * Creates a canvas for drawing the stats.
     * @param game The game to draw stats for
     * @param width The width of the canvas
     */
    public StatsCanvas(Game game, int width) {
        super(width, 50);

        this.state = game.getState().getValue();
        this.score = game.getPlayer().getScore().get();
        this.lives = game.getPlayer().getLives().get();

        game.getState().addListener((observable, oldVal, newVal) -> setState(newVal));
        game.getPlayer().getScore().addListener((observable, oldVal, newVal) ->
                setScore(newVal.intValue()));
        game.getPlayer().getLives().addListener((observable, oldVal, newVal) ->
                setLives(newVal.intValue()));

        draw();
    }

    private void draw() {
        getGraphicsContext2D().setFill(drawStyle.getBackgroundColor());
        getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());

        getGraphicsContext2D().setFont(Font.font(27));
        getGraphicsContext2D().setFill(drawStyle.getTextColor());
        String text;
        switch (state) {
            case WON:
                text = "You won!";
                break;
            case LOST:
                text = "You lost :(";
                break;
            default:
                text = "Score: " + score;
                break;
        }
        getGraphicsContext2D().fillText(text, 30, getHeight() / 2 + 8);

        getGraphicsContext2D().setFill(drawStyle.getPacmanColor());
        double lifeSize = getHeight() / 2;
        double lifeY = getHeight() / 2 - lifeSize / 2; // NOPMD variable used
        for (int x = 1; x <= lives; x++) {
            getGraphicsContext2D().fillArc(getWidth() - x * lifeSize * 1.5 - 20, lifeY, lifeSize,
                    lifeSize, 40, 280, ArcType.ROUND);
        }
    }

    private void setState(GameState state) {
        this.state = state;
        draw();
    }

    private void setLives(int lives) {
        this.lives = lives;
        draw();
    }

    private void setScore(int score) {
        this.score = score;
        draw();
    }

}
