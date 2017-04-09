package com.application.controllers;

import com.StartApp;
import com.application.utils.QuestionsUtil;
import com.application.utils.TopicUtil;
import com.dto.Answer;
import com.dto.Question;
import com.dto.QuestionInApp;
import com.dto.Student;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.services.QuestionsService;
import com.jdbc.services.StudentsService;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by surik on 2/4/17
 */
public class AdminController extends AbstractController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private Button homeButton;
    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, String> firstNameCol;

    @FXML
    private TableColumn<Student, String> lastNameCol;
    @FXML
    private TableColumn<Student, Integer> courseCol;
    @FXML
    private TableColumn<Student, String> groupCol;
    @FXML
    private TableColumn<Student, String> ratingCol;
    @FXML
    private TableColumn<Student, String> firstExamCol;
    @FXML
    private TableColumn<Student, String> secondExamCol;
    @FXML
    private TextField firstNameField;
    @FXML
    private TableView<QuestionInApp> questionsTable;

    @FXML
    private TableColumn<QuestionInApp, String> questionCol;

    @FXML
    private TableColumn<QuestionInApp, Integer> questionRatingCol;
    @FXML
    private TableColumn<QuestionInApp, String> topicCol;
    @FXML
    private TableColumn<QuestionInApp, String> rightAnswerCol;
    @FXML
    private TableColumn<QuestionInApp, String> answer1Col;
    @FXML
    private TableColumn<QuestionInApp, String> answer2Col;
    @FXML
    private TableColumn<QuestionInApp, String> answer3Col;
    @FXML
    private TextField lastNameField;


    @FXML private ComboBox<Integer> courseCheckBox;
    @FXML private TextField groupField;
    @FXML private Label studentAddedLabel;
    @FXML private ComboBox<Integer> ratingBox;

    @FXML private ComboBox<TopicUtil> topicBox;
    @FXML private TextArea questionArea;
    @FXML private TextArea ans1Area;
    @FXML private TextArea ans2Area;
    @FXML private TextArea ans3Area;
    @FXML private TextArea rightAnswerArea;
    @FXML private Button confirmQuestionButton;
    @FXML private Label questionAddedLabel;


    @FXML
    private CheckBox editCheckBox;

    private StudentsDAO studentsService;
    private QuestionsDAO questionsService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsService = context.getBean("studentsService", StudentsService.class);
        questionsService = context.getBean("questionsService", QuestionsService.class);
        homeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/home.png"))));
        initializeCheckBoxes();
        initializeStudentsTableCellFactories();
        initializeQuestionsTableCellFactories();
        showStudents();
//        showQuestions();
        courseCheckBox.getSelectionModel().select(null);
    }

    @FXML
    private void showStudents() {
        try {
            studentsTable.setItems(new ObservableListWrapper<>(studentsService.getAllStudents()));
        } catch (NullPointerException e) {
            System.out.println("Null");
        }
    }

    @FXML
    private void showQuestions() {
        Map<Question, List<Answer>> resultFromDB = questionsService.getAllQuestions();
        if (resultFromDB != null) {
            try {
                questionsTable.setItems(
                        new ObservableListWrapper<>(QuestionsUtil.getInAppFromQuestions(resultFromDB)));
            } catch (NullPointerException ignored) {
            }
        }
    }


    public void addNewStudent() {
        if (firstNameField.getText().length() < 3 || lastNameField.getText().length() < 5 ||
                groupField.getText().length() < 5) {
            studentAddedLabel.setText("Սխալ տվյալներ");
            studentAddedLabel.setTextFill(Color.RED);
            return;
        }
        studentsService.addNewStudent(
                firstNameField.getText(), lastNameField.getText(), courseCheckBox.getValue(), groupField.getText()
        );
        studentAddedLabel.setText("Ավելացված է");
        successPopup(studentAddedLabel);
        resetStudentFields();
    }

    public void addNewQuestion() {
        String question = questionArea.getText();
        String[] answers = new String[]{ans1Area.getText(), ans2Area.getText(), ans3Area.getText()};
        String rightAnswer = rightAnswerArea.getText();
        if (question.isEmpty() || rightAnswer.isEmpty() || answers[0].isEmpty() || answers[0].isEmpty() || answers[0].isEmpty())
            return;
        String topic = topicBox.getValue().getTopic();
        int rating = ratingBox.getValue();
        questionsService.addNewQuestion(question, rating, topic, rightAnswer, answers);

        questionAddedLabel.setText("Ավելացված է");
        successPopup(questionAddedLabel);

    }

    public void goToMainPage() throws IOException {
        homeButton.getScene().getWindow().hide();
        StartApp.showMainPage();
    }

    private void initializeCheckBoxes() {
        courseCheckBox.setItems(new ObservableListWrapper<>(Arrays.asList(1, 2, 3, 4)));
        ratingBox.setItems(new ObservableListWrapper<>(Arrays.asList(1, 2, 3, 4)));
        topicBox.setItems(new ObservableListWrapper<>(Arrays.asList(TopicUtil.values())));
    }

    private void initializeStudentsTableCellFactories() {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        firstExamCol.setCellValueFactory(new PropertyValueFactory<>("passedFirstExam"));
        secondExamCol.setCellValueFactory(new PropertyValueFactory<>("passedSecondExam"));
    }

    private void initializeQuestionsTableCellFactories() {
        questionCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        questionRatingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        rightAnswerCol.setCellValueFactory(new PropertyValueFactory<>("rightAnswer"));
        answer1Col.setCellValueFactory(new PropertyValueFactory<>("answer1"));
        answer2Col.setCellValueFactory(new PropertyValueFactory<>("answer2"));
        answer3Col.setCellValueFactory(new PropertyValueFactory<>("answer3"));
    }

    private void successPopup(Label inLabel) {
        AnimationTimer timer = new AnimationTimer() {
            private double opacity = 1;

            @Override
            public void handle(long now) {
                opacity -= 0.01;
                inLabel.opacityProperty().set(opacity);
                if (opacity <= 0) {
                    stop();
                }
            }
        };
        timer.start();
    }

    private void resetStudentFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        courseCheckBox.getSelectionModel().select(0);
        groupField.setText("");
    }

    public void unHideIdField(ActionEvent actionEvent) {

    }
}
