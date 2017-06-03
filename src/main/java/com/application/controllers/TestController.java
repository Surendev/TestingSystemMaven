package com.application.controllers;

import com.StartApp;
import com.dto.Question;
import com.dto.QuestionInApp;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.TestDAO;
import com.jdbc.services.TestService;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private @FXML AnchorPane questionTitleContainer;

    private @FXML TextArea questionTitle;

    private @FXML RadioButton answer1CheckBox;
    private @FXML RadioButton answer2CheckBox;
    private @FXML RadioButton answer3CheckBox;
    private ToggleGroup answersGroup = new ToggleGroup();

    private @FXML TextArea answer1;
    private @FXML TextArea answer2;
    private @FXML TextArea answer3;

    private @FXML TextField insertedQuestionId;
    private @FXML Button forwardButton;
    private @FXML Button backButton;

    private TestDAO testService = new TestService();

    private Integer questionId = 1;
    private Test test;

    private Thread timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test = testService.generateTest(context.getBean("questionsService", QuestionsDAO.class));
        initializeTimer();
        displayCurrentQuestion();
        initRadioButtons();
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

    public void goToHomePage() throws IOException {

        StartApp.showMainPage();
    }


    public void goToInsertedQuestion() throws UnsupportedEncodingException {
        test.getQuestion(Integer.parseInt(insertedQuestionId.getText()));
    }

    public void goToQuestion(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            //TODO go go ))
        }
    }


    private final void updateTimer() {

        if(timeOfExam/60 < 3){
            timerLabel.setTextFill(Color.RED);
        }
        timerLabel.setText(timeOfExam / 60 + ":" + timeOfExam % 60);
        --timeOfExam;
        if (timeOfExam<0){
            timer.interrupt();
            showEndPopup();
        }
    }



    //region Initialization
    private void initializeTimer(){
        timerLabel.setText(timeOfExam / 60 + ":" + timeOfExam % 60);
        timer = new Thread(() ->
            Platform.runLater(() -> {
                try {
                    updateTimer();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
        );
        timer.start();
    }

    private void displayCurrentQuestion() {
        QuestionInApp current = null;
        try {
            current = test.getQuestion(questionId);
            questionTitle.setText(current.getQuestion());
            answer1.setText(current.getAnswer1());
            answer1CheckBox.setUserData(current.getAnswer1());
            answer2.setText(current.getAnswer2());
            answer2CheckBox.setUserData(current.getAnswer2());
            answer3.setText(current.getAnswer3());
            answer3CheckBox.setUserData(current.getAnswer3());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void initRadioButtons() {
        answer1CheckBox.setToggleGroup(answersGroup);
        answer2CheckBox.setToggleGroup(answersGroup);
        answer3CheckBox.setToggleGroup(answersGroup);
    }
    //endregion

}
