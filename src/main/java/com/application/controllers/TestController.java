package com.application.controllers;

import com.StartApp;
import com.dto.QuestionInApp;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.TestDAO;
import com.jdbc.services.TestService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by surik on 2/18/17
 */
public class TestController extends AbstractController implements Initializable {

    private Integer timeOfExam = Integer.valueOf(adminConfigs.getProperty("test.timer"));
    Timeline timeline;
    private @FXML
    Label timerLabel;

    private @FXML
    Label questionNumberLabel;
    private @FXML
    Label questionsSizeLabel;

    private @FXML
    Label questionTitle;

    private @FXML
    RadioButton answer1CheckBox;
    private @FXML
    RadioButton answer2CheckBox;
    private @FXML
    RadioButton answer3CheckBox;
    private ToggleGroup answersGroup = new ToggleGroup();

    private @FXML
    Label answer1;
    private @FXML
    Label answer2;
    private @FXML
    Label answer3;

    private @FXML
    TextField insertedQuestionId;

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

    //TODO ցիկլիկ հարցերի փոփոխություն
    public void goToNextButton() throws UnsupportedEncodingException {
        questionId = Integer.parseInt(questionNumberLabel.getId()) + 1;
        if (questionId > test.getQuestionsSize()) questionId = 1;
        displayCurrentQuestion();
    }

    public void backToPreviousQuestion() {
        questionId = Integer.parseInt(questionNumberLabel.getId()) - 1;
        if (questionId < 1) questionId = test.getQuestionsSize();
        displayCurrentQuestion();
    }

    private void showEndPopup() {

    }

    public void goToHomePage() throws IOException {

        StartApp.showMainPage();
    }

    @FXML
    private void qualifyTest() {
        timeline.stop();
        System.exit(0);
    }


    public void goToQuestion(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            questionId = Integer.parseInt(insertedQuestionId.getText());
            displayCurrentQuestion();
        }
    }


    //region Initialization
    private void initializeTimer() {
        Integer[] time = {0, 10};
        timerLabel.setTextFill(Color.DARKGREEN);
        timerLabel.setText(time[0] + " ր․ " + ((time[1] < 10) ? "0" : "") + time[1] + " վրկ․");
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        ae -> {
                            if (time[0] <= 19) timerLabel.setTextFill(Color.ORANGE);
                            if (time[0] <= 18) timerLabel.setTextFill(Color.DARKRED);

                            if (time[1] <= 0) {
                                time[0]--;
                                time[1] = 60;
                            }
                            time[1]--;
                            timerLabel.setText(time[0] + " ր․ " + ((time[1] < 10) ? "0" : "") + time[1] + " վրկ․");
                            if (time[0] == 0 && time[1] == 0) {
                                Toolkit.getDefaultToolkit().beep();
                                qualifyTest();
                            }
                        }
                )
        );
        timeline.setCycleCount(time[0] * 60 + time[1]);
        timeline.play();
    }


    private void displayCurrentQuestion() {
        QuestionInApp current;
        try {
            current = test.getQuestion(questionId);
            questionTitle.setText(current.getQuestion());
            answer1.setText(current.getAnswer1());
            answer1CheckBox.setUserData(current.getAnswer1());
            answer2.setText(current.getAnswer2());
            answer2CheckBox.setUserData(current.getAnswer2());
            answer3.setText(current.getAnswer3());
            answer3CheckBox.setUserData(current.getAnswer3());
            questionNumberLabel.setId(questionId.toString());
            questionNumberLabel.setText(questionNumberLabel.getText().substring(0, 5) + " " + questionId.toString());
            questionsSizeLabel.setText("Հարցերի քանակը - " + test.getQuestionsSize());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void initRadioButtons() {
        answer1CheckBox.setToggleGroup(answersGroup);
        answer2CheckBox.setToggleGroup(answersGroup);
        answer3CheckBox.setToggleGroup(answersGroup);
    }
    //region

}
