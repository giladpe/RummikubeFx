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
import javafx.scene.layout.HBox;
import rummikub.Engine.Game;
import rummikub.Rummikub;

/**
 * FXML Controller class
 *
 * @author Arthur
 */
public class PlayScreenController implements Initializable, ResetableScreen, ControlledScreen {
    @FXML
    private Button menu;
    @FXML
    private Button orgenaizeHand;
    @FXML
    private HBox handFirstRow;
    @FXML
    private Button endTrun;
    @FXML
    private Button wizdrawCard;

    private ScreensController myController;
    private Game gameLogic;
   

    @FXML
    private void handleMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.SUBMENU_SCREEN_ID,ScreensController.NOT_RESETABLE);
    }

    @FXML
    private void handleOrgenaizeHandAction(ActionEvent event) {
    }

    @FXML
    private void handleEndTrunAction(ActionEvent event) {
    }

    @FXML
    private void handleWizdrawCardAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //not good - what about loading file?????
        //Game.Settings gameSetting = ((GameParametersController)myController.getControllerScreen(Rummikub.GAME_PARAMETERS_SCREEN_ID)).getGameSettings();
        //this.gameLogic = new Game(gameSetting);
    }    
    
    public void createNewGame(Game.Settings gameSetting) {
        this.gameLogic = new Game(gameSetting);
    }

    //TODO:
    @Override
    public void resetScreen() {
        int i=0;
        i++;
    }

    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }
}
