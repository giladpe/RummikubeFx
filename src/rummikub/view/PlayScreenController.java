/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
import rummikub.gameLogic.model.gameobjects.Serie;
import rummikub.gameLogic.model.logic.PlayersMove;
import rummikub.gameLogic.model.logic.SeriesGenerator;
import rummikub.gameLogic.model.player.ComputerSingleMoveGenerator;
import rummikub.gameLogic.view.ioui.Utils;
import rummikub.view.viewObjects.AnimatedFlowPane;
import rummikub.view.viewObjects.AnimatedSeriePane;
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
    private Label errorMsg;
    @FXML
    private BorderPane board;
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
    private SeriesGenerator serieGenerator;
    private ComputerSingleMoveGenerator newMoveGenerator;
    private SimpleBooleanProperty isLegalMove ; 
    private Timeline swapTurnTimeLineDelay;
    private AnimatedFlowPane centerPane;
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
    private ArrayList<HBox> playersBar = new ArrayList<>(4);
    private ArrayList<Label> numOfTileInHand = new ArrayList<>(4);

    @FXML
    private Label heapTile;

    @FXML
    private void handleMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.SUBMENU_SCREEN_ID, ScreensController.NOT_RESETABLE);
    }

    @FXML
    private void handleEndTrunAction(ActionEvent event) {
        if (swapTurnTimeLineDelay.getStatus() == Animation.Status.STOPPED) {

            //check the player move
            rummikubLogic.playSingleTurn(currentPlayerMove);

            // Swap players
            if (rummikubLogic.isGameOver()) {
                ResultScreenController resultScreen = (ResultScreenController) this.myController.getControllerScreen(Rummikub.RESULT_SCREEN_ID);
                resultScreen.updatedGameResultMsg();
                this.myController.setScreen(Rummikub.RESULT_SCREEN_ID, ScreensController.NOT_RESETABLE);
            } else {
                swapTurnTimeLineDelay.play();
            }
        }
    }

    @FXML
    private void handleWithdrawCardAction(ActionEvent event) {

        if (swapTurnTimeLineDelay.getStatus() == Animation.Status.STOPPED) {

            this.currentPlayerMove.setIsTurnSkipped(PlayersMove.USER_WANT_SKIP_TRUN);
            this.rummikubLogic.playSingleTurn(currentPlayerMove);

            if (rummikubLogic.isGameOver() || rummikubLogic.isOnlyOnePlayerLeft()) {
                ResultScreenController resultScreen = (ResultScreenController) this.myController.getControllerScreen(Rummikub.RESULT_SCREEN_ID);
                resultScreen.updatedGameResultMsg();
                this.myController.setScreen(Rummikub.RESULT_SCREEN_ID, ScreensController.NOT_RESETABLE);
            } else {
                swapTurnTimeLineDelay.play();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.hand.setSpacing(5);
        initPlayers();
        this.centerPane = new AnimatedFlowPane();
        this.serieGenerator = new SeriesGenerator();
        this.newMoveGenerator = new ComputerSingleMoveGenerator();
        //sighn for CenterPane events
//        centerPane.addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                fdd
//            }
//        });
        this.board.setCenter(centerPane);

        this.swapTurnTimeLineDelay = new Timeline(new KeyFrame(Duration.millis(800), (ActionEvent event1) -> {
            swapTurns();
        }));

        setHandEvents();
        isLegalMove=new SimpleBooleanProperty(false);
        //not good - what about loading file?????
        //Game.Settings gameSetting = ((GameParametersController)myController.getControllerScreen(Rummikub.GAME_PARAMETERS_SCREEN_ID)).getGameSettings();
        //this.gameLogic = new Game(gameSetting);
    }

    private void setHandEvents() {

        this.handTile.setOnDragOver((event) -> {
//            Timeline animation;
//            animation = new Timeline(new KeyFrame(Duration.millis(200),(ActionEvent event1) -> { styleOfHandWhenEnter(); }));
//            animation.play();
            AnimatedTilePane currTile = (AnimatedTilePane) event.getDragboard().getContent(DataFormat.RTF);
            if (currTile.getClass() == AnimatedTilePane.class && currTile.getIsTileMovedFromHandToBoard()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                //event.acceptTransferModes(TransferMode.ANY);
            }
//            animation = new Timeline(new KeyFrame(Duration.millis(200),(ActionEvent event1) -> { styleOfHandWhenExit(); }));
//            animation.play();
            event.consume();
        });

        this.handTile.setOnDragDropped((event) -> {
            Dragboard db = event.getDragboard();
            AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);
            boolean success = false;
            if (event.getTransferMode() == TransferMode.MOVE) {
                int ySource = currTile.getIndexOfTileInSerie(currTile);
                int xSource = currTile.getSerieIndexFromTile(currTile);
                Point pSource = new Point(xSource, ySource);
                SingleMove singleMove = new SingleMove(pSource, SingleMove.MoveType.BOARD_TO_HAND);
                currTile.setSingleMove(singleMove);
                success = currTile.getIsLegalMove();
            }

            event.setDropCompleted(success);
            event.consume();
        });

        this.handTile.setOnDragDone((DragEvent event) -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
            }
            event.consume();
        });

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
        //((AnimatedFlowPane) this.board.getCenter()).resetScreen();
        resetPlayersBar();
        setPlayersBar();
        handTile.getChildren().clear();
        withdrawCard.setStyle("-fx-font-size: 24px; -fx-font-weight: bold");
        withdrawCard.setText("+");
        withdrawCard.setDisable(false);
        this.errorMsg.setText(Utils.Constants.EMPTY_STRING);
        //show();
    }

    public void resetPlayersBar() {
        for (HBox playerBar : this.playersBar) {
            playerBar.setVisible(false);
        }
    }

    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }

    private void initScreenComponentetWithoutBoard() {
        setPlayersBar();
        showPlayerHand();
        //need to debub it
        initCurrPlayerLabel();
        initAboveHeapLabel();
        initHeapButton();
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

    private void showPlayerHand() {
        createPlayerHand(this.rummikubLogic.getCurrentPlayer().getListPlayerTiles());
    }

    private void showCurrentPlayerHand() {
        createPlayerHand(this.currentPlayerMove.getHandAfterMove());
    }

    private void createPlayerHand(ArrayList<Tile> handTiles) {
        this.handTile.getChildren().clear();

        for (Tile currTile : handTiles) {
            AnimatedTilePane viewTile = new AnimatedTilePane(currTile, !(AnimatedTilePane.TILE_BELONG_TO_BOARD));
            
            viewTile.addSingleMoveListener((ObservableValue<? extends SingleMove> observable, SingleMove oldValue, SingleMove newValue) -> {
                makeSingleMove(newValue);
            });
            
            this.isLegalMove.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                viewTile.onSingleMoveDone(newValue);
            });
            
            viewTile.addIsMoveSuccesfulyCompletedListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                onSuccesfulyCompletedMove(newValue);
            });
            this.handTile.getChildren().add(viewTile);
        }
    }

    private void makeSingleMove(SingleMove singleMove) {
        boolean isLegal = dealWithSingleMoveResualt(singleMove);
        this.isLegalMove.set(isLegal);
    }
    
    private void onSuccesfulyCompletedMove(boolean newVal) {
        if (newVal) {
//            Platform.runLater(() -> {
//                initScreenComponentetWithoutBoard();
//                showCurrentGameBoardAndCurrentPlayerHand();
//            });

        //initScreenComponentetWithoutBoard();
        initCurrPlayerLabel();
        this.isLegalMove.set(false);
        showCurrentGameBoardAndCurrentPlayerHand();  
        } 
    }

    
    public void showGameBoardAndPlayerHand() {
        initScreenComponentetWithoutBoard();
        showGameBoard();
    }

    public void showGameBoard() {
        setBoard(this.rummikubLogic.getGameBoard().getListOfSerie());
    }

    public void showCurrentPlayerBoard() {
        setBoard(this.currentPlayerMove.getBoardAfterMove().getListOfSerie());
    }

    private void setBoard(ArrayList<Serie> serieList) {
        centerPane.resetScreen();
        ArrayList<FlowPane> flowPaneSeriesList = new ArrayList<>();
        for (Serie serie : serieList) {
            flowPaneSeriesList.add(createFlowPaneSerie(serie));
        }
        centerPane.getChildren().addAll(flowPaneSeriesList);
    }

    private FlowPane createFlowPaneSerie(Serie serie) {
        FlowPane serieFlowPan = new AnimatedSeriePane();
        serieFlowPan.getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
            if (serieFlowPan.getChildren().isEmpty()) {
                centerPane.removeEmptySerie(serieFlowPan);
            } else {
                ((AnimatedSeriePane) serieFlowPan).setSize();
            }
        });
        
        for (Tile tile : serie.getSerieOfTiles()) {
            AnimatedTilePane viewTile = new AnimatedTilePane(tile, AnimatedTilePane.TILE_BELONG_TO_BOARD);
            viewTile.addSingleMoveListener((ObservableValue<? extends SingleMove> observable, SingleMove oldValue, SingleMove newValue) -> {
                makeSingleMove(newValue);
            });
            
            this.isLegalMove.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                viewTile.onSingleMoveDone(newValue);
            });
            
            viewTile.addIsMoveSuccesfulyCompletedListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                onSuccesfulyCompletedMove(newValue);
            });
            serieFlowPan.getChildren().add(viewTile);
        }
        serieFlowPan.setMinWidth(serieFlowPan.getChildren().size() * 30);
        return serieFlowPan;
    }    
    private void updateHand() {
        showCurrentPlayerHand();
        updateCurrPlayerBar();
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

    public void swapTurns() {
        rummikubLogic.swapTurns();
        //this.handTile.getChildren().clear();
        //this.showPlayerHand(this.rummikubLogic.getCurrentPlayer());
        initCurrentPlayerMove();
        showGameBoardAndPlayerHand();
        initCurrPlayerLabel();
    }

    private void initCurrPlayerLabel() {
        int index = 0;

        for (HBox barPlayer : playersBar) {
            for (Node child : barPlayer.getChildren()) {
                child.setStyle(styleWhite);
            }
        }

        for (Player player : rummikubLogic.getPlayers()) {
            this.numOfTileInHand.get(index).setText(String.valueOf(player.getListPlayerTiles().size()));
            index++;
        }
        updateCurrPlayerBar();
    }

    private void initAboveHeapLabel() {
        this.heapTile.setStyle(styleWhite);
        this.heapTile.setText("Tile Left:" + rummikubLogic.getHeap().getTileList().size());
    }

    private void initHeapButton(/*ActionEvent event*/) {

        if (rummikubLogic.getHeap().isEmptyHeap()) {
            this.withdrawCard.setFont(new Font(14));
            this.withdrawCard.setText("Empy Deck");
            this.withdrawCard.setDisable(true);
            //if the code above maks problems then need to use this one and pass the event to here
        }
    }

    private void setPlayersBar() {
        int i = 0;

        this.playersBar.stream().forEach((playersLabelBar) -> {
            playersLabelBar.setVisible(false);
        });

        for (Player player : this.rummikubLogic.getPlayers()) {
            setLabel(player, this.rummikubLogic.getPlayers().indexOf(player));
            this.playersBar.get(i).setVisible(true);
            i++;
        }

    }

//    
    //test
    // Deals with the basic inputs from the user about the game board and his hand
//    private Utils.TurnMenuResult getMoveFromPlayer(PlayersMove currentPlayerMove, Player printablePlayer, ArrayList<Player> printablePlayersList) {
//        Utils.TurnMenuResult turnResult = null;
//        SingleMove singleMove;
//        boolean keepPlaying, isTurnSkipped = false, isFirstMoveForPlayerInCurrTurn = true;
//        
//        do{
//            if (rummikubLogic.getCurrentPlayer().getIsHuman()) {
//                if(isFirstMoveForPlayerInCurrTurn){
//                    turnResult = InputOutputParser.askTurnMenuWithSave();
//                    isFirstMoveForPlayerInCurrTurn = false;
//                }
//                else {
//                    turnResult = InputOutputParser.askTurnMenuWithoutSave();
//                }
//                singleMove = dealWithHumanPlayer(turnResult, currentPlayerMove, isTurnSkipped);
//            }
//            else {  
//                singleMove = dealWithComputerPlayer(currentPlayerMove, isTurnSkipped); 
//                turnResult = Utils.TurnMenuResult.CONTINUE;
//            }
//            keepPlaying = turnResult != Utils.TurnMenuResult.EXIT_GAME && !currentPlayerMove.getIsTurnSkipped();
//            if(keepPlaying){
//                if(singleMove != null) {
//                    try {
//                       dealWithSingleMoveResualt(turnResult,singleMove,currentPlayerMove);        
//                       InputOutputParser.printGameScreen(printablePlayer, currentPlayerMove.getBoardAfterMove(), printablePlayersList);
//                    }
//                    catch (Exception ex) {
//                         currentPlayerMove.setIsTurnSkipped(PlayersMove.USER_WANT_SKIP_TRUN);
//                    }
//                }
//            }
//            keepPlaying = keepPlaying && isPlayerWantsToContinueHisTurn(); 
//        }while (keepPlaying); 
//        
//        return turnResult;
//    }
    private boolean dealWithSingleMoveResualt(/*Utils.TurnMenuResult turnResult,*/SingleMove singleMove) {
        SingleMove.SingleMoveResult singleMoveResualt;
        //if (turnResult == Utils.TurnMenuResult.CONTINUE) {
        singleMoveResualt = this.currentPlayerMove.implementSingleMove(singleMove);
        boolean isLegalMove = false;
        switch (singleMoveResualt) {
            case TILE_NOT_BELONG_HAND: {
                //show message on the scene
                this.errorMsg.setText(Utils.Constants.ErrorMessages.ILEGAL_TILE_IS_NOT_BELONG_TO_HAND);
                //InputOutputParser.printTileNotBelongToTheHand();
                break;
            }
            case NOT_IN_THE_RIGHT_ORDER: {
                //show message on the scene
                this.errorMsg.setText(Utils.Constants.ErrorMessages.ILEGAL_TILE_INSERTED_NOT_IN_RIGHT_ORDER);
                //InputOutputParser.printTileInsertedNotInRightOrder();
                break;
            }
            case CAN_NOT_TOUCH_BOARD_IN_FIRST_MOVE: {
                //show message on the scene
                this.errorMsg.setText(Utils.Constants.ErrorMessages.ILEGAL_CANT_TUCH_BOARD_IN_FIRST_MOVE);
                //InputOutputParser.printCantTuchBoardInFirstMove();
                break;
            }
            case LEGAL_MOVE:
            default:
                this.errorMsg.setText(Utils.Constants.QuestionsAndMessagesToUser.SUCCSESSFUL_MOVE);
                isLegalMove = true;
                break;

        }
        return isLegalMove;
    }

//        // Deals with the computer player and allows him to makes his inputs
    private SingleMove dealWithComputerPlayer(PlayersMove currentPlayerMove, boolean isTurnSkipped) {
        SingleMove singleMove;
        Serie serie;

        if (newMoveGenerator.isFinishedGeneratingLastSerie()) {
            serie = this.serieGenerator.generateSerieMove(currentPlayerMove.getHandAfterMove(), isTurnSkipped);
            this.newMoveGenerator.setSerieToPlaceOnBoard(serie);

            if (serie != null) {
                this.newMoveGenerator.setBoardSizeBeforeMove(currentPlayerMove.getBoardAfterMove().boardSize());
            }
        }

        singleMove = newMoveGenerator.generateSingleMove(currentPlayerMove.getHandAfterMove(), currentPlayerMove.getBoardAfterMove());

        if (this.newMoveGenerator.isTurnSkipped()) {
            currentPlayerMove.setIsTurnSkipped(this.newMoveGenerator.isTurnSkipped());
        }

        return singleMove;
    }

    private void ImplementComputerPlayerTurn(SingleMove singleMove) {
        if (singleMove != null) {
            try {
                dealWithSingleMoveResualt(singleMove);
            } catch (Exception ex) {
                currentPlayerMove.setIsTurnSkipped(PlayersMove.USER_WANT_SKIP_TRUN);
            }
        }
    }

    private void updateCurrPlayerBar() {
        int index = rummikubLogic.getPlayers().indexOf(rummikubLogic.getCurrentPlayer());
        for (Node child : this.playersBar.get(index).getChildren()) {
            child.setStyle(styleBlue);
            this.numOfTileInHand.get(index).setText(String.valueOf(this.currentPlayerMove.getHandAfterMove().size()));

        }
    }

    void showCurrentGameBoardAndCurrentPlayerHand() {
        showCurrentPlayerBoard();
        showCurrentPlayerHand();
    }
}
