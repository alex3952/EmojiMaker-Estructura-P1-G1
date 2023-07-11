package com.pooespol.emojimakerg1;

import Controllers.AuthController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private Scene authScene;
    private Scene menuPrincipalScene;
    private Scene createEmoticonScene;
    
    private Stage primaryStage;
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        
        loadScenes();
        
        stage.setTitle("Inicia sesión");
        stage.setScene(authScene);
        stage.show();

    }
    
    public void loadScenes() {
        try {
            FXMLLoader authLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/auth.fxml"));
            FXMLLoader menuLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/menuPrincipal.fxml"));
            FXMLLoader createEmoticonLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/createEmoticon.fxml"));
            
            Parent auth = authLoader.load();
            Parent menu = menuLoader.load();
            Parent createEmoticon = createEmoticonLoader.load();
            
            AuthController authController = authLoader.getController();
            
            authController.setApp(this);
            
            authScene = new Scene(auth);
            menuPrincipalScene = new Scene(menu);
            createEmoticonScene = new Scene(createEmoticon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void switchToMenuPrincipal() {
        primaryStage.setScene(menuPrincipalScene);
        primaryStage.setTitle("Menu Principal");
    }
   
}