/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author giladPe
 */


public class ScreensFramework extends Application { 

    public static final String MAINMENU_SCREEN_ID = "mainMenu"; 
    public static final String MAINMENU_SCREEN_FXML = "MainMenu.fxml"; 
     
    public static final String GAME_PARAMETERS_SCREEN_ID = "gameParameters"; 
    public static final String GAME_PARAMETERS_FXML = "GameParameters.fxml";
    
    public static final String PLAY_SCREEN_ID = "playScreen"; 
    public static final String PLAY_FXML = "PlayScreen.fxml";
    
    public static final String SUBMENU_SCREEN_ID = "subMenu"; 
    public static final String SUBMENU_SCREEN_FXML = "SubMenu.fxml"; 
 
    public static final String SAVE_GAME_SCREEN_ID = "saveGameMenu"; 
    public static final String SAVE_GAME_FXML = "SaveGameMenu.fxml"; 


     @Override 
     public void start(Stage primaryStage) { 
       ScreensController screensController = new ScreensController(); 
       screensController.loadScreen(MAINMENU_SCREEN_ID, MAINMENU_SCREEN_FXML); 
       screensController.loadScreen(GAME_PARAMETERS_SCREEN_ID, GAME_PARAMETERS_FXML);
       //screensController.loadScreen(PLAY_SCREEN_ID, PLAY_FXML); 
       screensController.loadScreen(SAVE_GAME_SCREEN_ID, SAVE_GAME_FXML); 
       screensController.setScreen(MAINMENU_SCREEN_ID); 
       Group root = new Group(); 
       root.getChildren().addAll(screensController); 
       Scene scene = new Scene(root); 
       primaryStage.setScene(scene); 
       primaryStage.show(); 
     } 
    
}
