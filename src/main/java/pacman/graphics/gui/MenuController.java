package pacman.graphics.gui;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import pacman.database.User;
import pacman.database.UserDao;
import pacman.graphics.GameView;
import pacman.logic.Direction;
import pacman.logic.entity.PacMan;
import pacman.logic.game.GameController;
import pacman.logic.game.GameState;

public class MenuController implements Initializable {

    private Scene scene;    //NOPMD no need for get/set for this one;
    // it is just for ease of use
    public static Stage stage;
    public static User user;
    public static GameView gameView;

    @FXML
    private Label userDetails; //NOPMD no need for having set/get for thi gui element

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private Label labelPacMan;

    @FXML
    private Button buttonPlay;

    @FXML
    private Button profileButton;

    @FXML
    private Button leaderboardButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    //known bug of pmd when using variable declaration
    @FXML
    private void loadGameScreen(ActionEvent event) {
        startGame(event);
    }

    /**
     * Starting the game window and timers.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void startGame(ActionEvent event) {

        stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
        gameView = new GameView(GameController.getInstance().getGame(), 800, 800);
        GameController.getInstance().setUser(user);
        scene = new Scene(gameView);
        stage.setScene(scene);
        stage.show();

        GameController.getInstance().start();
        scene.setOnKeyPressed(e -> {
            PacMan pm = GameController.getInstance().getGame().getLevel().getPacMan();
            Direction direction = Direction.keyToDirection(e.getCode(), pm);
            if (direction != null) {
                pm.setNextDirection(direction);
            }
        });

        gameView.getButton().setOnMouseClicked(event1 -> {
            if (!gameView.isStopped()) {
                GameController.getInstance().pause();
                gameView.getBoardCanvas().pauseGame();
            } else {
                GameController.getInstance().unpause();
                gameView.getBoardCanvas().unPauseGame();
            }
            gameView.getButton().setText(gameView.flipText());
        });

        GameController.getInstance().getGame().getState().addListener((ob, o, n) -> {
            if (n == GameState.WON || n == GameState.LOST) {
                gameView.getBoardCanvas().stopGame();
                GameController.getInstance().stop();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int score = GameController.getInstance().getGame().getPlayer().getScore().get();
                gameFinished(score);
            }
        });

    }

    private void gameFinished(int score) {
        Stage stg = new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.initStyle(StageStyle.UNDECORATED);
        stg.initOwner(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/endGameWindow.fxml"));
        try {
            Parent root = loader.load();
            EndGameWindowController controller = loader.getController();
            controller.setUp(score, stg);
            Scene scene = new Scene(root);
            stg.setScene(scene);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the leaderboard page.
     * @throws IOException if it can't find the fxml file
     */
    public void goToLeaderBoard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/leaderboard.fxml"));
        Scene scene = new Scene(root);
        stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displaying the user profile info (username) and his current score.
     *
     * @param us the user
     */
    public void setProfileDetails(User us) {
        user = us;
        int score = new UserDao().retrieveScore(us);
        userDetails.setText("User: " + user.getUsername()
                + "\n" + "High score: " + score);
    }

    /**
     * Sends the current user to his/her profile page.
     * @param event click.
     */
    public void goToProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profile.fxml"));
        Parent root = loader.load();
        ProfileController controller = loader.getController();
        controller.setUp();
        Scene scene = new Scene(root);
        stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logs the user out of the current session.
     * @param event click
     * @throws IOException user is sent to login page.
     (exception in case it is not found).
     */
    public void logOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
