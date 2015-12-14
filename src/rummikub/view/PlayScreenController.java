/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import rummikub.Engine.Game;
import rummikub.Engine.Player.Player;
import rummikub.Engine.TilesLogic.Tile;
import rummikub.Rummikub;
import rummikub.view.viewObjects.AnimatedTile;

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

    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;
    
    private ArrayList<Label> playersLabels = new ArrayList<>(4);
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
        this.handFirstRow.setSpacing(5);
        initPlayers();

        
        //not good - what about loading file?????
        //Game.Settings gameSetting = ((GameParametersController)myController.getControllerScreen(Rummikub.GAME_PARAMETERS_SCREEN_ID)).getGameSettings();
        //this.gameLogic = new Game(gameSetting);
    }    
    
    private void initPlayers() {
        this.playersLabels.add(player1);
        this.playersLabels.add(player2);
        this.playersLabels.add(player3);
        this.playersLabels.add(player4);
    }
    
    
    public void createNewGame(Game.Settings gameSetting) {
        this.gameLogic = new Game(gameSetting);
        this.gameLogic.setPlayersFromSettings();
        this.gameLogic.addStartTilesToPlayers();
    }

    //TODO:
    @Override
    public void resetScreen() {
    }

    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }

    public void show() {
        this.gameLogic.getPlayers().stream().forEach((player) -> {
            setLabel(player, this.gameLogic.getPlayers().indexOf(player));
        });
        showPlayerHand(this.gameLogic.getCurrPlayer());
    }

    private void setLabel(Player player, int index) {
        Label currentPlayer = this.playersLabels.get(index);
        currentPlayer.setText(player.getName());
        currentPlayer.setVisible(true);
        currentPlayer.setAlignment(Pos.BOTTOM_CENTER);
        currentPlayer.setTextAlignment(TextAlignment.JUSTIFY);
        
        if(player.isPlayerHuman()) {
            currentPlayer.setGraphic(ImageUtils.getImageView(ImageUtils.HUMAN_PLAYER_LOGO));
        }
        else {
            currentPlayer.setGraphic(ImageUtils.getImageView(ImageUtils.COMPUTER_PLAYER_LOGO));
        }
    }

    private void showPlayerHand(Player player) {
        for (Tile currTile : player.getHand()) {
            this.handFirstRow.getChildren().add(new AnimatedTile(currTile));
        }
    }
}
