package com.application.controllers;

import com.jdbc.dao.LoginDAO;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.services.LoginService;
import com.jdbc.services.StudentsService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by surik on 1/29/17
 * Controller function is to show results to some actions.
 * LoginController is provides login
 */
public class LoginController extends AbstractController implements Initializable{

    private LoginDAO loginService;

    private @FXML Label dbConnectionLabel;
    private @FXML TextField loginField;
    private @FXML TextField passwordField;
    private @FXML Button logIn;
    private @FXML Button cancel;
    private @FXML Label errLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginService =  context.getBean("loginDAO",LoginService.class);
    }

    public void logIn() throws IOException {
        String login = loginField.getText();
        String pass = passwordField.getText();
        if(login.equals("") || pass.equals("")){
            errLabel.setText("Invalid login or password");
            errLabel.setTextFill(Color.RED);
            return;
        }
        if(loginService.login(login,pass)){
            logIn.getScene().getWindow().hide();
            showAdminPage();
            return;
        }
        errLabel.setText("Login and password are Incorrect");
        errLabel.setTextFill(Color.RED);
    }

    public void cancel() {
        Platform.exit();
    }


    public void checkKeyPressing(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            logIn();
        }
    }

    private void showAdminPage() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
        Scene scene = new Scene(pane, 600,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Admin Page");
        stage.show();
    }
}
