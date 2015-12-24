/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import rummikub.gameLogic.model.gameobjects.Tile;
import rummikub.gameLogic.model.logic.GameLogic;
import rummikub.gameLogic.model.logic.Settings;
import rummikub.gameLogic.model.player.Player;
import rummikub.Rummikub;
import rummikub.gameLogic.controller.rummikub.SingleMove;
import rummikub.gameLogic.model.gameobjects.Board;
import rummikub.gameLogic.model.logic.PlayersMove;
import rummikub.gameLogic.view.ioui.InputOutputParser;
import rummikub.gameLogic.view.ioui.Utils;
import rummikub.view.viewObjects.AnimatedFlowPane;
import rummikub.view.viewObjects.AnimatedTilePane;

/**
 * FXML Controller class
 *
 * @author Arthur
 */
public class PlayScreenController implements Initializable, ResetableScreen, ControlledScreen {

    private static final String styleWhite = "-fx-text-fill: white";
    private static final String styleBlue = "-fx-text-fill: blue";
    @FXML
    BorderPane board;
    @FXML
    private Button menu;
    @FXML
    private FlowPane handTile;
    @FXML
    private Button endTrun;
    @FXML
    private Button withdrawCard;

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
    private GameLogic rummikubLogic = new GameLogic();
    private Timeline withdrawCardWithDelay;

    private PlayersMove currentPlayerMove;
    @FXML
    private HBox barPlayer1;
    @FXML
    private Label numTileP1;
    @FXML
    private HBox barPlayer3;
    @FXML
    private Label numTileP3;
    @FXML
    private HBox barPlayer2;
    @FXML
    private Label numTileP2;
    @FXML
    private HBox barPlayer4;
    @FXML
    private Label numTileP4;
    private ArrayList<HBox>playersBar=new ArrayList<>(4);
   private ArrayList<Label>numOfTileInHand=new ArrayList<>(4);
   
    @FXML
    private Label heapTile;
    @FXML
    private void handleMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.SUBMENU_SCREEN_ID, ScreensController.NOT_RESETABLE);
    }

    @FXML
    private void handleEndTrunAction(ActionEvent event) {
    }

    @FXML
    private void handleWithdrawCardAction(ActionEvent event) {

        if(withdrawCardWithDelay.getStatus() == Animation.Status.STOPPED) {

            ///when loading file error in this line !!!
            this.currentPlayerMove.setIsTurnSkipped(PlayersMove.USER_WANT_SKIP_TRUN);
            this.rummikubLogic.playSingleTurn(currentPlayerMove);

            //this.handTile.getChildren().clear();
            this.handTile.getChildren().clear();
            show();
    //        try {
    //            Thread.sleep(1000);
    //        } catch (InterruptedException ex) {}
            initHeapLabel();
            if (rummikubLogic.getHeap().isEmptyHeap()) {
                ((Button) event.getSource()).setFont(new Font(14));
                ((Button) event.getSource()).setText("Empy Deck");
                ((Button) event.getSource()).setDisable(true);
            }

            if (rummikubLogic.isGameOver() || rummikubLogic.isOnlyOnePlayerLeft()) {
                //it means the game is over..... what we do now
            }

            withdrawCardWithDelay.play();
            //swapTurns();
            //this.handFirstRow.getChildren().add(new AnimatedTile(playerHand.get(playerHand.size()-1)));

    //        ArrayList<Tile> playerHand=this.rummikubLogic.getCurrentPlayer().getListPlayerTiles();
    //        if (this.gameLogic.withdrawCard()) {
    //            playerHand = this.gameLogic.getCurrPlayer().getHand();
    //            this.handFirstRow.getChildren().add(new AnimatedTile(playerHand.get(playerHand.size()-1)));
    //        }
        
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.hand.setSpacing(5);
        initPlayers();
        AnimatedFlowPane centerPane = new AnimatedFlowPane();
        //sighn for CenterPane events
        this.board.setCenter(centerPane);
     
        this.withdrawCardWithDelay = new Timeline(new KeyFrame(Duration.millis(800), (ActionEvent event1) -> { swapTurns(); }));
   
        setHandEvents();
        //not good - what about loading file?????
        //Game.Settings gameSetting = ((GameParametersController)myController.getControllerScreen(Rummikub.GAME_PARAMETERS_SCREEN_ID)).getGameSettings();
        //this.gameLogic = new Game(gameSetting);
    }
    
    private void setHandEvents() {
//        this.handTile.setOnMouseEntered((MouseEvent event) -> {
//            this.handTile.setStyle("-fx-border-color: blue; -fx-border-width: 0.5");
//        });
//        
//        this.handTile.setOnMouseExited((MouseEvent event) -> {
//            this.handTile.setStyle("-fx-border-color: none; -fx-border-width: 0");
//        });
        
        
//        this.handTile.setOnDragDetected(null);
//        this.handTile.setOnDragDone(null);
//        this.handTile.setOnDragDropped(null);
//        this.handTile.setOnDragEntered(null);
//        this.handTile.setOnDragExited(null);
//        this.handTile.setOnDragOver(null);

                
        this.handTile.setOnDragOver((event) -> {
//            Timeline animation;
//            animation = new Timeline(new KeyFrame(Duration.millis(200),(ActionEvent event1) -> { styleOfHandWhenEnter(); }));
//            animation.play();
            AnimatedTilePane currTile = (AnimatedTilePane)event.getDragboard().getContent(DataFormat.RTF);
            if (currTile.getClass() == AnimatedTilePane.class && currTile.getIsTileMovedToBoard() ) {
                event.acceptTransferModes(TransferMode.ANY);
            }
//            animation = new Timeline(new KeyFrame(Duration.millis(200),(ActionEvent event1) -> { styleOfHandWhenExit(); }));
//            animation.play();
            event.consume();
        });
        
        
        
        this.handTile.setOnDragDropped((event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (event.getTransferMode() == TransferMode.MOVE) {
                //need to make it work in future
//                int nRowLoc,nColLoc;
//                // get somehow the index
//                nColLoc = 0;
//                nRowLoc = 0;
//                Point pointToTakeFromBoard = new Point(nRowLoc, nColLoc);
//                SingleMove singleMove = new SingleMove(pointToTakeFromBoard, SingleMove.MoveType.BOARD_TO_HAND);
//                dealWithSingleMoveResualt(singleMove);

                success = true;
            }
            
            event.setDropCompleted(success);
            event.consume();
        }); 
        
        this.handTile.setOnDragDone((DragEvent event) -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
            }
            event.consume();
        });
        
