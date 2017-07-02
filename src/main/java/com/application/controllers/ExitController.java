package com.application.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by Atom on 02-Jul-17.
 *
 */
public class ExitController {

    public Label exitLabel;
    public Button ok;
    public Button cancel;
    private Stage currentStage = new Stage();

//    public void closeStage() {
//        currentStage = (Stage) exitLabel.getScene().getWindow();
//        Stage exitableStage = (Stage) currentStage.getOwner();
//        currentStage.close();
//        exitableStage.close();
//    }

    public void cancel() {
        currentStage = (Stage) exitLabel.getScene().getWindow();
        currentStage.close();
    }
}
