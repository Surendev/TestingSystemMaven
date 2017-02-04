package com.application.controllers;

import com.jdbc.services.LoginService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by surik on 1/29/17
 * Controller function is to show results to some actions.
 * LoginController is provides login
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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring_context.xml");
        loginService =  context.getBean("loginDao",LoginService.class);
    }

    public void logIn() {
        String login = loginField.getText();
        String pass = passwordField.getText();
        if(login.equals("") || pass.equals("")){
            errLabel.setText("Invalid login or password");
            errLabel.setTextFill(Color.RED);
            return;
        }
        if(loginService.login(login,pass)){
            errLabel.setText("Login or password are correct");
            errLabel.setTextFill(Color.GREEN);
            return;
        }
        errLabel.setText("Login and password are Incorrect");
        errLabel.setTextFill(Color.RED);
    }

    public void cancel() {
        Platform.exit();
    }


    public void checkKeyPressing(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            logIn();
        }
    }
}
