package com.application.controllers;

import com.StartApp;
import com.application.utils.SecurityUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * Controller function is to show results to some actions.
 * LoginController is provides login
 */
public class LoginController extends AbstractController implements Initializable {


    private @FXML
    TextField loginField;
    private @FXML
    TextField passwordField;
    private @FXML
    Button logIn;
    private @FXML
    Button cancel;
    private @FXML
    Label errLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginField.setText("");
        passwordField.setText("");
    }

    public void logIn() throws IOException, NoSuchAlgorithmException {
        String login = loginField.getText();
        String pass = passwordField.getText();
        if (login.equals("") || pass.equals("")) {
            errLabel.setText("Սխալ տվյալներ");
            errLabel.setTextFill(Color.RED);
            return;
        }
        if (login.equals("admin") && SecurityUtil.encrypt(pass).equals(SecurityUtil.justString)) {
            logIn.getScene().getWindow().hide();
            showAdminPage();
        }
        errLabel.setText("Սխալ տվյալներ");
        errLabel.setTextFill(Color.RED);
    }

    public void cancel() throws IOException {
        StartApp.showMainPage();
    }

    public void checkKeyPressing(KeyEvent keyEvent) throws IOException, NoSuchAlgorithmException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            logIn();
        }
    }

    private void showAdminPage() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
        Scene scene = new Scene(pane, 800, 590);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Admin Page");
        stage.setResizable(false);
        stage.getIcons().add(new Image("/icons/TestIcon.png"));
        stage.show();
    }

    public void initialMouse() {
        logIn.addEventFilter(MouseEvent.MOUSE_ENTERED, event ->
                logIn.setStyle("-fx-border-color: floralwhite;-fx-background-color: brown; -fx-border-width: 0 0 4px 0"));
        cancel.addEventFilter(MouseEvent.MOUSE_ENTERED, event ->
                cancel.setStyle("-fx-border-color: floralwhite;-fx-background-color: brown; -fx-border-width: 0 0 4px 0"));

        logIn.addEventFilter(MouseEvent.MOUSE_EXITED, event -> logIn.setStyle("-fx-background-color: brown"));
        cancel.addEventFilter(MouseEvent.MOUSE_EXITED, event -> cancel.setStyle("-fx-background-color: brown"));
    }
}
