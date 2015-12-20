/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import rummikub.gameLogic.model.gameobjects.Tile;
import rummikub.gameLogic.model.logic.GameLogic;
import rummikub.gameLogic.model.logic.Settings;
import rummikub.gameLogic.model.player.Player;
import rummikub.Rummikub;
import rummikub.gameLogic.model.gameobjects.Board;
import rummikub.gameLogic.model.logic.PlayersMove;
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
    private Button orgenaizeHand;
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

    private PlayersMove currentPlayerMove;

    @FXML
    private void handleMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.SUBMENU_SCREEN_ID, ScreensController.NOT_RESETABLE);
    }

    @FXML
    private void handleEndTrunAction(ActionEvent event) {
    }

    @FXML
    private void handleWithdrawCardAction(ActionEvent event) {

        ///when loading file error in this line !!!
        this.currentPlayerMove.setIsTurnSkipped(PlayersMove.USER_WANT_SKIP_TRUN);
        this.rummikubLogic.playSingleTurn(currentPlayerMove);

        //this.handTile.getChildren().clear();
        this.handTile.getChildren().remove(0, this.handTile.getChildren().size());

        this.showPlayerHand(this.rummikubLogic.getCurrentPlayer());
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(PlayScreenController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        if (rummikubLogic.getHeap().isEmptyHeap()) {
            ((Button) event.getSource()).setFont(new Font(14));
            ((Button) event.getSource()).setText("Empy Deck");
            ((Button) event.getSource()).setDisable(true);
        }

        if (rummikubLogic.isGameOver() || rummikubLogic.isOnlyOnePlayerLeft()) {
            //it means the game is over..... what we do now
        }
        swapTurns();
        //this.handFirstRow.getChildren().add(new AnimatedTile(playerHand.get(playerHand.size()-1)));

//        ArrayList<Tile> playerHand=this.rummikubLogic.getCurrentPlayer().getListPlayerTiles();
//        if (this.gameLogic.withdrawCard()) {
//            playerHand = this.gameLogic.getCurrPlayer().getHand();
//            this.handFirstRow.getChildren().add(new AnimatedTile(playerHand.get(playerHand.size()-1)));
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.hand.setSpacing(5);
        initPlayers();
        board.setCenter(createGridPane());

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

    public void createNewGame(Settings gameSetting) {
        this.rummikubLogic.setGameSettings(gameSetting);
        this.rummikubLogic.setGameOriginalInputedSettings(rummikubLogic.getGameSettings());
        this.rummikubLogic.initGameFromUserSettings();
        initCurrentPlayerMove();
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
        this.rummikubLogic.getPlayers().stream().forEach((player) -> {
            setLabel(player, this.rummikubLogic.getPlayers().indexOf(player));
        });
        showPlayerHand(this.rummikubLogic.getCurrentPlayer());
        initCurrPlayerLabel();
        ///int currPlayerIndex=this.rummikubLogic.getPlayers().indexOf(this.rummikubLogic.getCurrentPlayer());

    }

    private void setLabel(Player player, int index) {
        Label currentPlayer = this.playersLabels.get(index);
        currentPlayer.setText(" " + player.getName() + "  ");
        currentPlayer.setVisible(true);
        currentPlayer.setAlignment(Pos.CENTER);
        currentPlayer.setTextAlignment(TextAlignment.JUSTIFY);

        if (player.getIsHuman()) {
            currentPlayer.setGraphic(ImageUtils.getImageView(ImageUtils.HUMAN_PLAYER_LOGO));
        } else {
            currentPlayer.setGraphic(ImageUtils.getImageView(ImageUtils.COMPUTER_PLAYER_LOGO));
        }
    }

    private void showPlayerHand(Player player) {
//        Task task;
//        task = new Task<Void>() {
//            @Override
//            public Void call() throws Exception {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        foo(player);
//                    }
//                });
//                Thread.sleep(1000);
//                
//            }
//        };

        for (Tile currTile : player.getListPlayerTiles()) {
            this.handTile.getChildren().add(new AnimatedTilePane(currTile));
        }
    }

    private void foo(Player player) {
        for (Tile currTile : player.getListPlayerTiles()) {
            this.handTile.getChildren().add(new AnimatedTilePane(currTile));
        }
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
        int index;

        for (Label playersLabel : playersLabels) {
            playersLabel.setStyle(styleWhite);
        }
        index = rummikubLogic.getPlayers().indexOf(rummikubLogic.getCurrentPlayer());
        this.playersLabels.get(index).setStyle(styleBlue);
    }
    
    
    //TEST
        private static final int BOARD_SIZE = 5;
        
        private Node createGridPane() {
            GridPane pane = new GridPane();
            pane.setHgap(2);
            pane.setVgap(2);
            pane.setAlignment(Pos.CENTER);
            pane.setPadding(new Insets(25));

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    pane.add(createCell(), i, j);
                }
            }

        return pane;
    }

    private Node createCell() {
        final Label cell = new Label();
        cell.setPrefSize(25, 25);
        cell.setAlignment(Pos.CENTER);
        cell.setStyle("-fx-border-color: gray; -fx-border-width: 1");

        cell.setOnDragOver((event) -> {
            //if (event.getDragboard().hasString()) {
              event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            //}
            event.consume();
        });

        cell.setOnDragEntered((event) -> {
            //if (event.getDragboard().hasString()) {
              cell.setStyle("-fx-background-color: green");
            //}
            event.consume();
        });

        cell.setOnDragExited((event) -> {
            cell.setStyle("-fx-background-color: none");
            cell.setStyle("-fx-border-color: gray; -fx-border-width: 1");
            event.consume();
        });

        cell.setOnDragDropped((event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                cell.setBackground(new Background(new BackgroundImage(db.getDragView(), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
              //cell.setText(db.getString());
              success = true;
              
            }
            event.setDropCompleted(success);
            event.consume();
        });

        return cell;
      }
}
