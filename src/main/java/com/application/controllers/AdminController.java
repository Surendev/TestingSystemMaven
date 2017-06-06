package com.application.controllers;

import com.StartApp;
import com.application.utils.ConfigsLoader;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

/**
 * Created by surik on 2/4/17
 */
public class AdminController extends AbstractController implements Initializable {


    @FXML private Button homeButton;
    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, String> studentIdCol;
    @FXML private TableColumn<Student, String> firstNameCol;
    @FXML private TableColumn<Student, String> lastNameCol;

    @FXML private TableColumn<Student, Integer> courseCol;
    @FXML private TableColumn<Student, String> groupCol;
    @FXML private TableColumn<Student, String> middleNameCol;
    @FXML private TextField firstNameField;
    @FXML private TableView<QuestionInApp> questionsTable;
    @FXML private TableColumn<QuestionInApp, String> questionCol;

    @FXML private TableColumn<QuestionInApp, Integer> questionRatingCol;

    @FXML private TableColumn<QuestionInApp, String> topicCol;
    @FXML private TableColumn<QuestionInApp, String> rightAnswerCol;
    @FXML private TableColumn<QuestionInApp, String> answer1Col;
    @FXML private TableColumn<QuestionInApp, String> answer2Col;
    @FXML private TableColumn<QuestionInApp, String> questionId;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<Integer> courseCheckBox;

    @FXML private TextField groupField;
    @FXML private Label studentAddedLabel;
    @FXML private ComboBox<Integer> ratingBox;
    @FXML private ComboBox<TopicUtil> topicBox;
    @FXML private Button confirmStudentButton;

    @FXML private TextArea questionArea;
    @FXML private TextArea ans1Area;
    @FXML private TextArea ans2Area;
    @FXML private TextArea ans3Area;
    @FXML private TextArea rightAnswerArea;
    @FXML private Button confirmQuestionButton;
    @FXML private Label questionAddedLabel;

    @FXML private CheckBox editCheckBox;
    @FXML private TextField iDField;
    @FXML private Label iDLabel;

    private Properties props = ConfigsLoader.getInstance().getProperties();
    @FXML private TextField questionsCountField;
    @FXML private TextField testTimeField;
    @FXML private VBox ratingsVBox;
    @FXML private VBox countOfQuestionsByRatingVBox;
    @FXML private VBox topicsVBox;
    @FXML private Button savePropsButton;

    @FXML private ScrollPane ratingsPane;
    @FXML private ScrollPane questionCountsPane;
    @FXML private ScrollPane topicsPane;

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
        showQuestions();
        courseCheckBox.getSelectionModel().select(null);
        disableEditFields(true);
        locateAllConfigs();
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
        if (resultFromDB == null) {
            System.err.println("Questions Not Found");
        }
            try {
                questionsTable.setItems(
                        new ObservableListWrapper<>(
                                QuestionsUtil.getInAppFromQuestions(resultFromDB)));
            } catch (NullPointerException ignored) {
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


    }


    public void addNewStudent() {
        if (editCheckBox.selectedProperty().get()){
            if(iDField.getText().equals("") || !iDField.getText().matches("^\\d+$")){
                studentAddedLabel.setText("Սխալ տվյալ. ID");
                studentAddedLabel.setTextFill(Color.RED);
                return;
            }
            if (firstNameField.getText().length() < 3 && lastNameField.getText().length() < 5 &&
                    groupField.getText().length() < 5 && courseCheckBox.getSelectionModel().getSelectedIndex() == -1) {
                studentAddedLabel.setText("Սխալ տվյալներ");
                studentAddedLabel.setTextFill(Color.RED);
                return;
            }
        }else
        if (firstNameField.getText().length() < 3 || lastNameField.getText().length() < 5 ||
                groupField.getText().length() < 5) {
            studentAddedLabel.setText("Սխալ տվյալներ");
            studentAddedLabel.setTextFill(Color.RED);
            return;
        }
        studentsService.addOrUpdateStudent(
                iDField.getText(),firstNameField.getText(), lastNameField.getText(),
                courseCheckBox.getValue(), groupField.getText(),editCheckBox.selectedProperty().get());
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
        topicBox.setItems(new ObservableListWrapper<>(TopicUtil.topics));
    }

