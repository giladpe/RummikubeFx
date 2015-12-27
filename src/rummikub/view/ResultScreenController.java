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
import rummikub.Rummikub;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class ResultScreenController implements Initializable, ControlledScreen {

    @FXML
    private Button mainMenu;
    @FXML
    private Button restartGame;
    @FXML
    private Label winnerName;
    private ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleMainMenuButtonAction(ActionEvent event) {
         this.myController.setScreen(Rummikub.MAINMENU_SCREEN_ID,(PlayScreenController)myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID));
    }

    @FXML
    private void handleRestartGameButtonAction(ActionEvent event) {
        PlayScreenController gameScreen = (PlayScreenController) this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        gameScreen.createNewGame(gameScreen.getRummikubLogic().getGameSettings());
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID, gameScreen);
        gameScreen.show();
    }

    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }

}
