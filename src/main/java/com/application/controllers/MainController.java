package com.application.controllers;

import com.StartApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Created by surik on 2/18/17
 */
public class MainController {

    @FXML Button goToAdminPage;
    @FXML Button goToTest;

    public void gotToAdminPage(ActionEvent event) throws IOException {
        StartApp.showLoginPage();
    }

    public void goToTestPage(ActionEvent event) throws IOException {
        StartApp.showTestPage();
    }
}
