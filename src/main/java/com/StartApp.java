package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Created by surik on 1/29/17
 */
public class StartApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        BorderPane pane = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(pane, 300,300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
