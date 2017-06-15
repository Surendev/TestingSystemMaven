package com.application.controllers;


import com.StartApp;
import com.dto.Student;
import com.jdbc.dao.StudentsDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestLogInController extends AbstractController implements Initializable {

    public Button confirmButton;
    private StudentsDAO studentsService;

    private @FXML
    TextField firstNameField;
    private @FXML
    TextField middleNameField;
    private @FXML
    TextField lastNameField;
    private @FXML
    ComboBox<String> groupBox;
    private @FXML
    TextField idField;
    private @FXML
    Label errLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsService = context.getBean("studentsService", StudentsDAO.class);
        groupBox.setItems(FXCollections.observableArrayList(studentsService.getGroups()));
    }

    public void checkAuthentication() throws IOException {
        if (!isValidEnteredValues()) {
            return;
        }
        Student studentFromDB = studentsService.getStudentById(idField.getText());
        if (!studentFromDB.getFirstName().equals(firstNameField.getText()) ||
                !studentFromDB.getLastName().equals(lastNameField.getText()) ||
                !studentFromDB.getGroup().equals(groupBox.getValue())) {
            errLabel.setText("Ուսանողի տվյալները չեն համապատասխանում");
            errLabel.setTextFill(Color.RED);
        } else {
            StartApp.showTestPage();
            resetTestLoginFields();
        }
    }

    private boolean isValidEnteredValues() {
        String value;
        if ((value = firstNameField.getText()).equals("") || value.matches("[^a-zA-z0-9_]")) {
            setErrorToView(firstNameField, "Անունը");
            return false;
        }
        if ((value = lastNameField.getText()).equals("") || value.matches("\\W")) {
            setErrorToView(lastNameField, "Ազգանունը");
            return false;
        }
        if ((value = middleNameField.getText()).equals("") || value.matches("\\W")) {
            setErrorToView(middleNameField, "Հայրանունը");
            return false;
        }
        if ((value = idField.getText()).equals("") || !value.matches("\\d+")) {
            setErrorToView(idField, "Հերթական համարը");
            return false;
        }
        if (groupBox.getSelectionModel().getSelectedIndex() == -1) {
            setErrorToView(groupBox, "Ընտրեք ձեր խումբը");
            return false;
        }
        return true;
    }

    private void reset() {
        errLabel.setText("Լրացրեք ձեր տվյալները ԱՆՍԽԱԼ");
        errLabel.setTextFill(Color.GREEN);
    }

    private void setErrorToView(Node node, String errMsg) {
        errLabel.setText(errMsg + " սխալ է ներմուծված");
        errLabel.setTextFill(Color.RED);
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                reset();
            }
        });
    }

    public void goToHome() throws IOException {
        errLabel.getScene().getWindow().hide();
        resetTestLoginFields();
        StartApp.showMainPage();
    }

    private void resetTestLoginFields() {
        firstNameField.clear();
        middleNameField.clear();
        lastNameField.clear();
        groupBox.getSelectionModel().clearSelection();
        idField.clear();
        errLabel.setText("");
        reset();
    }
}
