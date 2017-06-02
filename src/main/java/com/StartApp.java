package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;


/**
 * This is where application begin.
 * At begin it loads {@code login.fxml}
 * and prompt user to enter admins login and password
*/
public class StartApp extends Application{

    private static Stage primaryStage;
    private static BorderPane mainLayout;
    private static Pane loginPane;
    private static Pane testPane;
    private static Pane registrationPane;
    private static Scene mainScene;
    private static Scene loginScene;
    private static Scene testScene;
    private static Scene registrationScene;

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
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
        primaryStage.show();
    }

    public static void showLoginPage() throws IOException {
        if (loginPane == null) {
            loginPane = FXMLLoader.load(StartApp.class.getResource("/fxml/login.fxml"));
            loginScene= new Scene(loginPane, 300,300);
        }else {
//            resetLoginPage();
        }
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Testing System Login");
        primaryStage.getIcons().add(new Image("/icons/TestIcon.png"));
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

    public static void showRegistrationPage() throws IOException{
        if (registrationPane == null){
            registrationPane = FXMLLoader.load(StartApp.class.getResource("/fxml/register.fxml"));
            registrationScene = new Scene(registrationPane, 500, 600);
        }
        primaryStage.setScene(registrationScene);
        primaryStage.setTitle("Registering system _ Test");
        primaryStage.show();
    }

    private static void resetLoginPage() {
        ((TextField)loginScene.lookup("loginField")).setText("");
        ((PasswordField)loginScene.lookup("passwordField")).setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

