/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import rummikub.Rummikub;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class SubMenuController implements Initializable,ControlledScreen {
    @FXML
    private Button saveGame;
    @FXML
    private Button restartGame;
    @FXML
    private Button loadGame;
    @FXML
    private Button resumeGame;
    @FXML
    private Button exitToMainMenu;
    @FXML
    private Button playerQuit;
    private ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void handleSaveGameButtonAction(ActionEvent event) {
        SaveGameMenuController saveScreen = (SaveGameMenuController)this.myController.getControllerScreen(Rummikub.SAVE_GAME_SCREEN_ID);
        this.myController.setScreen(Rummikub.SAVE_GAME_SCREEN_ID,saveScreen);
    }
    
    //TODO: 
    @FXML
    private void handlePlayerQuitButtonAction(ActionEvent event) {
        
    }

    //TODO: restart the logic of the game and the screen
    @FXML
    private void handleRestartGameButtonAction(ActionEvent event) {
        
    }

    //TODO: after loading the file we need to start a game with that file
    @FXML
    private void handleLoadGameButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(((Button)event.getSource()).getContextMenu()); 
        
        // now i got the file => need to check it if legal
        // then need to init the game from the file
        // then start the game
//        if (file != null) {
//            openFile(file);
//        }
    }

    @FXML
    private void handleResumeGameButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID,ScreensController.NOT_RESETABLE);
    }

    @FXML
    private void handleExitToMainMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.MAINMENU_SCREEN_ID,ScreensController.NOT_RESETABLE);
    }
    
    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }
}
