package com.application.controllers;

import com.StartApp;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;

import java.io.IOException;

/**
 * Created by surik on 2/18/17
 */
public class TestController {


    public Button homeButton;
    public Label questionNumberLabel;
    public Button forwardButton;
    public Button backButton;
    public AnchorPane questionTitleContainer;
    public TextFlow questionTitleTextFlow;
    public GridPane answersGrid;

    public void goToHomePage(ActionEvent event) throws IOException {
        StartApp.showLoginPage();
    }

    public void goToNextQuestion(ActionEvent event) {
        
    }

    public void backToPreviousQuestion(ActionEvent event) {
        
    }
}
