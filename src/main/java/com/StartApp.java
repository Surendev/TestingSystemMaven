package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by surik on 1/29/17
 *
 * This is where application begin.
 * At begin it loads {@code login.fxml}
 * and prompt user to enter admins login and password
*/
public class StartApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(pane, 300,300);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
        primaryStage.setTitle("Testing System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
