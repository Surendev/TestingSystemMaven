package com.application.controllers;

import com.StartApp;
import com.application.helpers.AdminHelper;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.services.StudentsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by surik on 2/4/17
 */
public class AdminController extends AbstractController implements Initializable{

    public TabPane tabPane;
    public Button homeButton;

    public Label firstNameLabel;
    public Label lastNameLabel;
    public TextField firstNameField;
    public TextField lastNameField;
    public Label courseLabel;
    public ComboBox courseCheckBox;
    public Label groupLabel;
    public TextField groupField;

    public Button confirmButton;

    private StudentsDAO studentsService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsService = context.getBean("studentsDAO", StudentsService.class);
    }

    public void showStudents() {

    }

    public void addNewStudent(ActionEvent event) {
    }

    public void addNewQuestion(ActionEvent event) {
    }

    public void goToMainPage(ActionEvent event) throws IOException {
        StartApp.showMainPage();
    }
}
