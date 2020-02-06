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
import javafx.stage.Stage;

public class SplashScreenController implements Initializable {

    @FXML
    Button splashButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * User can click on the play button and is prompted with the login/register view.
     * @param event the click on the play button
     * @throws IOException for when IO fails
     */
    public void splash(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
