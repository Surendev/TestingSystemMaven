package com.application.controllers;

import com.StartApp;
import com.application.utils.ConfigsLoader;
import com.application.utils.QuestionsHelper;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by surik on 2/4/17
 */
public class AdminController extends AbstractController implements Initializable {


    @FXML     private Button homeButton;

    //region Students tab
    @FXML     private TableView<Student> studentsTable;
    @FXML     private TableColumn<Student, String> studentIdCol;
    @FXML     private TableColumn<Student, String> firstNameCol;
    @FXML     private TableColumn<Student, String> lastNameCol;
    @FXML     private TableColumn<Student, Integer> courseCol;
    @FXML     private TableColumn<Student, String> groupCol;
    @FXML     private TableColumn<Student, String> middleNameCol;
    //endregion

    //region Questions tab
    @FXML     private TableView<QuestionInApp> questionsTable;
    @FXML     private TableColumn<QuestionInApp, String> questionCol;
    @FXML     private TableColumn<QuestionInApp, Integer> questionRatingCol;
    @FXML     private TableColumn<QuestionInApp, String> topicCol;
    @FXML     private TableColumn<QuestionInApp, String> rightAnswerCol;
    @FXML     private TableColumn<QuestionInApp, String> answer1Col;
    @FXML     private TableColumn<QuestionInApp, String> answer2Col;
    @FXML     private TableColumn<QuestionInApp, String> questionId;
    //endregion

    //region Student update tab
    @FXML     private Label studentIDLabel;
    @FXML     private TextField firstNameField;
    @FXML     private TextField lastNameField;
    @FXML private TextField middleNameField;
    @FXML     private TextField groupField;
    @FXML     private ComboBox<Integer> courseCheckBox;
    @FXML     private ComboBox<Integer> ratingBox;
    @FXML     private Button confirmStudentButton;
    @FXML     private CheckBox editStudentCheckBox;
    @FXML     private TextField studentIDField;
    @FXML     private Label studentAddedLabel;
    @FXML     private Button delStudentButton;
    //endregion

    //region Question update tab
    @FXML     private TextArea questionArea;
    @FXML     private TextArea ans1Area;
    @FXML     private TextArea ans2Area;
    @FXML     private TextArea rightAnswerArea;
    @FXML     private ComboBox<TopicUtil> topicBox;
    @FXML     private CheckBox editQuestionCheckBox;
    @FXML     private TextField questionIDField;
    @FXML     private Label questionIDLabel;
    @FXML     private Button confirmQuestionButton;
    @FXML     private Label questionAddedLabel;
    @FXML     private Button delQuestionButton;
    //endregion


    //region Configs tab
    private Properties props = ConfigsLoader.getInstance().getProperties();

