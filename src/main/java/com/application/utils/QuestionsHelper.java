package com.application.utils;

import com.StartApp;
import com.application.controllers.QuestionInAppController;
import com.dto.QuestionInApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Suro Smith.
 */
public class QuestionsHelper {


    public static void showQuestionOnNewPage(QuestionInApp clickedQuestion, Stage owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApp.class.getResource("/fxml/question.fxml"));
        Pane newPane = loader.load();
        initLabels(loader.getController(),clickedQuestion);
        Scene scene = new Scene(newPane, 600,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Question");
        stage.getIcons().add(new Image("/icons/TestIcon.png"));
        stage.setResizable(false);
        stage.initOwner(owner);
        stage.show();
    }

    private static void initLabels(QuestionInAppController controller, QuestionInApp clickedQuestion){
        controller.IDLabel.setText(String.valueOf(clickedQuestion.getQuestionId()));
        controller.questionLabel.setText(String.valueOf(clickedQuestion.getQuestion()));
        controller.topicLabel.setText(String.valueOf(clickedQuestion.getTopic()));
        controller.ratingLabel.setText(String.valueOf(clickedQuestion.getRating()));
        controller.rightAnswerLabel.setText(String.valueOf(clickedQuestion.getAnswer1()));
        controller.answer1Label.setText(String.valueOf(clickedQuestion.getAnswer2()));
        controller.answer2Label.setText(String.valueOf(clickedQuestion.getAnswer3()));
    }
}
