package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


/**
 * This is where application begin.
 * At begin it loads {@code login.fxml}
 * and prompt user to enter admins login and password
 */
public class StartApp extends Application {

    private static Stage primaryStage;
    private static BorderPane mainLayout;
    private static Pane registrationPane;
    private static Scene mainScene;
    private static Scene registrationScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartApp.primaryStage = primaryStage;
        StartApp.primaryStage.setResizable(false);
        showMainPage();
    }

    public static void showMainPage() throws IOException {
        if (mainLayout == null) {
            mainLayout = FXMLLoader.load(StartApp.class.getResource("/fxml/main.fxml"));
            mainScene = new Scene(mainLayout, 300, 300);
        }
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Testing System");
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void showLoginPage() throws IOException {
        Pane loginPane = FXMLLoader.load(StartApp.class.getResource("/fxml/login.fxml"));
        Scene loginScene = new Scene(loginPane, 300, 300);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Testing System Login");
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void showRegistrationPage() throws IOException {
        if (registrationPane == null) {
            registrationPane = FXMLLoader.load(StartApp.class.getResource("/fxml/register.fxml"));
            registrationScene = new Scene(registrationPane, 500, 600);
        }
        primaryStage.setScene(registrationScene);
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
        primaryStage.setTitle("Registering system _ Test");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

