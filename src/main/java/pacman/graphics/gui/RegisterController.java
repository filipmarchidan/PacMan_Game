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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import pacman.database.RegisterDao;
import pacman.database.User;

public class RegisterController implements Initializable {

    @FXML
    private TextArea usernameTextBox; //NOPMD it's a gui object, no need to set and get it

    @FXML
    private PasswordField passwordField; //NOPMD it's a gui object, no need to set and get it

    @FXML
    private PasswordField confPass; //NOPMD it's a gui object, no need to set and get it

    @FXML
    private Button registerButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    @SuppressWarnings("PMD")
    private void registerUser(ActionEvent event) throws IOException {
        RegisterDao registerDao = new RegisterDao();
        if (validate()) {
            User user = new User();
            user.setUsername(usernameTextBox.getText());
            user.setPassword(passwordField.getText());
            user.setScore(0);
            if (!registerDao.checkUserAlreadyExists(user)) {
                registerDao.addUser(user);
                Parent root = FXMLLoader.load(getClass()
                        .getResource("/views/login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene()
                        .getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Username already exists");
                alert.setContentText("Please try again");
                alert.showAndWait();
            }
        } else {
            alert();
        }
    }

    /**
     * Opens an information dialog to let user know about the current state of his register process.
     */
    public void alert() {
        String message;
        if (!passwordField.getText().equals(confPass.getText())) {
            message = "Passwords don't match!";
        } else if (passwordField == null || passwordField.getText().isBlank()) {
            message = "Password cannot be empty!";
        } else {
            message = "Username cannot be empty!";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(message);
        alert.setContentText(message + "\n"
                + "Please provide the correct format for the password and username");
        alert.showAndWait();
    }

    /**
     * Checks the format of the input to be correct.
     * @return true if it is/ false if not
     */
    public boolean validate() {
        return passwordField.getText().equals(confPass.getText())
                && passwordField != null
                && !passwordField.getText().isBlank()
                && usernameTextBox != null
                && !usernameTextBox.getText().isBlank();

    }

}
