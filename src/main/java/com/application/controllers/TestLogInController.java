package com.application.controllers;


import com.StartApp;
import com.dto.Student;
import com.jdbc.dao.StudentsDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
    private Timeline timeline;

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
            showTestPage();
            resetTestLoginFields();
            errLabel.getScene().getWindow().hide();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentsService = context.getBean("studentsService", StudentsDAO.class);
        groupBox.setItems(FXCollections.observableArrayList(studentsService.getGroups()));
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

    private void showTestPage() throws IOException{
        Pane testPane = FXMLLoader.load(StartApp.class.getResource("/fxml/test.fxml"));
        Scene testScene = new Scene(testPane, 800, 620);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(testScene);
        stage.getIcons().add(new Image("/icons/TestIcon.png"));
//        stage.setTitle("Testing System _ Test");
        stage.setResizable(false);
        stage.show();
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

    public void initMouse(MouseEvent mouseEvent) {
        confirmButton.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
            int[] ints = {1};
            timeline = new Timeline(new KeyFrame(Duration.millis(3),
                    event1 -> {
                        confirmButton.setStyle("-fx-background-color: Background; -fx-background-image: url('icons/1/1.png'); -fx-rotate: " + ints[0]);
                        ++ints[0];
                        if (ints[0] == 92) {
                            confirmButton.setStyle("-fx-background-color: Background; -fx-background-image: url('icons/1/2.png')");
                        }
                        if (ints[0] > 92) {
                            confirmButton.setStyle("-fx-background-color: Background; -fx-background-image: url('icons/1/3.png')");
                            confirmButton.setStyle("-fx-background-color: Background; -fx-background-image: url('icons/1/4.png')");
                            timeline.stop();
                        }

                    }));
            timeline.setCycleCount(93);
            timeline.play();
        });
        confirmButton.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
            timeline.stop();
            confirmButton.setStyle("-fx-background-image: url('icons/1/5.png'); -fx-background-color: Background");
        });
    }
}
