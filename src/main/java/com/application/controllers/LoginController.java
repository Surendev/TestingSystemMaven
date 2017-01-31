package com.application.controllers;

import com.jdbc.services.LoginService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by surik on 1/29/17
 */
public class LoginController implements Initializable{

    private LoginService loginService ;
    private @FXML Label dbConnectionLabel;
    private @FXML TextField loginField;
    private @FXML TextField passwordField;
    private @FXML Button logIn;
    private @FXML Button cancel;
    private @FXML Label errLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginService = new LoginService();
        if(loginService.isConnected()){
            dbConnectionLabel.setTextFill(Color.GREEN);
            dbConnectionLabel.setText("DB is Connected");
        }else{
            dbConnectionLabel.setTextFill(Color.RED);
            dbConnectionLabel.setText("DB is NOT Connected");
        }
    }

    public void logIn(ActionEvent event) throws SQLException {
        String login = loginField.getText();
        String pass = passwordField.getText();
        if(login.equals("") || pass.equals("")){
            errLabel.setText("Login or password are invalid");
            errLabel.setTextFill(Color.RED);
            return;
        }
        if(loginService.login(login,pass)){
            errLabel.setText("Login or password are correct");
            errLabel.setTextFill(Color.GREEN);
            return;
        }
        errLabel.setText("Login or password are Incorrect");
        errLabel.setTextFill(Color.RED);
    }

    public void cancel(ActionEvent event) {
        Platform.exit();
    }

}
