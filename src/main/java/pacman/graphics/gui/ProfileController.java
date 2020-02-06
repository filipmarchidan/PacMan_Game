package pacman.graphics.gui;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import pacman.database.User;
import pacman.database.UserDao;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") //it is not a bean
public class ProfileController implements Initializable {

    @FXML
    private Label userNameLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private TextField newUsername;
    @FXML
    private PasswordField newPassword;

    private User newUser;


    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") //knwon bug of pmd when declaring variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Sets up the current username and his/her highscore.
     */
    public void setUp() {
        UserDao dao = new UserDao();
        int score = dao.retrieveScore(MenuController.user);
        newUser = MenuController.user;
        userNameLabel.setText(MenuController.user.getUsername());
        scoreLabel.setText("" + score);
    }

    /**
     * Changes the username of the current user.
     * @param event click on the button.
     */
    public void changeUsername(ActionEvent event) {
        if (newUsername.getText() == null || newUsername.getText().isBlank()
                || newUsername.getText().length() == 0) {
            showAlert("Username", true);
        } else {
            UserDao userDao = new UserDao();
            newUser.setId(userDao.getUserIdFromDatabase(newUser));
            newUser.setUsername(newUsername.getText());
            userDao.updateUserUsername(newUser);
            userNameLabel.setText(newUser.getUsername());
            showAlert("Username", false);
        }
    }

    /**
     * Changes the password of the current user.
     * @param event click on the button.
     */
    public void changePassword(ActionEvent event) {
        if (newPassword.getText() == null || newPassword.getText().isBlank()
                || newPassword.getText().length() == 0) {
            showAlert("Password", true);
        } else {
            newUser.setPassword(newPassword.getText());
            UserDao userDao = new UserDao();
            userDao.updateUserPassword(newUser);
            showAlert("Password", false);
        }
    }

    /**
     * deletes the current user's account for good.
     * @param event click.
     * @throws IOException after deletion, he is sent to login page
    (exception in case it is not found).
     */
    public void deleteAccount(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to delete your account");
        alert.setContentText(newUser.getUsername()
                + ", are you sure you want to delete your account ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            UserDao userDao = new UserDao();
            userDao.deleteUser(newUser);
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void backToMenu(ActionEvent event) {
        MenuController.user = newUser;
        LoginController.goToMainMenu(event, MenuController.user);
    }

    /**
     * Alerts the user regarding his/her intention of updating user details.
     * Tells him if it was successful or not.
     */
    public void showAlert(String x, boolean errorAlert) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        if (errorAlert) {
            alert.setHeaderText(x + " field cannot be empty");
            alert.setContentText("Please try again and enter the correct format!");
        } else {
            alert.setHeaderText("Success!");
            alert.setContentText(x + " successfully updated!");
        }
        alert.showAndWait();

        newUsername.setText(null);
        newPassword.setText(null);
    }
}
