package com.application.helpers;

import com.dto.Student;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

}
