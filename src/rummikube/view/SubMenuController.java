/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class SubMenuController implements Initializable {
    @FXML
    private Button SaveGame;
    @FXML
    private Button RestartGame;
    @FXML
    private Button LoadGame;
    @FXML
    private Button ResumeGame;
    @FXML
    private Button ExitButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSaveGameButtonAction(ActionEvent event) {
    }

    @FXML
    private void handleRestartGameButtonAction(ActionEvent event) {
    }

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
    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
    }
    
}
