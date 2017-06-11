package com.application.controllers;

import com.dto.QuestionInApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Suro Smith.
 */
public class QuestionInAppController extends AbstractController implements Initializable {


    @FXML  public Label questionLabel;
    @FXML  public Label rightAnswerLabel;
    @FXML  public Label topicLabel;
    @FXML  public Label ratingLabel;
    @FXML  public Label IDLabel;
    @FXML  public Label answer1Label;
    @FXML  public Label answer2Label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
