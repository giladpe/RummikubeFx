/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.view.gameParameters;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class GameParametersController implements Initializable {
    @FXML ChoiceBox<Integer> numberOfPlayers;
    @FXML ChoiceBox<Integer> numberOfComputerPlayers;
    @FXML TextField gameName;
    @FXML TextArea PlayerNames;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML protected void handleGameNameTextChange(ActionEvent event) {
        final String gameNameString = gameName.getText();
        
        if (!gameNameString.isEmpty() && gameNameString.startsWith("\\S")) {
            numberOfPlayers.setDisable(false);
        }
        else {
            this.numberOfComputerPlayers.setDisable(true);
            this.numberOfPlayers.setDisable(true);
            this.PlayerNames.setDisable(true);
        }
    }
    
    @FXML protected void handleNumberOfPlayersChoiceChange(ActionEvent event) {

    }
}
