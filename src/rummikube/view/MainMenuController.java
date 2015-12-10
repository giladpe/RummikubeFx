
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class MainMenuController implements Initializable {
    @FXML
    private Button NewGame;
    @FXML
    private Button LoadGame;
    @FXML
    private Button ExitButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    
    @FXML protected void handleNewGameButtonAction(ActionEvent event) throws IOException {
        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Game Settings");
        URL url = this.getClass().getResource("GameParameters.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        Parent root = (Parent)fxmlLoader.load(url.openStream());
        Scene scene = new Scene(root);
        closeMainMenuScene(event);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    @FXML protected void handleLoadGameButtonAction(ActionEvent event) {
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
    
    @FXML protected void handleExitButtonAction(ActionEvent event) {
        closeMainMenuScene(event);
    }
    
    private void closeMainMenuScene(ActionEvent event) {
        (((Node)event.getSource()).getScene().getWindow()).hide();
    }
            
}
