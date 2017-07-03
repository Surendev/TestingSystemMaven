package com.application.controllers;

import com.dto.QuestionInApp;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.TestDAO;
import com.jdbc.services.TestService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    public Button closeButton;
    public Button minimizeButton;
    public Button confirmButton;
    public AnchorPane answersPane;
    public Button fullScreenButton;
    public Label resultLabel;

    private Stage stage;
    //    private double[] xy;
    private Integer timeOfExam = Integer.valueOf(adminConfigs.getProperty("test.timer"));
    private Timeline timeline;

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
    private boolean examPassed = false;
    private int[] rights;
    private Scene exitScene = null;

    {
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/exit.fxml"));
            exitScene = new Scene(root, 250, 140);
        } catch (IOException e) {
            exitScene = null;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test = testService.generateTest(context.getBean("questionsService", QuestionsDAO.class));
        chosenAnswers = new int[test.getAppQuestionsSize()];
        for (int i = 0; i < chosenAnswers.length; i++) {
            chosenAnswers[i] = 1;
        }
        questionsSizeLabel.setText("Հարցերի քանակը - " + test.getAppQuestionsSize());
        rights = new int[chosenAnswers.length];
        fullScreenButton.setDisable(true);
        initRadioButtons();
        displayCurrentQuestion();
        initializeTimer();
    }

    private void rollbackRadioButtons() {
        if (answer1CheckBox.isSelected()) chosenAnswers[questionId - 1] = 1;
        else if (answer2CheckBox.isSelected()) chosenAnswers[questionId - 1] = 2;
        else if (answer3CheckBox.isSelected()) chosenAnswers[questionId - 1] = 3;

    }

    public void goToNextButton() throws UnsupportedEncodingException {
        if (!examPassed) rollbackRadioButtons();
        questionId = Integer.parseInt(questionNumberLabel.getId()) + 1;
        if (questionId > test.getAppQuestionsSize()) questionId = 1;
        displayCurrentQuestion();
    }

    public void backToPreviousQuestion() {
        if (!examPassed) rollbackRadioButtons();
        questionId = Integer.parseInt(questionNumberLabel.getId()) - 1;
        if (questionId < 1) questionId = test.getAppQuestionsSize();
        displayCurrentQuestion();
    }

    @FXML
    private void showEndPopup() {
        disableTest();
        test.markAnswerToQuestion(chosenAnswers);
        double[] result = test.qualifyTest(rights);
        double enough = result[1] / 2.5;
        double good = result[1] / 1.6;
        double excellent = result[1] / 1.1;
        String color = result[0] < enough ? "red" : result[0] < good ? "blue" : result[0] < excellent ? "green" : "gold";
        resultLabel.setText("Ստացած միավորները ՝  " + result[1] + "-ից   " + result[0]);
        resultLabel.setStyle("-fx-text-fill: linear-gradient(to right, black 88%, " + color + " 12%)");
        examPassed = true;
        showDifference();
    }

    private void disableTest() {
        timeline.stop();
        Toolkit.getDefaultToolkit().beep();
        answer1CheckBox.setDisable(true);
        answer2CheckBox.setDisable(true);
        answer3CheckBox.setDisable(true);
        answer1CheckBox.setVisible(false);
        answer2CheckBox.setVisible(false);
        answer3CheckBox.setVisible(false);
        confirmButton.setDisable(true);
    }

    public void goToHomePage() throws IOException {
        stage = (Stage) closeButton.getScene().getWindow();
        Stage exitStage = new Stage();
        if (exitScene != null) {
            exitStage.setScene(exitScene);
            exitStage.setTitle("LOG_OUT");
            exitStage.getIcons().add(new Image("/icons/TestIcon.png"));
            stage.setResizable(false);
            exitStage.initModality(Modality.APPLICATION_MODAL);
            exitStage.initOwner(stage);
            Label label = (Label) exitScene.getRoot().lookup("#exitLabel");
            label.setText("Դու՞րս գալ թեստից");
            exitStage.show();
        }
    }


    public void goToQuestion(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String questionNumber = insertedQuestionId.getText().trim();
            questionId = Integer.parseInt(questionNumber.matches("-?\\d+(\\.\\d+)?") ? questionNumber : questionId.toString());
            if (!examPassed) rollbackRadioButtons();
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
            QuestionInApp current = test.getQuestion(questionId);
            questionTitle.setText(current.getQuestion());
            answer1.setText(current.getAnswer1());
            answer2.setText(current.getAnswer2());
            answer3.setText(current.getAnswer3());
            questionNumberLabel.setId(questionId.toString());
            questionNumberLabel.setText(questionNumberLabel.getText().substring(0, 5) + " " + questionId.toString() +
                    " | " + test.getCurrentRating(questionId) + " միավոր" + " | " + test.getCurrentTopic(questionId));
            if (!examPassed) {
                answer1CheckBox.setSelected(chosenAnswers[questionId - 1] == 1);
                answer2CheckBox.setSelected(chosenAnswers[questionId - 1] == 2);
                answer3CheckBox.setSelected(chosenAnswers[questionId - 1] == 3);
            }
            if (examPassed) showDifference();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void showDifference() {
        answer1.setStyle("-fx-background-color: transparent");
        answer2.setStyle("-fx-background-color: transparent");
        answer3.setStyle("-fx-background-color: transparent");
        stage = (Stage) questionNumberLabel.getScene().getWindow();
        Label current;
        current = (Label) stage.getScene().getRoot().lookup("#answer" + chosenAnswers[questionId - 1]);
        current.setStyle("-fx-background-color: red");
        current = (Label) stage.getScene().getRoot().lookup("#answer" + rights[questionId - 1]);
        current.setStyle("-fx-background-color: green");
    }


    private void initRadioButtons() {
        answer1CheckBox.setToggleGroup(answersGroup);
        answer2CheckBox.setToggleGroup(answersGroup);
        answer3CheckBox.setToggleGroup(answersGroup);
    }

    public void hideStage() {
//        stage = (Stage) questionNumberLabel.getScene().getWindow();
//        stage.setIconified(true);
    }

    public void minimize() {
        stage = (Stage) questionNumberLabel.getScene().getWindow();
        stage.setIconified(true);
    }

    public void setFullScreen() {
        stage = (Stage) questionNumberLabel.getScene().getWindow();
        if (!stage.isFullScreen()) {
            stage.setFullScreen(true);
            fullScreenButton.setStyle("-fx-background-color: transparent; -fx-background-image: url('icons/fullScreen.png')");
        } else {
            stage.setFullScreen(false);
            fullScreenButton.setStyle("-fx-background-color: transparent; -fx-background-image: url('icons/fullScreen_1.png')");
        }
    }

    public void close() {
        stage = (Stage) questionNumberLabel.getScene().getWindow();
        Stage exitStage = new Stage();
        if (exitScene != null) {
            exitStage.setScene(exitScene);
            exitStage.setTitle("EXIT");
            exitStage.getIcons().add(new Image("/icons/TestIcon.png"));
            stage.setResizable(false);
            exitStage.initModality(Modality.APPLICATION_MODAL);
            exitStage.initOwner(stage);
            Label label = (Label) exitScene.getRoot().lookup("#exitLabel");
            label.setText("Փակե՞լ ծրագիրը");
            exitStage.show();
        } else {
            Platform.exit();
        }
    }

    public void decorateButtons() {
        closeButton.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> closeButton.setStyle("-fx-background-image: url(icons/close.png)"));
        closeButton.addEventFilter(MouseEvent.MOUSE_EXITED, event -> closeButton.setStyle("-fx-background-image: url(icons/close_1.png); -fx-background-color: transparent"));
        minimizeButton.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> minimizeButton.setStyle("-fx-background-image: url(icons/minimize.png)"));
        minimizeButton.addEventFilter(MouseEvent.MOUSE_EXITED, event -> minimizeButton.setStyle("-fx-background-image: url(icons/minimize_1.png); -fx-background-color: transparent"));
        fullScreenButton.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> fullScreenButton.setStyle("-fx-background-image: url(icons/fullScreen.png); -fx-background-color: #13a61d; -fx-border-color: #13a61d"));
        fullScreenButton.addEventFilter(MouseEvent.MOUSE_EXITED, event -> fullScreenButton.setStyle("-fx-background-image: url(icons/fullScreen_1.png); -fx-background-color: transparent"));
    }
    //region
}
