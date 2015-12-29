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
import rummikub.Rummikub;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class SubMenuController implements Initializable, ControlledScreen {

    @FXML
    private Button saveGame;
    @FXML
    private Button restartGame;
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
        SaveGameMenuController saveScreen = (SaveGameMenuController) this.myController.getControllerScreen(Rummikub.SAVE_GAME_SCREEN_ID);
        this.myController.setScreen(Rummikub.SAVE_GAME_SCREEN_ID, saveScreen);
    }

    //TODO: 
    @FXML
    private void handlePlayerQuitButtonAction(ActionEvent event) {
        PlayScreenController gameScreen = (PlayScreenController) this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        gameScreen.getRummikubLogic().removeCurrentPlayerFromTheGame();
        if (!gameScreen.getRummikubLogic().isGameOver()) {
            gameScreen.swapTurns();
        }
        
        if (!(gameScreen.getRummikubLogic().isGameOver() || gameScreen.getRummikubLogic().isOnlyOnePlayerLeft())){
            gameScreen.showGameBoardAndPlayerHand();
            this.myController.setScreen(Rummikub.PLAY_SCREEN_ID, ScreensController.NOT_RESETABLE);
        }
        else{
            ResultScreenController resultScreen = (ResultScreenController) this.myController.getControllerScreen(Rummikub.RESULT_SCREEN_ID);
            resultScreen.updatedGameResultMsg();
            this.myController.setScreen(Rummikub.RESULT_SCREEN_ID, gameScreen);
        }
        

    }

    //TODO: restart the logic of the game and the screen
    @FXML
    private void handleRestartGameButtonAction(ActionEvent event) {
        PlayScreenController gameScreen = (PlayScreenController) this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        gameScreen.createNewGame(gameScreen.getRummikubLogic().getGameSettings());
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID, gameScreen);
        gameScreen.showGameBoard();
    }

    //TODO: after loading the file we need to start a game with that file
    @FXML
    private void handleResumeGameButtonAction(ActionEvent event) {
        PlayScreenController gameScreen = (PlayScreenController) this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        gameScreen.showCurrentGameBoardAndCurrentPlayerHand();
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID, ScreensController.NOT_RESETABLE);
    }

    @FXML
    private void handleExitToMainMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.MAINMENU_SCREEN_ID, (PlayScreenController) myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID));
    }

    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }
}
