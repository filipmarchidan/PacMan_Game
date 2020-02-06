package pacman.graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.jetbrains.annotations.NotNull;
import pacman.logic.game.Game;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class GameView extends VBox {

    private static final int BUTTON_DIMENSION = 100;
    private StatsCanvas statsCanvas;
    private BoardCanvas boardCanvas;
    private Label button;
    private boolean stopped = false;

    /**
     * Creates a view of the game, i.e. the board and stats.
     * @param game The game to view
     * @param width The width of the view
     * @param height The height of the board
     */
    public GameView(@NotNull Game game, int width, int height) {
        this.statsCanvas = new StatsCanvas(game, width - BUTTON_DIMENSION);
        this.boardCanvas = new BoardCanvas(game.getLevel().getBoard(),
                width, height - 70);
        setUpButton();
        HBox hbox = new HBox();
        hbox.setPrefHeight(50);
        hbox.getChildren().add(button);
        hbox.getChildren().add(statsCanvas);
        getChildren().add(hbox);
        getChildren().add(boardCanvas);
    }

    public StatsCanvas getStatsCanvas() {
        return statsCanvas;
    }

    public BoardCanvas getBoardCanvas() {
        return boardCanvas;
    }

    /**
     * Flipping the text on the start/pause button.
     * @return the corresponding text to action.
     */
    public String flipText() {
        stopped = !stopped;
        if (!stopped) {
            return "PAUSE";
        } else {
            return "START";
        }
    }

    public Label getButton() {
        return button;
    }

    /**
     * Sets up the design for the start/pause button.
     */
    private void setUpButton() {
        button = new Label();
        button.setBackground(new Background(new BackgroundFill(Color.BLACK,
                CornerRadii.EMPTY, Insets.EMPTY)));
        button.setBorder(new Border(new BorderStroke(Style.CLASSIC.getWallColor(),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        button.setFont(new Font(22));
        button.setTextFill(Color.YELLOW);
        button.setAlignment(Pos.CENTER);
        button.setText("PAUSE");
        button.setPrefWidth(BUTTON_DIMENSION);
        button.setPrefHeight(49);
    }

    public boolean isStopped() {
        return stopped;
    }
}