    @FXML     private TextField questionsCountField;
    @FXML     private TextField testTimeField;
    @FXML     private VBox ratingsVBox;
    @FXML     private VBox countOfQuestionsByRatingVBox;
    @FXML     private VBox topicsVBox;
    @FXML     private Label propsAddedLabel;
    //endregion


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
        disableStudentEditFields(true);
        disableQuestionEditFields(true);
        showStudents();
    }

    private void initializeCheckBoxes() {
        courseCheckBox.setItems(new ObservableListWrapper<>(Arrays.asList(1, 2, 3, 4)));
        String[] ratings = ConfigsLoader.getInstance().getProperties().getProperty("test.ratings").split(",");
        Integer[] ratingsArr = new Integer[ratings.length];
        for (int i = 0; i < ratings.length; ++i) {
            ratingsArr[i] = Integer.valueOf(ratings[i]);
        }
        ratingBox.setItems(new ObservableListWrapper<>(Arrays.asList(ratingsArr)));
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

        questionsTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                QuestionInApp clickedQuestion = questionsTable.getSelectionModel().getSelectedItem();
                try {
                    QuestionsHelper.showQuestionOnNewPage(clickedQuestion);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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


    @FXML
    private void addOrUpdateStudent() {
        boolean update = editStudentCheckBox.selectedProperty().get();
        if (update) {
            if (studentIDField.getText().equals("") || !studentIDField.getText().matches("^\\d+$")) {
                studentAddedLabel.setText("Սխալ տվյալ. ID");
                studentAddedLabel.setTextFill(Color.RED);
                return;
            }
        } else if (firstNameField.getText().length() < 3 || lastNameField.getText().length() < 5 ||
                groupField.getText().length() < 5 && courseCheckBox.getSelectionModel().getSelectedIndex() == -1) {
            studentAddedLabel.setText("Սխալ տվյալներ");
            studentAddedLabel.setTextFill(Color.RED);
            return;
        }
        studentsService.addOrUpdateStudent(
                studentIDField.getText().trim(), firstNameField.getText().trim(), middleNameField.getText().trim(), lastNameField.getText().trim(),
                courseCheckBox.getValue(), groupField.getText().trim(), editStudentCheckBox.selectedProperty().get());
        studentAddedLabel.setText(update ? "Փոփոխված է" : "Ավելացված է");
        successPopup(studentAddedLabel);
        resetStudentFields();
    }


    @FXML
    private void deleteStudent() {
        String id = studentIDField.getText();
        if (id == null || id.isEmpty() || !id.matches("^\\d+$")){
            studentAddedLabel.setText("Սխալ տվյալ. ID");
            studentAddedLabel.setTextFill(Color.RED);
            return;
        }

        try {
            studentsService.deleteStudent(Long.valueOf(id));
        } catch (SQLException e) {
            studentAddedLabel.setText(e.getMessage());
            studentAddedLabel.setTextFill(Color.RED);
        }
        studentAddedLabel.setTextFill(Color.GREEN);
        studentAddedLabel.setText("Ջնջված է");
        successPopup(studentAddedLabel);
    }

    @FXML
    private void addOrUpdateQuestion() throws UnsupportedEncodingException, SQLException {
        String question = questionArea.getText();
        String[] answers = new String[]{ans1Area.getText(), ans2Area.getText()};
        String rightAnswer = rightAnswerArea.getText();
        questionAddedLabel.setTextFill(Color.RED);
        String topic = topicBox.getValue() == null ? "" : topicBox.getValue().getTopic();
        Integer rating = ratingBox.getValue() == null ? 0 : ratingBox.getValue();

        boolean update = editQuestionCheckBox.isSelected();
        if (update) {
            if (questionIDField.getText().equals("") || !questionIDField.getText().matches("^\\d+$")) {
                questionAddedLabel.setText("Սխալ տվյալ․ ID");
                questionAddedLabel.setTextFill(Color.RED);
                return;
            }
        } else {
            if (question.isEmpty() || rightAnswer.isEmpty() || answers[0].isEmpty() ||
                    answers[1].isEmpty() || topic.equals("") || rating == 0) {
                questionAddedLabel.setText("Սխալ տվյալներ");
                return;
            }
        }

        questionsService.addOrUpdateQuestion(update, questionIDField.getText(), question, rating, topic, rightAnswer, answers);
        questionAddedLabel.setTextFill(Color.GREEN);
        questionAddedLabel.setText(update ? "Փոփոխված է" : "Ավելացված է");
        successPopup(questionAddedLabel);
        this.resetQuestionFields();
    }


    public void deleteQuestion() {
        String id = questionIDField.getText();
        if (id == null || id.isEmpty() || !id.matches("^\\d{1,5}$")){
            questionAddedLabel.setText("Սխալ տվյալ. ID");
            questionAddedLabel.setTextFill(Color.RED);
            return;
        }

        try {
            questionsService.deleteQuestion(Long.valueOf(id));
            questionAddedLabel.setTextFill(Color.GREEN);
            questionAddedLabel.setText("Ջնջված է");
        } catch (SQLException e) {
            questionAddedLabel.setText(e.getMessage());
            questionAddedLabel.setTextFill(Color.RED);
        }

        successPopup(questionAddedLabel);
    }

    public void goToMainPage() throws IOException {
        homeButton.getScene().getWindow().hide();
        StartApp.showMainPage();
    }

    private void successPopup(Label inLabel) {
        AnimationTimer timer = new AnimationTimer() {
            private double opacity = 1;

            @Override
            public void handle(long now) {
                opacity -= 0.008;
                inLabel.opacityProperty().set(opacity);
                if (opacity <= 0) {
                    stop();
                }
            }
        };
        timer.start();
    }

    public void unHideStudentEditFields() {
        if (editStudentCheckBox.selectedProperty().get()) {
            disableStudentEditFields(false);
        } else {
            disableStudentEditFields(true);
        }
    }

    public void unHideQuestionEditFields() {
        if (editQuestionCheckBox.selectedProperty().get()) {
            disableQuestionEditFields(false);
        } else {
            disableQuestionEditFields(true);
        }
    }

    private void disableQuestionEditFields(boolean bool) {
        questionIDField.setDisable(bool);
        questionIDLabel.setDisable(bool);
        delQuestionButton.setDisable(bool);
        ans1Area.setDisable(!bool);
        ans2Area.setDisable(!bool);
        if (bool) {
            confirmQuestionButton.setText("Ավելացնել");
        } else {
            confirmQuestionButton.setText("Փոփոխել");
        }
    }

    private void disableStudentEditFields(boolean bool) {
        studentIDField.setDisable(bool);
        studentIDLabel.setDisable(bool);
        delStudentButton.setDisable(bool);
        if (bool) {
            confirmStudentButton.setText("Ավելացնել");
        } else {
            confirmStudentButton.setText("Փոփոխել");
        }
    }

    private void resetStudentFields() {
        editStudentCheckBox.selectedProperty().setValue(false);
        studentIDField.setText("");
        firstNameField.setText("");
        middleNameField.setText("");
        lastNameField.setText("");
        courseCheckBox.getSelectionModel().select(-1);
        groupField.setText("");
    }

    private void resetQuestionFields() {
        questionArea.setText("");
        rightAnswerArea.setText("");
        ans1Area.setText("");
        ans2Area.setText("");
        topicBox.getSelectionModel().select(-1);
        ratingBox.getSelectionModel().select(-1);
    }

    public void showAllConfigs() {
        ratingsVBox.getChildren().clear();
        countOfQuestionsByRatingVBox.getChildren().clear();
        topicsVBox.getChildren().clear();


        testTimeField.setText(props.getProperty("test.timer"));
        questionsCountField.setText(props.getProperty("test.questionsCount"));

        int[] ratings =
                Arrays.stream(props.getProperty("test.ratings").split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
        TextField ratingField;
        for (int rating : ratings) {
            ratingField = new TextField(String.valueOf(rating));
            ratingField.setMaxWidth(180);
            ratingsVBox.getChildren().add(ratingField);
        }

        int[] countOfQuestionsByRating =
                Arrays.stream(props.getProperty("test.countOfRatings").split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

        TextField questionCountField;
        for (int count : countOfQuestionsByRating) {
            questionCountField = new TextField(String.valueOf(count));
            questionCountField.setMaxWidth(180);
            countOfQuestionsByRatingVBox.getChildren().add(questionCountField);
        }

        String[] topics = props.getProperty("test.topics").split(",");

        TextField topicField;
        for (String topic : topics) {
            topicField = new TextField(topic);
            topicField.setMaxWidth(180);
            topicsVBox.getChildren().add(topicField);
        }

    }

    public void saveSettingsInPropFIle() {
        if (!props.getProperty("test.timer").equals(testTimeField.getText())) {
            props.setProperty("test.timer", testTimeField.getText());
        }
        if (!props.getProperty("test.questionsCount").equals(questionsCountField.getText())) {
            props.setProperty("test.questionsCount", questionsCountField.getText());
        }

        StringBuilder ratings = new StringBuilder();
        ratingsVBox.getChildren().forEach(node -> ratings.append(((TextField) node).getText()).append(","));
        ratings.delete(ratings.length() - 1, ratings.length());
        props.setProperty("test.ratings", ratings.toString());

        StringBuilder countOfQuestions = new StringBuilder();
        countOfQuestionsByRatingVBox.getChildren().forEach(node -> countOfQuestions.append(((TextField) node).getText()).append(","));
        countOfQuestions.delete(countOfQuestions.length() - 1, countOfQuestions.length());
        props.setProperty("test.countOfRatings", countOfQuestions.toString());

        StringBuilder topics = new StringBuilder();
        topicsVBox.getChildren().forEach(node -> topics.append(((TextField) node).getText()).append(","));
        topics.delete(topics.length() - 1, topics.length());
        props.setProperty("test.topics", topics.toString());

        ConfigsLoader.getInstance().setProperties(props);
        propsAddedLabel.setText("Պահպանված է");
        successPopup(propsAddedLabel);

    }

    public void addQuestionCountField() {
        TextField ratingField = new TextField();
        ratingField.setMaxWidth(180);
        ratingsVBox.getChildren().add(ratingField);
        TextField questionCountField = new TextField();
        questionCountField.setMaxWidth(180);
        countOfQuestionsByRatingVBox.getChildren().add(questionCountField);
    }

    public void addTopicField() {
        TextField newField = new TextField();
        newField.setMaxWidth(180);
        topicsVBox.getChildren().add(newField);
    }
}
