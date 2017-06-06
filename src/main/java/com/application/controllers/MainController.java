package com.application.controllers;

import com.StartApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Created by surik on 2/18/17
 */
public class MainController {

    //todo add main page logo

    public void gotToAdminPage(ActionEvent event) throws IOException {
        StartApp.showLoginPage();
    }

    public void goToTestPage(ActionEvent event) throws IOException {
        StartApp.showRegistrationPage();
    }

    public void exitFromApp(ActionEvent event) {
        Platform.exit();
    }
}
