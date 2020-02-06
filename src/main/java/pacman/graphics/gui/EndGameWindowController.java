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
import javafx.stage.Stage;

import pacman.database.LeaderboardDao;
import pacman.database.UserDao;
import pacman.logic.game.GameController;
import pacman.logic.game.GameState;


@SuppressWarnings("PMD.BeanMembersShouldSerialize") //not a bean class
public class EndGameWindowController implements Initializable {

    private Stage newStage;

    @FXML
    Label status;
    @FXML
    Label highScore;
    @FXML
    Button highScores;
    @FXML
    Button nextLevel;
    @FXML
    Button mainMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * clicks on this button and send the user to main menu.
     *
     * @param event click
     */
    public void goToMainMenu(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
        try {
            Parent roots = loader.load();
            MenuController controller = loader.getController();
            controller.setProfileDetails(GameController.getInstance().getUser());
            Scene sc = new Scene(roots);
            GameController.getInstance().reset();
            newStage.close();
            MenuController.stage.setScene(sc);
            MenuController.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends the user to leaderBoard page.
     *
     * @param event click
     */
    public void goToLeaderBoard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/leaderboard.fxml"));
            Scene scene = new Scene(root);
            MenuController.stage.setScene(scene);
            GameController.getInstance().reset();
            newStage.close();
            MenuController.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the next level in store.
     *
     * @param event click
     */
    public void goToNextLevel(ActionEvent event) {
        GameController.getInstance().nextLevel();
        MenuController.gameView.getBoardCanvas().setBoard(GameController
                .getInstance().getGame().getLevel().getBoard());
        newStage.close();
        GameController.getInstance().start();
        MenuController.gameView.getBoardCanvas().start();
    }

    /**
     * setting up the parameters for displaying the right messages.
     *
     * @param newScore score achieved by player in this run.
     */
    public void setUp(int newScore, Stage stg) {
        newStage = stg;
        GameState state = GameController.getInstance().getGame().getState().getValue();
        if (state == GameState.WON && !GameController.getInstance().getGame().won()) {
            highScores.setVisible(false);
            highScore.setDisable(true);
            status.setText("! Level Won !");
            highScore.setText("Your Score : " + newScore);
        } else {
            winLoseGame(newScore, state);
        }
    }

    /**
     * Shows the windows for losing/winning the game with option to go
     * to main menu or to leaderboard.
     *
     * @param newScore the new score achieved during this run
     * @param state    win/loss
     */
    public void winLoseGame(int newScore, GameState state) {
        LeaderboardDao dao = new LeaderboardDao(); //NOPMD pmd error prone detection rule
        int oldScore = new UserDao().retrieveScore(GameController.getInstance().getUser());
        if (oldScore < newScore) {
            dao.enterScore(GameController.getInstance().getUser(), newScore);
        }
        nextLevel.setVisible(false);
        nextLevel.setDisable(true);
        highScore.setText("Your Score :" + newScore);
        if (state == GameState.WON && GameController.getInstance().getGame().won()) {
            status.setText("!! YOU WIN !!");
        } else {
            status.setText("*_* YOU LOST !!");
        }
    }
}