    private void initializeStudentsTableCellFactories() {
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
        middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));
    }

    private void initializeQuestionsTableCellFactories() {
        questionCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        questionRatingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        rightAnswerCol.setCellValueFactory(new PropertyValueFactory<>("answer1"));
        answer1Col.setCellValueFactory(new PropertyValueFactory<>("answer2"));
        answer2Col.setCellValueFactory(new PropertyValueFactory<>("answer3"));
        questionId.setCellValueFactory(new PropertyValueFactory<>("questionId"));
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

    public void unHideEditFields() {
        if(editCheckBox.selectedProperty().get()){
            disableEditFields(false);
        }else{
            disableEditFields(true);
        }
    }

    private void resetStudentFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        courseCheckBox.getSelectionModel().select(-1);
        groupField.setText("");
    }

    private void disableEditFields(boolean bool){
        iDField.setDisable(bool);
        iDLabel.setDisable(bool);
        if(bool){
            confirmStudentButton.setText("Ավելացնել");
        }else {
            confirmStudentButton.setText("Փոփոխել");
        }
    }

    private void locateAllConfigs() {
        testTimeField.setText(props.getProperty("test.timer"));
        questionsCountField.setText(props.getProperty("test.questionsCount"));

        int [] ratings =
                Arrays.stream(props.getProperty("test.ratings").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        TextField ratingField;
        for (int rating : ratings){
            ratingField = new TextField(String.valueOf(rating));
            ratingField.setPrefWidth(198);
            ratingsVBox.getChildren().add(ratingField);
        }

        int [] countOfQuestionsByRating =
                Arrays.stream(props.getProperty("test.countOfRatings").split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

        TextField questionCountField;
        for (int count : countOfQuestionsByRating){
            questionCountField = new TextField(String.valueOf(count));
            questionCountField.setPrefWidth(198);
            countOfQuestionsByRatingVBox.getChildren().add(questionCountField);
        }

        String[] topics = props.getProperty("test.topics").split(",");

        TextField topicField;
        for (String topic : topics){
            topicField = new TextField(topic);
            topicField.setPrefWidth(198);
            topicsVBox.getChildren().add(topicField);
        }

        ratingsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ratingsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        topicsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        topicsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        questionCountsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        questionCountsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    public void saveSettingsInPropFIle() {
        if(!props.getProperty("test.timer").equals(testTimeField.getText())){
            props.setProperty("test.timer",testTimeField.getText());
        }
        if (!props.getProperty("test.questionsCount").equals(questionsCountField.getText())){
            props.setProperty("test.questionsCount",questionsCountField.getText());
        }

        StringBuilder ratings = new StringBuilder();
        ratingsVBox.getChildren().forEach(node -> ratings.append(((TextField) node).getText()).append(","));
        ratings.delete(ratings.length()-1,ratings.length());
        props.setProperty("test.ratings",ratings.toString());

        StringBuilder countOfQuestions = new StringBuilder();
        countOfQuestionsByRatingVBox.getChildren().forEach(node -> countOfQuestions.append(((TextField) node).getText()).append(","));
        countOfQuestions.delete(countOfQuestions.length()-1,countOfQuestions.length());
        props.setProperty("test.countOfRatings",countOfQuestions.toString());

        StringBuilder topics = new StringBuilder();
        topicsVBox.getChildren().forEach(node -> topics.append(((TextField) node).getText()).append(","));
        topics.delete(topics.length()-1,topics.length());
        props.setProperty("test.topics",topics.toString());

        ConfigsLoader.getInstance().setProperties(props);
    }
}
