package com.application.controllers;

import com.StartApp;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by surik on 2/18/17
 */
public class MainController {
    public Button goToAdminPage;
    public Button goToTest;


    //todo add main page logo

    public void gotToAdminPage() throws IOException {
        StartApp.showLoginPage();
    }

    public void goToTestPage() throws IOException {
        StartApp.showRegistrationPage();
    }

    public void exitFromApp() {
        Platform.exit();
    }

    public void initialMouse() {
        goToAdminPage.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> goToAdminPage.setTextFill(Color.RED));
        goToAdminPage.addEventFilter(MouseEvent.MOUSE_EXITED, event -> goToAdminPage.setTextFill(Color.BLACK));
        goToTest.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> goToTest.setTextFill(Color.RED));
        goToTest.addEventFilter(MouseEvent.MOUSE_EXITED, event -> goToTest.setTextFill(Color.BLACK));
    }
}
