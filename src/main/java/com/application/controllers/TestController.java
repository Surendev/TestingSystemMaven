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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
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

    @FXML private Button forwardButton;
    @FXML private Button backButton;

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
    private int[] chosenAnswers;
    private QuestionInApp current;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test = testService.generateTest(context.getBean("questionsService", QuestionsDAO.class));
        chosenAnswers = new int[test.getAppQuestionsSize()];
        initRadioButtons();
        displayCurrentQuestion();
        initializeTimer();

    }
    private void rollbackRadioButtons(){
        if (answer1CheckBox.isSelected()) chosenAnswers[questionId-1] = 1;
        else if (answer2CheckBox.isSelected()) chosenAnswers[questionId-1] = 2;
        else if (answer3CheckBox.isSelected()) chosenAnswers[questionId-1] = 3;
        else chosenAnswers[questionId-1] = 0;

    }

    public void goToNextButton() throws UnsupportedEncodingException {
        rollbackRadioButtons();
        questionId = Integer.parseInt(questionNumberLabel.getId()) + 1;
        if (questionId > test.getAppQuestionsSize()) questionId = 1;
        displayCurrentQuestion();
    }

    public void backToPreviousQuestion() {
        rollbackRadioButtons();
        questionId = Integer.parseInt(questionNumberLabel.getId()) - 1;
        if (questionId < 1) questionId = test.getAppQuestionsSize();
        displayCurrentQuestion();
    }

    @FXML
    private void showEndPopup() {
       disableTest();
       double[] result = test.qualifyTest();
       questionTitle.setFont(javafx.scene.text.Font.font(25));
       questionTitle.setTextFill(Color.BLACK);
       questionTitle.setText("Դուք հավաքել եք " + result[0] + " միավոր " + result[1] + "-ից");

    }

    private void disableTest(){
        timeline.stop();
        questionNumberLabel.setVisible(false);
        questionsSizeLabel.setVisible(false);
        timerLabel.setVisible(false);
        answer1CheckBox.setDisable(true);
        answer2CheckBox.setDisable(true);
        answer3CheckBox.setDisable(true);
        answer1CheckBox.setVisible(false);
        answer2CheckBox.setVisible(false);
        answer3CheckBox.setVisible(false);
        answer1.setText("");
        answer2.setText("");
        answer3.setText("");
        insertedQuestionId.setDisable(true);
        insertedQuestionId.setVisible(false);
        test.markAnswerToQuestion(chosenAnswers);
        forwardButton.setDisable(true);
        backButton.setDisable(true);
        forwardButton.setVisible(false);
        backButton.setVisible(false);
    }
    public void goToHomePage() throws IOException {
        StartApp.showMainPage();
    }


    public void goToQuestion(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            questionId = Integer.parseInt(insertedQuestionId.getText());
            displayCurrentQuestion();
        }
    }


    //region Initialization
    private void initializeTimer() {
        Integer[] time = {timeOfExam, 0};
        timerLabel.setTextFill(Color.DARKGREEN);
        timerLabel.setText(time[0] + " ր․ " + ((time[1] < 10) ? "0" : "") + time[1] + " վրկ․");
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        ae -> {
                            if (time[0] <= 19) timerLabel.setTextFill(Color.ORANGE);
                            if (time[0] <= 5) timerLabel.setTextFill(Color.DARKRED);

                            if (time[1] <= 0) {
                                time[0]--;
                                time[1] = 60;
                            }
                            time[1]--;
                            timerLabel.setText(time[0] + " ր․ " + ((time[1] < 10) ? "0" : "") + time[1] + " վրկ․");
                            if (time[0] == 0 && time[1] == 0) {
                                Toolkit.getDefaultToolkit().beep();
                                showEndPopup();
                            }
                        }
                )
        );
        timeline.setCycleCount(time[0] * 60 + time[1]);
        timeline.play();
    }

    private void displayCurrentQuestion() {
        try {
            current = test.getQuestion(questionId);
            questionTitle.setText(current.getQuestion());
            answer1.setText(current.getAnswer1());
            answer2.setText(current.getAnswer2());
            answer3.setText(current.getAnswer3());
            questionNumberLabel.setId(questionId.toString());
            questionNumberLabel.setText(questionNumberLabel.getText().substring(0, 5) + " " + questionId.toString() + " - " + test.getCurrentRating(questionId) + " միավոր");
            questionsSizeLabel.setTextFill(Color.DARKGOLDENROD);
            questionsSizeLabel.setText("Հարցերի քանակը - " + test.getAppQuestionsSize());
            answer1CheckBox.setSelected(chosenAnswers[questionId - 1] == 1);
            answer2CheckBox.setSelected(chosenAnswers[questionId - 1] == 2);
            answer3CheckBox.setSelected(chosenAnswers[questionId - 1] == 3);
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
