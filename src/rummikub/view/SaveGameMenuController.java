/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import rummikub.Rummikub;
import rummikub.gameLogic.model.logic.GameLogic;
import rummikub.gameLogic.view.ioui.JaxBXmlParser;

/**
 * FXML Controller class
 *
 * @author Arthur
 */
public class SaveGameMenuController implements Initializable, ControlledScreen, ResetableScreen {

    @FXML
    private Button save;
    @FXML
    private Button saveAs;
    @FXML
    private Button backToPrevMenu;
    private ScreensController myController;
    @FXML
    private Label msg;

    private final String SAVED = "Game was saved!";
    private final String NOT_SAVED = "Error game was not saved!";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected void handleBackToPrevMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.SUBMENU_SCREEN_ID, ScreensController.NOT_RESETABLE);
    }

    //TODO: save the running game and back to the play screen
    @FXML
    protected void handleSaveGameButtonAction(ActionEvent event) {
        saveGame();
        //      resetScreen();
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID, ScreensController.NOT_RESETABLE);
    }

    //TODO: save as the running game and back to the play screen
    @FXML
    protected void handleSaveAsButtonAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilterXML = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.XML");
        fileChooser.getExtensionFilters().add(extFilterXML);
        File file = fileChooser.showSaveDialog(((Button) event.getSource()).getContextMenu());

        if (file != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    saveAsGame(file.getPath());
                }
            });

        }
//        resetScreen();
// not sure what to do with it or how exacly it works
    }

    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }

    private void saveAsGame(String filePath) {
        PlayScreenController gameScreen = (PlayScreenController) this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        boolean succedSavingFile = false;
        GameLogic rummikubLogic = gameScreen.getRummikubLogic();
        try {

            succedSavingFile = JaxBXmlParser.saveAsSettingsToXml(filePath, rummikubLogic.getPlayers(),
                    rummikubLogic.getGameBoard(),
                    rummikubLogic.getGameSettings().getGamesName(),
                    rummikubLogic.getCurrentPlayer().getName());
        } catch (SAXException | JAXBException | IOException ex) {
            succedSavingFile = false;
        } finally {
            handleMsg(succedSavingFile);
        }
    }

    private void saveGame() {
        PlayScreenController gameScreen = (PlayScreenController) this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        boolean succedSavingFile = false;
        GameLogic rummikubLogic = gameScreen.getRummikubLogic();
        try {

            succedSavingFile = JaxBXmlParser.saveSettingsToXml(rummikubLogic.getPlayers(),
                    rummikubLogic.getGameBoard(),
                    rummikubLogic.getGameSettings().getGamesName(),
                    rummikubLogic.getCurrentPlayer().getName());

        } catch (SAXException | JAXBException | IOException ex) {
            succedSavingFile = false;
        } finally {
            handleMsg(succedSavingFile);

        }
    }

    private void handleMsg(boolean succedSavingFile) {
        resetScreen();
        if (!succedSavingFile) {
            msg.setText(this.NOT_SAVED);
            msg.setStyle("-fx-text-fill: #ff0000");
            msg.setVisible(true);
        } else {
            msg.setText(this.SAVED);
            msg.setStyle("-fx-text-fill: blue");
            msg.setVisible(true);
        }
    }

    @Override
    public void resetScreen() {
        this.save.setDisable(!isGameSavedBefor());
        msg.setVisible(false);
    }

    private boolean isGameSavedBefor() {////////////////need to fix this !!!!!!!!!!!!!!!!!!!!
        boolean savedBefor = false;
        if (JaxBXmlParser.getLastPathSaved() != null) {
            savedBefor = true;
        }
        return savedBefor;
    }

}
