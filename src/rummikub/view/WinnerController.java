/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class WinnerController implements Initializable {

    @FXML
    private Button mainMenu;
    @FXML
    private Button restartGame;
    @FXML
    private Label winnerName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleMainMenuButtonAction(ActionEvent event) {
    }

    @FXML
    private void handleRestartGameButtonAction(ActionEvent event) {
    }

    
}
