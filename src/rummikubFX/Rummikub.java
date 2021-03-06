/*
 * this class responsible for starting the application of rummikub game
 */
package rummikubFX;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import rummikub.view.ScreensController;

public class Rummikub extends Application {

    //Constants:
    public static final String MAINMENU_SCREEN_ID = "mainMenu";
    public static final String MAINMENU_SCREEN_FXML = "MainMenu.fxml";

    public static final String GAME_PARAMETERS_SCREEN_ID = "gameParameters";
    public static final String GAME_PARAMETERS_FXML = "GameParameters.fxml";

    public static final String PLAY_SCREEN_ID = "playScreen";
    public static final String PLAY_SCREEN_FXML = "PlayScreen.fxml";

    public static final String SUBMENU_SCREEN_ID = "subMenu";
    public static final String SUBMENU_SCREEN_FXML = "SubMenu.fxml";

    public static final String SAVE_GAME_SCREEN_ID = "saveGameMenu";
    public static final String SAVE_GAME_FXML = "SaveGameMenu.fxml";

    //not created yet
    public static final String RESULT_SCREEN_ID = "resultScreen";
    public static final String RESULT_SCREEN_FXML = "ResultScreen.fxml";

    @Override
    public void start(Stage primaryStage) {

//        /**/
//        rummikub.gameLogic.controller.rummikub.Rummikub newGame = new rummikub.gameLogic.controller.rummikub.Rummikub();
//        newGame.startGame();
//        /**/
        ScreensController screensController = new ScreensController();
        screensController.loadScreen(MAINMENU_SCREEN_ID, MAINMENU_SCREEN_FXML);
        screensController.loadScreen(GAME_PARAMETERS_SCREEN_ID, GAME_PARAMETERS_FXML);
        screensController.loadScreen(PLAY_SCREEN_ID, PLAY_SCREEN_FXML);
        screensController.loadScreen(SAVE_GAME_SCREEN_ID, SAVE_GAME_FXML);
        screensController.loadScreen(SUBMENU_SCREEN_ID, SUBMENU_SCREEN_FXML);
        screensController.loadScreen(RESULT_SCREEN_ID, RESULT_SCREEN_FXML);

        screensController.setScreen(MAINMENU_SCREEN_ID, ScreensController.NOT_RESETABLE);
        StackPane root = new StackPane();
        root.getChildren().addAll(screensController);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
