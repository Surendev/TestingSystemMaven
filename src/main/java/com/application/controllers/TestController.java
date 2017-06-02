package com.application.controllers;

import com.StartApp;
import com.dto.Question;
import com.dto.QuestionInApp;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.TestDAO;
import com.jdbc.services.TestService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by surik on 2/18/17
 */
public class TestController extends AbstractController implements Initializable{

    private Integer timeOfExam = Integer.valueOf(adminConfigs.getProperty("test.timer"));
    private @FXML Label timerLabel;

    private @FXML Label questionNumberLabel;
    private @FXML Button forwardButton;
    private @FXML Button backButton;
    private @FXML AnchorPane questionTitleContainer;
    private @FXML TextFlow questionTitle;//question
    private @FXML GridPane answersGrid; // answers grid
    public TextField insertedQuestionId;

    private TestDAO testService = new TestService();
    private Test test;

    private Service<Void> timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test = testService.generateTest(context.getBean("questionsService", QuestionsDAO.class));
        timerLabel.setText(timeOfExam / 60 + ":" + timeOfExam % 60);
        timer = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        while(timeOfExam > 0) {
                            if(timeOfExam/60 < 3){
                                timerLabel.setTextFill(Color.RED);
                            }
                            timerLabel.setText(timeOfExam / 60 + ":" + timeOfExam % 60);
                            Thread.sleep(1000);
                            --timeOfExam;
                        }
                        return null;
                    }
                };
            }
        };
        timer.setOnSucceeded(event -> {
            showEndPopup();
        });
        timer.start();
    }

    public void goToNextButton() throws UnsupportedEncodingException {
        QuestionInApp curr = test.getQuestion(Integer.parseInt(questionNumberLabel.getText())+1);
        //TODO next
    }

    public void backToPreviousQuestion(ActionEvent event) {
        //TODO previous
    }

    private void showEndPopup() {

    }

    private void goToInsertedQuestion() throws UnsupportedEncodingException {
        test.getQuestion(Integer.parseInt(insertedQuestionId.getText()));
    }


    public void goToHomePage() throws IOException {

        StartApp.showMainPage();
    }

    public void goToQuestion(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            //TODO go go ))
        }
    }
}
