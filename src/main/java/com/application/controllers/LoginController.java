package com.application.controllers;

import com.StartApp;
import com.application.utils.SecurityUtil;
import com.jdbc.dao.LoginDAO;
import com.jdbc.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
public class LoginController extends AbstractController implements Initializable{

    private LoginDAO loginService;

    private @FXML TextField loginField;
    private @FXML TextField passwordField;
    private @FXML Button logIn;
    private @FXML Button cancel;
    private @FXML Label errLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginService =  context.getBean("loginService",LoginService.class);
    }

    public void logIn() throws IOException, NoSuchAlgorithmException {
        String login = loginField.getText();
        String pass = passwordField.getText();
        if(login.equals("") || pass.equals("")){
            errLabel.setText("Invalid login or password");
            errLabel.setTextFill(Color.RED);
            return;
        }
        if(login.equals("admin") && SecurityUtil.encrypt(pass).equals(SecurityUtil.justString)){
            logIn.getScene().getWindow().hide();
            showAdminPage();
        }
        errLabel.setText("Login and password are Incorrect");
        errLabel.setTextFill(Color.RED);
    }

    public void cancel() throws IOException {
        StartApp.showMainPage();
    }

    public void checkKeyPressing(KeyEvent keyEvent) throws IOException, NoSuchAlgorithmException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            logIn();
        }
    }

    private void showAdminPage() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
        Scene scene = new Scene(pane, 800,600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Admin Page");
        stage.show();
    }
}
