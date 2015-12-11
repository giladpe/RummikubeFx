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
 * @author Arthur
 */
public class SaveGameMenuController implements Initializable,ControlledScreen {
    @FXML
    private Button save;
    @FXML
    private Button saveAs;
    @FXML
    private Button backToPrevMenu;
    private ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML protected void handleBackToPrevMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.SUBMENU_SCREEN_ID);
    }
    
    //TODO: save the running game and back to the play screen
    @FXML protected void handleSaveGameButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID);
    }
    
    //TODO: save as the running game and back to the play screen
    @FXML protected void handleSaveAsButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(((Button)event.getSource()).getContextMenu()); 
        // not sure what to do with it or how exacly it works
    }
    
    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }
}
