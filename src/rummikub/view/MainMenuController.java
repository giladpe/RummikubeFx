/*
 * this class is responsible for contolling the main menu scene
 */

package rummikub.view;

import rummikub.Rummikub;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;
import rummikub.gameLogic.model.logic.GameLogic;
import rummikub.gameLogic.view.ioui.JaxBXmlParser;
import rummikub.gameLogic.view.ioui.Utils;


public class MainMenuController implements Initializable, ControlledScreen,ResetableScreen {
    
    //FXML Private members:
    @FXML private Button LoadGame;
    @FXML private Button ExitButton;
    @FXML private Button NewGame;
    @FXML private Label errorMsg;
    //Private members:
    private ScreensController myController;
    //private GameParametersController gameParmetersController;
    //FXML Protected methods:
    private String EMPTY_STRING="";

    @FXML 
    protected void handleNewGameButtonAction(ActionEvent event) {
        //((GameParametersController)this.myController.getControllerScreen(Rummikub.GAME_PARAMETERS_SCREEN_ID)).resetScreen();
        this.myController.setScreen(Rummikub.GAME_PARAMETERS_SCREEN_ID,ScreensController.NOT_RESETABLE);
        resetScreen();
    }
    
    @FXML 
    protected void handleExitButtonAction(ActionEvent event) {
        closeMainMenuScene(event);
    }
    
    
    //TODO: finish wrting methods to work with scene
    @FXML 
    protected void handleLoadGameButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(((Button)event.getSource()).getContextMenu()); 
        loadGame(file);
        // now i got the file => need to check it if legal
        // then need to init the game from the file
        // then start the game
//        if (file != null) {
//            openFile(file);
//        }
    }
    

        
    //Private methods:
    private void closeMainMenuScene(ActionEvent event) {
        (((Node)event.getSource()).getScene().getWindow()).hide();
    }
    
    //Public methods:
    
    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    } 
    
    private void loadGame(File file) {
        
        boolean succedLoadingFile = false;
        resetScreen();
        try {
            succedLoadingFile = JaxBXmlParser.loadSettingsFromXml(file);

            if (succedLoadingFile) {
                GameLogic rummikubLogic= new GameLogic();
                rummikubLogic.initGameFromFile(JaxBXmlParser.getPlayerArray(),
                                               JaxBXmlParser.getBoard(),
                                               JaxBXmlParser.getCurrPlayer(), 
                                               JaxBXmlParser.getGameName());

        PlayScreenController gameScreen = (PlayScreenController)this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        gameScreen.setRummikubLogic(rummikubLogic);
        gameScreen.show();
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID,gameScreen);
        resetScreen();
//                playGame();
//                roundResualt();
               
            }
        }
        catch (SAXException | IOException ex) {
            succedLoadingFile = false;
        }
        finally{
            if(!succedLoadingFile){
                errorMsg.setText(Utils.Constants.ErrorMessages.FAIL_LOADING_FILE_MSG);
            }
        }
    }

    public void resetScreen() {
        this.errorMsg.setText(EMPTY_STRING);
    }
}

//************************Test Zone*****************************//


//    @FXML
//    protected void handleNewGameButtonAction(ActionEvent event) throws IOException {
//        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
//        primaryStage.setTitle("Game Settings");
//        URL url = this.getClass().getResource("GameParameters.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(url);
//        Parent root = (Parent)fxmlLoader.load(url.openStream());
//        Scene scene = new Scene(root);
//        closeMainMenuScene(event);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//    }
//    
//************************END*****************************//