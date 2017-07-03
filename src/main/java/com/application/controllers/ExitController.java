package com.application.controllers;

import com.StartApp;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Atom on 02-Jul-17.
 *
 */
public class ExitController {

    public Label exitLabel;
    public Button ok;
    public Button cancel;
    private Stage currentStage = new Stage();

    private void close() {
        currentStage = (Stage) exitLabel.getScene().getWindow();
        if (currentStage.getTitle().equals("EXIT")) exit();
        else if (currentStage.getTitle().equals("LOG_OUT")) logOut();
    }

    private void exit() {
        Platform.exit();
    }

    public void closeK(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER) || keyEvent.getCode().equals(KeyCode.SPACE)) close();
    }

    public void closeM(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) close();

    }

    private void logOut() {
        currentStage = (Stage) exitLabel.getScene().getWindow();
        Stage exitableStage = (Stage) currentStage.getOwner();
        exitableStage.close();
        try {
            StartApp.showMainPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cancel() {
        currentStage = (Stage) exitLabel.getScene().getWindow();
        currentStage.close();
    }

    public void cancelK(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER) || keyEvent.getCode().equals(KeyCode.SPACE)) cancel();

    }

    public void cancelM(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) cancel();

    }

}
