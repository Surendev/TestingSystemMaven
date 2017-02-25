package com.application.controllers;

import com.StartApp;
import com.dto.Test;
import com.jdbc.dao.TestDAO;
import com.jdbc.services.TestService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by surik on 2/18/17
 */
public class TestController extends AbstractController implements Initializable{


    private @FXML Button homeButton;
    private @FXML Label questionNumberLabel;
    private @FXML Button forwardButton;
    private @FXML Button backButton;
    private @FXML AnchorPane questionTitleContainer;
    private @FXML TextFlow questionTitleTextFlow;
    private @FXML GridPane answersGrid;

    private TestDAO testService = new TestService();
    private Test test;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test = testService.generateTest();
    }

    public void goToNextButton(ActionEvent event) {

    }

    public void backToPreviousQuestion(ActionEvent event) {

    }

    public void goToHomePage() throws IOException {
        StartApp.showMainPage();
    }
}
