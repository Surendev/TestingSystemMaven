package com.application.controllers;

import com.application.helpers.AdminHelper;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.services.StudentsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by surik on 2/4/17
 */
public class AdminController extends AbstractController implements Initializable{

    private StudentsDAO studentsService;

    @FXML Pane dataPane;
    @FXML Button showStudents;
    @FXML Button newStudent;
    @FXML Button newQuestion;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsService = context.getBean("studentsDAO", StudentsService.class);
    }

    public void showStudents() {
        dataPane.getChildren().add(AdminHelper.
                getGroupOfLabels("Full Name","Group","Course"
                        ,"Rating","1st Exam","2st Exam",50));
        dataPane.getChildren().add(AdminHelper.getStudentsData(studentsService.getAllStudents()));
    }

    public void addNewStudent(ActionEvent event) {

    }

    public void addNewQuestion(ActionEvent event) {

    }
}
