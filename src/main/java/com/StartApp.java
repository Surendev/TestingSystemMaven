package com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by surik on 1/29/17
 *
 * This is where application begin.
 * At begin it loads {@code login.fxml}
 * and prompt user to enter admins login and password
*/
public class StartApp extends Application{

    private static Stage primaryStage;
    private static BorderPane mainLayout;
    private static Pane loginPane;
    private static Pane testPane;
    private static Scene mainScene;
    private static Scene loginScene;
    private static Scene testScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartApp.primaryStage = primaryStage;
        showMainPage();
    }

    public static void showMainPage() throws IOException {
        if(mainLayout==null) {
            mainLayout = FXMLLoader.load(StartApp.class.getResource("/fxml/main.fxml"));
            mainScene = new Scene(mainLayout,300,300);
        }
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Testing System");
        primaryStage.show();
    }

    public static void showLoginPage() throws IOException {
        if (loginPane == null) {
            loginPane = FXMLLoader.load(StartApp.class.getResource("/fxml/login.fxml"));
            loginScene= new Scene(loginPane, 300,300);
        }
        primaryStage.setScene(loginScene);
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
        primaryStage.setTitle("Testing System _ Login");
        primaryStage.show();

    }

    public static void showTestPage() throws IOException{
        if(testPane == null) {
            testPane = FXMLLoader.load(StartApp.class.getResource("/fxml/test.fxml"));
            testScene = new Scene(testPane, 800,600);
        }
        primaryStage.setScene(testScene);
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
        primaryStage.setTitle("Testing System _ Test");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
