package com.application.controllers;

import com.StartApp;
import com.dto.QuestionInApp;
import com.dto.Test;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.TestDAO;
import com.jdbc.services.TestService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
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
    private double[] xy;
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
//        double enough = result[1] / 2.5;
//        double good = result[1] / 1.6;
//        double excellent = result[1] / 1.1;
//        String color = result[0] < enough ? "red" : result[0] < good ? "blue" : result[0] < excellent ? "green" : "gold";
        resultLabel.setText("Դուք հավաքել եք " + result[0] + " միավոր " + result[1] + "-ից։");
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
        confirmButton.setVisible(false);
    }

    public void goToHomePage() throws IOException {
        stage = (Stage) closeButton.getScene().getWindow();
        timeline.pause();
        Button ok = new Button("Այո");
        ok.setLayoutX(10.0);
        ok.setLayoutY(90.0);
        ok.setPrefWidth(100.0);

        Button cancel = new Button("Ոչ");
        cancel.setLayoutX(140.0);
        cancel.setLayoutY(90.0);
        cancel.setPrefWidth(100.0);

        Label confirmLabel = new Label("Դու՞րս գալ թեստից։");
        confirmLabel.setPrefWidth(240.0);
        confirmLabel.setFont(javafx.scene.text.Font.font(20.0));
        confirmLabel.setLayoutY(40.0);
        confirmLabel.setAlignment(Pos.CENTER);
        confirmLabel.setTextAlignment(TextAlignment.CENTER);

        Pane confirmPane = new Pane(confirmLabel, ok, cancel);
        confirmPane.setStyle("-fx-background-color: white");
        Scene confirmScene = new Scene(confirmPane, 240, 140);
        Stage confirmStage = new Stage();
        confirmStage.setScene(confirmScene);
        confirmStage.getIcons().add(new Image("/icons/TestIcon.png"));
        confirmStage.setTitle(" log out");
        confirmStage.setResizable(false);
        confirmStage.initModality(Modality.WINDOW_MODAL);
        confirmStage.initOwner(stage);
        confirmStage.show();
        ok.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            stage.close();
            try {
                StartApp.showMainPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancel.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            timeline.play();
            confirmStage.close();
        });
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
        answer1.setStyle("-fx-background-color: Background");
        answer2.setStyle("-fx-background-color: Background");
        answer3.setStyle("-fx-background-color: Background");
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
            fullScreenButton.setStyle("-fx-background-color: Background; -fx-background-image: url('icons/fullScreen.png')");
        } else {
            stage.setFullScreen(false);
            fullScreenButton.setStyle("-fx-background-color: Background; -fx-background-image: url('icons/fullScreen_1.png')");
        }
    }

    public void close() {
        stage = (Stage) questionNumberLabel.getScene().getWindow();
        Platform.exit();
    }


//    public void changeDirection(MouseEvent mouseEvent) {
//        stage.setX(mouseEvent.getScreenX() - xy[0]);
//        stage.setY(mouseEvent.getScreenY() - xy[1]);
//    }

//    public void fixDirection(MouseEvent mouseEvent) {
//        stage = (Stage) questionNumberLabel.getScene().getWindow();
//        if (mouseEvent.getSceneY() <= 20) {
//            xy = new double[2];
//            xy[0] = mouseEvent.getSceneX();
//            xy[1] = mouseEvent.getSceneY();
//        }
//    }

    public void decorateButtons() {
        closeButton.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> closeButton.setStyle("-fx-background-image: url(icons/close.png)"));
        closeButton.addEventFilter(MouseEvent.MOUSE_EXITED, event -> closeButton.setStyle("-fx-background-image: url(icons/close_1.png); -fx-background-color: Background"));
        minimizeButton.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> minimizeButton.setStyle("-fx-background-image: url(icons/minimize.png)"));
        minimizeButton.addEventFilter(MouseEvent.MOUSE_EXITED, event -> minimizeButton.setStyle("-fx-background-image: url(icons/minimize_1.png); -fx-background-color: Background"));
        fullScreenButton.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> fullScreenButton.setStyle("-fx-background-image: url(icons/fullScreen.png); -fx-background-color: #13a61d; -fx-border-color: #13a61d"));
        fullScreenButton.addEventFilter(MouseEvent.MOUSE_EXITED, event -> fullScreenButton.setStyle("-fx-background-image: url(icons/fullScreen_1.png); -fx-background-color: Background"));
    }

    public void resetCoords() {
        xy = null;
    }

    public void initKeys(KeyEvent keyEvent) throws UnsupportedEncodingException {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.equals(KeyCode.PRINTSCREEN)) {
            Clipboard.getSystemClipboard().clear();
//            Stage stage = (Stage) questionNumberLabel.getScene().getWindow();
//            stage.setIconified(true);
            showEndPopup();
        }
        if (keyCode.equals(KeyCode.DOWN)) {
            if (answer1CheckBox.isSelected()) answer2CheckBox.setSelected(true);
            if (answer2CheckBox.isSelected()) answer3CheckBox.setSelected(true);
            if (answer3CheckBox.isSelected()) answer1CheckBox.setSelected(true);
        }
        if (keyCode.equals(KeyCode.UP)) {
            if (answer1CheckBox.isSelected()) answer3CheckBox.setSelected(true);
            if (answer2CheckBox.isSelected()) answer1CheckBox.setSelected(true);
            if (answer3CheckBox.isSelected()) answer2CheckBox.setSelected(true);
        }
        if (keyCode.equals(KeyCode.LEFT)) {
            backToPreviousQuestion();
        }
        if (keyCode.equals(KeyCode.RIGHT)) {
            goToNextButton();
        }
    }


    //region

}
