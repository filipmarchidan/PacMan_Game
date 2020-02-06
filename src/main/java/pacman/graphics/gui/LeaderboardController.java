package pacman.graphics.gui;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import pacman.database.LeaderboardDao;
import pacman.database.User;
import pacman.graphics.Style;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize",
        "PMD.DataflowAnomalyAnalysis"}) // Class is not a bean.
public class LeaderboardController implements Initializable {

    private static int LEADERBOARD_AMOUNT = 5;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LeaderboardDao dao = new LeaderboardDao();
        List<User> users = dao.getTop(LEADERBOARD_AMOUNT);
        for (int i = 0; i < users.size(); i++) {
            List<Label> labels = new ArrayList<>();
            Label rank = new Label("#" + (i + 1));
            Label username = new Label(users.get(i).getUsername());
            Label score = new Label("" + users.get(i).getScore());
            labels.add(rank);
            labels.add(username);
            labels.add(score);
            int a = 0;
            for (Label l: labels) {
                gridPane.add(l, a, i + 1);
                l.setTextFill(Style.CLASSIC.getTextColor());
                l.setFont(Font.font(27));
                l.setTextAlignment(TextAlignment.RIGHT);
                l.setAlignment(Pos.CENTER_RIGHT);
                l.setTranslateX(50);
                a++;
            }
        }
    }

    /**
     * Goes back to main menu.
     * @param event click on the button
     * @throws IOException in case the fxml file can't be found.
     */
    public void goToMainMenu(ActionEvent event) throws IOException {
        LoginController.goToMainMenu(event, MenuController.user);
    }

}
