package com.application.controllers;

import com.StartApp;
import com.application.utils.TopicUtil;
import com.dto.Student;
import com.jdbc.dao.QuestionsDAO;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.services.QuestionsService;
import com.jdbc.services.StudentsService;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by surik on 2/4/17
 */
public class AdminController extends AbstractController implements Initializable{

    @FXML private TabPane tabPane;
    @FXML private Button homeButton;

    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student,String> firstNameCol;
    @FXML private TableColumn<Student,String> lastNameCol;
    @FXML private TableColumn<Student,Integer> courseCol;
    @FXML private TableColumn<Student,String> groupCol;
    @FXML private TableColumn<Student,String> ratingCol;
    @FXML private TableColumn<Student,String> firstExamCol;
    @FXML private TableColumn<Student,String> secondExamCol;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<Integer> courseCheckBox;
    @FXML private TextField groupField;
    @FXML private Label studentAddedLabel;

    @FXML private ComboBox ratingBox;
    @FXML private ComboBox topicBox;
    @FXML private TextArea questionArea;
    @FXML private TextArea answer_1Area;
    @FXML private TextArea answer_2Area;
    @FXML private TextArea answer_3Area;
    @FXML private TextArea rightAnswerArea;
    @FXML private Button confirmQuestionButton;
    @FXML private Label questionAddedLabel;

    private StudentsDAO studentsService;
    private QuestionsDAO questionsService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsService = context.getBean("studentsService", StudentsService.class);
        questionsService = context.getBean("questionsDAO", QuestionsService.class);
        homeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/home.png"))));
        initializeCheckBoxes();
        initializeTableCellFactories();
        showStudents();
    }

    @FXML
    private void showStudents() {
        studentsTable.setItems(new ObservableListWrapper<>(studentsService.getAllStudents()));
    }

    public void addNewStudent() {
        if(!firstNameField.getText().matches("\\w{4,}") || !lastNameField.getText().matches("\\w{5,}") ||
                groupField.getText().matches("\\w{3,}")){
            return;
        }
        studentsService.addNewStudent(
                firstNameField.getText(),lastNameField.getText(),courseCheckBox.getValue(),groupField.getText()
        );
        studentAddedLabel.setText("Ավելացված է");
        AnimationTimer timer = new AnimationTimer() {
            private double opacity = 1;
            @Override
            public void handle(long now) {
                opacity -=0.01;
                studentAddedLabel.opacityProperty().set(opacity);
                if(opacity<=0){
                    stop();
                }
            }
        };
        timer.start();
    }

    public void addNewQuestion() {
        //TODO check valid question parameters and add it to DB
        String question = questionArea.getText();
        String[] answers = new String[]{answer_1Area.getText(), answer_2Area.getText(), answer_3Area.getText()};
        String rightAnswer = rightAnswerArea.getText();
        if (question.isEmpty() || rightAnswer.isEmpty() || answers[0].isEmpty() || answers[0].isEmpty() ||answers[0].isEmpty())
            return;
        String topic = topicBox.getPromptText();
        int rating = Integer.parseInt(ratingBox.getPromptText());
        questionsService.addNewQuestion(question, rating, topic, rightAnswer, answers);

        questionAddedLabel.setText("Ավելացված է");
        AnimationTimer timer = new AnimationTimer() {
            private double opacity = 1;
            @Override
            public void handle(long now) {
                opacity -=0.01;
                studentAddedLabel.opacityProperty().set(opacity);
                if(opacity<=0){
                    stop();
                }
            }
        };
        timer.start();

    }

    public void goToMainPage() throws IOException {
        homeButton.getScene().getWindow().hide();
        StartApp.showMainPage();
    }

    private void initializeCheckBoxes(){
        courseCheckBox.setItems(new ObservableListWrapper<>(Arrays.asList(1, 2, 3, 4)));
        ratingBox.setItems(new ObservableListWrapper<>(Arrays.asList(1, 2, 3, 4)));
        topicBox.setItems(new ObservableListWrapper<>(Arrays.asList(TopicUtil.values())));
    }

    private void initializeTableCellFactories(){
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        firstExamCol.setCellValueFactory(new PropertyValueFactory<>("passedFirstExam"));
        secondExamCol.setCellValueFactory(new PropertyValueFactory<>("passedSecondExam"));
    }
}
