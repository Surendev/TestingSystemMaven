package com.application.helpers;

import com.dto.Student;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.text.TextAlignment;
;import java.util.List;

/**
 * Created by surik on 2/4/17
 */

public class AdminHelper {
    private static final int STANDART_HEIGHT  = 30;


    public static Group getGroupOfLabels(String fName, String group, String course,
                                         String rating, String fExam, String sExam,int layoutY){
        Label fullName = new Label(fName);
        fullName.setLayoutX(0);
        fullName.setPrefWidth(270);
        fullName.setPrefHeight(STANDART_HEIGHT);
        fullName.setStyle("-fx-border-color: black; -fx-border-width: 1px");
        Label groupLabel = new Label(group);
        groupLabel.setLayoutX(270);
        groupLabel.setPrefWidth(50);
        groupLabel.setPrefHeight(STANDART_HEIGHT);
        groupLabel.setTextAlignment(TextAlignment.CENTER);
        groupLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px");
        Label courseLabel= new Label(course);
        courseLabel.setLayoutX(320);
        courseLabel.setPrefWidth(60);
        courseLabel.setPrefHeight(STANDART_HEIGHT);
        courseLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px");
        Label ratingLabel = new Label(rating);
        ratingLabel.setLayoutX(380);
        ratingLabel.setPrefWidth(60);
        ratingLabel.setPrefHeight(STANDART_HEIGHT);
        ratingLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px");
        Label fExamPassed = new Label(fExam);
        fExamPassed.setLayoutX(440);
        fExamPassed.setPrefWidth(80);
        fExamPassed.setPrefHeight(STANDART_HEIGHT);
        fExamPassed.setStyle("-fx-border-color: black; -fx-border-width: 1px");
        Label sExamPassed = new Label(sExam);
        sExamPassed.setLayoutX(520);
        sExamPassed.setPrefWidth(80);
        sExamPassed.setPrefHeight(STANDART_HEIGHT);
        sExamPassed.setStyle("-fx-border-color: black; -fx-border-width: 1px");

        Group columns = new Group
                (fullName,groupLabel,courseLabel,ratingLabel,fExamPassed,sExamPassed);
        columns.setLayoutY(layoutY);
        columns.setLayoutX(0);
        columns.setFocusTraversable(false);
        return columns;
    }

    public static ScrollPane getStudentsData(List<Student> allStudents) {
        if(allStudents == null){
            return new ScrollPane(new Label("Students are not found"));
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setLayoutX(0);
        scrollPane.setLayoutY(80);
        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(20);
        AnchorPane content = new AnchorPane();
        int layoutY = 0;
        for (Student currStudent : allStudents){
            Group row = getGroupOfLabels(currStudent.getFirstName()+" "+currStudent.getLastName(),
                    currStudent.getGroup(),String.valueOf(currStudent.getCourse()),String.valueOf(currStudent.getRating()),
                currStudent.isPassedFirstExam() ? "" : "Not" + " Passed",
                    currStudent.isPassedSecondExam() ? "" : "Not" + " Passed", layoutY);

            content.getChildren().add(row);
        }

        scrollPane.setContent(content);
        return scrollPane;
    }


    public static Group getGroupOfAddingStudent(){
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField  = new TextField("Last Name");
        TextField courseField = new TextField("Course");
        TextField groupField = new TextField("Group");

        firstNameField.setLayoutY(20);
        lastNameField.setLayoutY(80);
        courseField.setLayoutY(140);
        groupField.setLayoutY(200);

        firstNameField.setLayoutX(50);
        lastNameField.setLayoutX(50);
        courseField.setLayoutX(50);
        groupField.setLayoutX(50);

        Button confirmButton = new Button("Confirm");
        confirmButton.setLayoutX(50);
        confirmButton.setLayoutY(260);
        return new Group(firstNameField,lastNameField,courseField,groupField,confirmButton);
    }

    public static Group getGroupOfAddingQuestion() {
        TextField questionField = new TextField("Question");
        TextField ratingField  = new TextField("Rating");
        TextField topicField = new TextField("Topic");
        TextField rightAnswerField = new TextField("RIGHT Answer");
        TextField answer1Field = new TextField("Answer 1");
        TextField answer2Field = new TextField("Answer 2");
        TextField answer3Field = new TextField("Answer 3");

        questionField.setLayoutY(20);
        ratingField.setLayoutY(60);
        topicField.setLayoutY(100);
        rightAnswerField.setLayoutY(140);
        answer1Field.setLayoutY(180);
        answer2Field.setLayoutY(220);
        answer3Field.setLayoutY(260);

        questionField.setLayoutX(50);
        ratingField.setLayoutX(50);
        topicField.setLayoutX(50);
        rightAnswerField.setLayoutX(50);
        answer1Field.setLayoutX(50);
        answer2Field.setLayoutX(50);
        answer3Field.setLayoutX(50);

        Button confirmButton = new Button("Confirm");
        confirmButton.setLayoutX(50);
        confirmButton.setLayoutY(300);
        return new Group(questionField,ratingField,topicField,rightAnswerField,answer1Field,answer2Field,answer3Field,confirmButton);
    }
}