//        this.setOnDragDetected((event) -> {
//            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
//            WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
//
//            ClipboardContent content = new ClipboardContent();  
//            content.put(DataFormat.RTF, this);
//            
//            //content.put(DataFormat.RTF, tile);
//            // content.putString(number + "");
//            db.setContent(content);
//            db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);
//            event.consume();
//        });

    }

//    private void styleOfHandWhenEnter() {
//        this.handTile.setStyle("-fx-border-color: blue; -fx-border-width: 0.5");
//    }
//    
//    private void styleOfHandWhenExit() {
//        this.handTile.setStyle("-fx-border-color: none; -fx-border-width: 0");
//    }
    
    private void updateHand() {
        this.handTile.getChildren().clear();
        showPlayerPlayingHand(currentPlayerMove.getHandAfterMove());
    }

    private void initPlayers() {
        this.playersBar.add(barPlayer1);
        this.playersBar.add(barPlayer2);
        this.playersBar.add(barPlayer3);
        this.playersBar.add(barPlayer4);
        this.playersLabels.add(player1);
        this.playersLabels.add(player2);
        this.playersLabels.add(player3);
        this.playersLabels.add(player4);
        this.numOfTileInHand.add(numTileP1);
        this.numOfTileInHand.add(numTileP2);
        this.numOfTileInHand.add(numTileP3);
        this.numOfTileInHand.add(numTileP4);
    }

    public void createNewGame(Settings gameSetting) {
        this.rummikubLogic.setGameSettings(gameSetting);
        this.rummikubLogic.setGameOriginalInputedSettings(rummikubLogic.getGameSettings());
        this.rummikubLogic.initGameFromUserSettings();
        initCurrentPlayerMove();
    }

    //TODO:
    @Override
    public void resetScreen() {
        ((AnimatedFlowPane)this.board.getCenter()).resetScreen();
        resetPlayersBar();
        setPlayersBar();
        handTile.getChildren().clear();
        withdrawCard.setStyle("-fx-font-size: 24px; -fx-font-weight: bold");
        withdrawCard.setText("+");
        withdrawCard.setDisable(false);
        show();
    }
    public void resetPlayersBar() {
        for (HBox playerBar: this.playersBar) {
            playerBar.setVisible(false);
        }
}
    
    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }

    
    public void show() {
        setPlayersBar();
        showPlayerHand(this.rummikubLogic.getCurrentPlayer());
        initCurrPlayerLabel();
        initHeapLabel();
        ///int currPlayerIndex=this.rummikubLogic.getPlayers().indexOf(this.rummikubLogic.getCurrentPlayer());
    }

    private void setLabel(Player player, int index) {
        Label currentPlayer = this.playersLabels.get(index);
        playersBar.get(index).setVisible(true);
        currentPlayer.setVisible(true);
        currentPlayer.setText(" " + player.getName() + "  ");
        
        currentPlayer.setAlignment(Pos.CENTER);
        currentPlayer.setTextAlignment(TextAlignment.JUSTIFY);

        if (player.getIsHuman()) {
            currentPlayer.setGraphic(ImageUtils.getImageView(ImageUtils.HUMAN_PLAYER_LOGO));
        } else {
            currentPlayer.setGraphic(ImageUtils.getImageView(ImageUtils.COMPUTER_PLAYER_LOGO));
        }
    }

    private void showPlayerHand(Player player) {
        createPlayerHand(player.getListPlayerTiles());
    }
    
    private void showPlayerPlayingHand(ArrayList<Tile> handTiles) {
        createPlayerHand(handTiles);
    }
    
    private void createPlayerHand(ArrayList<Tile> handTiles) {
        for (Tile currTile : handTiles) {
            AnimatedTilePane viewTile = new AnimatedTilePane(currTile);
            viewTile.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(newValue) {
                    removeTileFromHand(viewTile);
                }
                else {
                    updateHand();
                }
            });
            
            this.handTile.getChildren().add(viewTile);
        }
    }
    
    
    private void removeTileFromHand(AnimatedTilePane CurrTile) {
        //need to remove it for currplayermove hand here or somwhere else
        this.handTile.getChildren().remove(CurrTile);
    }

    public void initCurrentPlayerMove() {

        //init variables in the statrt of the turn
        Board printableBoard = new Board(new ArrayList<>(rummikubLogic.getGameBoard().getListOfSerie()));
        boolean isFirstMoveDone = rummikubLogic.getCurrentPlayer().isFirstMoveDone();
        Player printablePlayer = rummikubLogic.getCurrentPlayer().clonePlayer();
        this.currentPlayerMove = new PlayersMove(printablePlayer.getListPlayerTiles(), printableBoard, isFirstMoveDone);

        //need it for java fx?
        //ArrayList<Player> printablePlayersList = new ArrayList<>(rummikubLogic.getPlayers());
        //printablePlayersList.remove(rummikubLogic.getCurrentPlayer());
        //printablePlayersList.add(printablePlayer);
    }

    public GameLogic getRummikubLogic() {
        return rummikubLogic;
    }

    public void setRummikubLogic(GameLogic rummikubLogic) {
        this.rummikubLogic = rummikubLogic;
    }

    private void swapTurns() {
        rummikubLogic.swapTurns();
        this.handTile.getChildren().clear();
        this.showPlayerHand(this.rummikubLogic.getCurrentPlayer());
        initCurrPlayerLabel();
    }

    private void initCurrPlayerLabel() {
        int index=0;

        for (HBox barPlayer : playersBar) {
            for (Node child : barPlayer.getChildren()) {
                child.setStyle(styleWhite);
            }
        }
        
        for (Player player : rummikubLogic.getPlayers()) {            
            this.numOfTileInHand.get(index).setText(String.valueOf(player.getListPlayerTiles().size()));
            index++;
        }
//        for (Label playersLabel : playersLabels) {
//            playersLabel.setStyle(styleWhite);
//        }
        
        index = rummikubLogic.getPlayers().indexOf(rummikubLogic.getCurrentPlayer());
        for (Node child :  this.playersBar.get(index).getChildren()) {
                child.setStyle(styleBlue);
            }
    }
    
    
    private void initHeapLabel() {
       this.heapTile.setStyle(styleWhite);
       this.heapTile.setText("Tile Left:"+rummikubLogic.getHeap().getTileList().size());
    }

    private void setPlayersBar() {
       this.rummikubLogic.getPlayers().stream().forEach((player) -> {
            setLabel(player, this.rummikubLogic.getPlayers().indexOf(player));
        });
    }
    
    
    
    
    
    //test
    private void dealWithSingleMoveResualt(/*Utils.TurnMenuResult turnResult,*/ SingleMove singleMove) {
        SingleMove.SingleMoveResult singleMoveResualt;

        //if (turnResult == Utils.TurnMenuResult.CONTINUE) {

            singleMoveResualt = this.currentPlayerMove.implementSingleMove(singleMove);
            
            switch(singleMoveResualt) {
                case TILE_NOT_BELONG_HAND:{
                    //show message on the scene
                    //InputOutputParser.printTileNotBelongToTheHand();
                    break;
                }
                case NOT_IN_THE_RIGHT_ORDER:{
                    //show message on the scene
                    //InputOutputParser.printTileInsertedNotInRightOrder();
                    break;
                }
                case CAN_NOT_TOUCH_BOARD_IN_FIRST_MOVE:{
                    //show message on the scene
                    //InputOutputParser.printCantTuchBoardInFirstMove();
                    break;
                }
                case LEGAL_MOVE:
                default:{
                    break;
                }
            }                    
        //}
    }
}
