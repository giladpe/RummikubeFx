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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import rummikub.Rummikub;
import rummikub.gameLogic.model.logic.GameLogic;
import rummikub.gameLogic.view.ioui.InputOutputParser;
import rummikub.gameLogic.view.ioui.JaxBXmlParser;
import rummikub.gameLogic.view.ioui.Utils;

/**
 * FXML Controller class
 *
 * @author Arthur
 */
public class SaveGameMenuController implements Initializable,ControlledScreen,ResetableScreen {
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
        this.myController.setScreen(Rummikub.SUBMENU_SCREEN_ID,ScreensController.NOT_RESETABLE);
    }
    
    //TODO: save the running game and back to the play screen
    @FXML protected void handleSaveGameButtonAction(ActionEvent event) {
        saveGame();
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID,ScreensController.NOT_RESETABLE);
    }
    
    //TODO: save as the running game and back to the play screen
    @FXML protected void handleSaveAsButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(((Button)event.getSource()).getContextMenu()); 
        if(file!=null){
            saveAsGame(file.getPath()+".xml");
        }
// not sure what to do with it or how exacly it works
    }
    
    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }
    private void saveAsGame(String filePath) {
        PlayScreenController gameScreen = (PlayScreenController)this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        boolean succedSavingFile = false;
        GameLogic rummikubLogic= gameScreen.getRummikubLogic();
        try {

                succedSavingFile = JaxBXmlParser.saveAsSettingsToXml(filePath,rummikubLogic.getPlayers(),
                                                                     rummikubLogic.getGameBoard(),
                                                                     rummikubLogic.getGameSettings().getGamesName(),
                                                                     rummikubLogic.getCurrentPlayer().getName());
        }
        catch (SAXException | JAXBException | IOException ex) {
            succedSavingFile = false;
        }
        finally{
            if (!succedSavingFile) {
             //   InputOutputParser.failSavingFileMsg();
             //need to change to label
            }
        }            
    }    
    private void saveGame() {
        PlayScreenController gameScreen = (PlayScreenController)this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        boolean succedSavingFile = false;
        GameLogic rummikubLogic= gameScreen.getRummikubLogic();
        try {

               succedSavingFile = JaxBXmlParser.saveSettingsToXml(rummikubLogic.getPlayers(),
                                                                  rummikubLogic.getGameBoard(),
                                                                  rummikubLogic.getGameSettings().getGamesName(),
                                                                  rummikubLogic.getCurrentPlayer().getName());
            
        }
        catch (SAXException | JAXBException | IOException ex) {
            succedSavingFile = false;
        }
        finally{
            if (!succedSavingFile) {
             //   InputOutputParser.failSavingFileMsg();
             //need to change to label
            }
        }            
    }

    @Override
    public void resetScreen() {        
        this.save.setDisable(isGameSavedBefor());
    }
    
    private boolean isGameSavedBefor(){
        boolean savedBefor=false;
        if(JaxBXmlParser.getLastPathSaved()!=null){
            savedBefor=true;
        }
        return savedBefor;
    }
    
    
}
