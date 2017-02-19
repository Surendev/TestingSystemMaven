package com.application.controllers;

import com.StartApp;
import com.dto.Student;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.services.StudentsService;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    @FXML private ComboBox ratingBox;
    @FXML private ComboBox topicBox;
    @FXML private TextField questionField;
    @FXML private TextField ans1Field;
    @FXML private TextField ans2Field;
    @FXML private TextField ans3Field;
    @FXML private TextField rightAnswerField;
    @FXML private Button confirmQuestionButton;

    private StudentsDAO studentsService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsService = context.getBean("studentsDAO", StudentsService.class);
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
        studentsService.addNewStudent(
                firstNameField.getText(),lastNameField.getText(),courseCheckBox.getValue(),groupField.getText()
        );
    }

    public void addNewQuestion() {

    }

    public void goToMainPage() throws IOException {
        homeButton.getScene().getWindow().hide();
        StartApp.showMainPage();
    }

    private void initializeCheckBoxes(){
        courseCheckBox.setItems(new ObservableListWrapper<>(Arrays.asList(1, 2, 3, 4)));
        ratingBox.setItems(new ObservableListWrapper<>(Arrays.asList(1, 2, 3, 4)));
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
