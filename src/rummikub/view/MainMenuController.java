/*
 * this class is responsible for contolling the main menu scene
 */

package rummikub.view;

import rummikub.Rummikub;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;


public class MainMenuController implements Initializable, ControlledScreen {
    
    //FXML Private members:
    @FXML private Button LoadGame;
    @FXML private Button ExitButton;
    @FXML private Button NewGame;
    
    //Private members:
    private ScreensController myController;

    //FXML Protected methods:

    @FXML 
    protected void handleNewGameButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.GAME_PARAMETERS_SCREEN_ID);
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