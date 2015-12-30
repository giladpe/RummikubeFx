/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import java.awt.MouseInfo;
import java.awt.Point;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import rummikub.gameLogic.controller.rummikub.SingleMove;
import rummikub.gameLogic.model.gameobjects.Tile;

/**
 *
 * @author giladPe
 */
public class AnimatedTilePane extends HBox {

    public boolean getIsLegalMove() {
        return isLegalMove;
    }
    public static final boolean TILE_BELONG_TO_BOARD = true;
    public static final double TILE_WIDTH = 30;
    public static final double TILE_SPACING = 1.5;
    public static final double TILE_TOTAL_WIDTH = TILE_SPACING + TILE_WIDTH;
    private Label tileLabel;
    private boolean isLegalMove;
    private SimpleObjectProperty<SingleMove> singleMove;
    private SimpleBooleanProperty isMoveSuccesfulyCompleted;

    public AnimatedTilePane(Tile currTile) {
        super();
        initTile(currTile);
    }

//
//    private void onDragEnter(DragEvent event) {
//        tileLabel.setScaleX(1.3);                
//        tileLabel.setScaleY(1.3);
//
//        if (timeline.getStatus() == Animation.Status.RUNNING) {
//            timeline.stop();
//        }
//        double targetWidth = Double.parseDouble(event.getDragboard().getString()) + 2;
//        growNode(targetWidth);
//        event.consume();
//    }
//
//    private void onDragLeave(DragEvent event) {
//        tileLabel.setScaleX(1);
//        tileLabel.setScaleY(1);
//        if (timeline.getStatus() == Animation.Status.RUNNING) {
//            timeline.stop();
//        }
//        shrinkNode();
//        event.consume();
//    }
//
//    private void growNode(double targetWidth) {
//        timeline.getKeyFrames().clear();
//
//        if (originalWidth == null) {
//            originalWidth = new KeyValue(this.widthProperty(), getWidth());
//        }
//        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, originalWidth);
//
//        KeyFrame toKeyFrame = new KeyFrame(duration., new KeyValue(this.w, targetWidth));
//        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
//        timeline.play();
//    }
//
//    private void shrinkNode() {
//        timeline.getKeyFrames().clear();
//
//        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(widthProperty(), getWidth()));
//        KeyFrame toKeyFrame = new KeyFrame(duration, originalWidth);
//        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
//        timeline.play();
//    }
//
    private void setTileEvents() {

        //Events when mouse enter and exit the tile:
        this.setOnMouseEntered(this::onMouseEntered);
        this.setOnMouseExited(this::onMouseExited);

        //Events when tile is involved in drag and drop:
        //this.setOnDragEntered(this::onDragEnter);
        //this.setOnDragExited(this::onDragLeave);
        this.setOnDragOver(this::onDragOver);
        this.setOnDragDetected(this::onDragDetected);
        this.setOnDragDropped(this::onDragDropped);
        this.setOnDragDone(this::OnDragDone);
    }

    private void onMouseEntered(MouseEvent event) {
        tileLabel.setScaleX(1.3);
        tileLabel.setScaleY(1.3);
    }

    private void onMouseExited(MouseEvent event) {
        tileLabel.setScaleX(1);
        tileLabel.setScaleY(1);
    }

    private void onDragOver(DragEvent event) {
        if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private void onDragDetected(MouseEvent event) {
        Dragboard db = this.startDragAndDrop(TransferMode.ANY);
        WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
        ClipboardContent content = new ClipboardContent();
        content.put(DataFormat.RTF, this);
        db.setContent(content);
        db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);
        event.consume();
    }

    private void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class;
        boolean isDroppedOnSerie = this.isTileParentIsSerie();
        int yTarget, xTarget, ySource , xSource= 0;
        Point pSource, pTarget;
        SingleMove singleMove;
        FlowPane holdingSerie = ((FlowPane) this.getParent());
        AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);
        //source currTile Parent target this.parent

        success = isDroppedOnSerie && success && event.getGestureSource() != event.getGestureTarget();
        if (success) {
            //if (isDroppedOnSerie) {
            yTarget = checkIfToAddTileBeforeOrAfterTheDroppedOnTile(holdingSerie.getChildren().indexOf(this), holdingSerie);
            xTarget = getSerieIndexFromTile(this);
            pTarget = new Point(xTarget, yTarget);
            //if (currTile.getParent().getClass() == AnimatedSeriePane.class) {
            if (currTile.isTileParentIsSerie()) {
                ///board to board
                ySource = getIndexOfTileInSerie(currTile);
                xSource = getSerieIndexFromTile(currTile);
                pSource = new Point(xSource, ySource);
                
               // currTile.sourceLocation.setLocation(pSource);
               // currTile.targetLocation.setLocation(pTarget);
               // currTile.move = SingleMove.MoveType.BOARD_TO_BOARD;
                //currTile.isTileMovedFromBoardToBoard.set(BOARD_TO_BOARD);
                currTile.singleMove.set(currTile.getSingleMove(pSource,pTarget,SingleMove.MoveType.BOARD_TO_BOARD));
            } else {
                //hand to board
                ySource = getIndexOfTileInHand(currTile);
                pSource = new Point(xSource, ySource);
                //currTile.sourceLocation.setLocation(pSource);
                //currTile.targetLocation.setLocation(pTarget);
                //currTile.move = SingleMove.MoveType.HAND_TO_BOARD;
                //this.isTileMovedFromHandToBoard.set(TILE_MOVED_TO_BOARD);
                //this.isTileMovedFromBoardToBoard.set(!BOARD_TO_BOARD);
                currTile.singleMove.set(currTile.getSingleMove(pSource,pTarget,SingleMove.MoveType.HAND_TO_BOARD));
            }
            success=currTile.isLegalMove;
        }
        event.setDropCompleted(success);
        event.consume();
    }
    private void OnDragDone(DragEvent event) {
        //Dragboard db = event.getDragboard();
        //AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);
        if (event.getTransferMode() == TransferMode.MOVE) {
            this.isMoveSuccesfulyCompleted.set(true);
        }
        else {
            this.isMoveSuccesfulyCompleted.set(false);
        }
        event.consume();
    }
   
    private void initTile(Tile currTile) {
        getStyleClass().add("tile");
        String style = "-fx-text-fill: " + currTile.getTileColor().getAnsiColor();
        setMinSize(30, 40);
        setMaxSize(30, 40);
        tileLabel = new Label();
        tileLabel.getStyleClass().add("tileText");
        tileLabel.setText(currTile.getEnumTileNumber().toString());
        tileLabel.setStyle(style);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(2, 0, 0, 0));
        setTileEvents();
        getChildren().add(tileLabel);

        //A
        this.singleMove=new SimpleObjectProperty<>();
        this.isLegalMove=false;
        this.isMoveSuccesfulyCompleted = new SimpleBooleanProperty(false);
    }

    public void addSingleMoveListener(ChangeListener<SingleMove> newListener) {
        this.singleMove.addListener(newListener);
    }
    
    public void addIsMoveSuccesfulyCompletedListener(ChangeListener<Boolean> newListener) {
        this.isMoveSuccesfulyCompleted.addListener(newListener);
    }

    public SingleMove getSingleMove(Point sourceLocation,Point targetLocation,SingleMove.MoveType moveType ) {
        SingleMove single=null;
        if (null != moveType) switch (moveType) {
            case HAND_TO_BOARD:
                single = new SingleMove(targetLocation, (int) sourceLocation.getY(), SingleMove.MoveType.HAND_TO_BOARD);
                break;
            case BOARD_TO_HAND:
                single = new SingleMove(sourceLocation, SingleMove.MoveType.BOARD_TO_HAND);
                break;
            default:
                single = new SingleMove(targetLocation, sourceLocation, SingleMove.MoveType.BOARD_TO_BOARD);
                break;
        }
        return single;
    }

    

    private int checkIfToAddTileBeforeOrAfterTheDroppedOnTile(int index, FlowPane holdingSerie) {
        int whereToDrop = MouseInfo.getPointerInfo().getLocation().x;
        boolean retVal;
        double res = whereToDrop - ((HBox) this).localToScreen(Point2D.ZERO).getX();
        retVal = res > TILE_WIDTH / 2;
        if (retVal) {
            index++;
        }
        if (index >= holdingSerie.getChildren().size()) {
            index = holdingSerie.getChildren().size();
        }
        return index;
    }
    public boolean isTileParentIsSerie() {
        return this.getParent().getClass()==AnimatedSeriePane.class;
    }
   
    ////////////test
//    Timeline timeline = new Timeline();
//    private KeyValue originalWidth;
//
//    Duration duration = Duration.seconds(0.2);
//    private double gap = 15.0;
//
//    private void onDragEnter(DragEvent event) {
//        if (timeline.getStatus() == Animation.Status.RUNNING) {
//            timeline.stop();
//        }
//        double targetWidth = Double.parseDouble(event.getDragboard().getString()) + 2;
//        growNode(targetWidth);
//        event.consume();
//    }
//
//    private void onDragLeave(DragEvent event) {
//        if (timeline.getStatus() == Animation.Status.RUNNING) {
//            timeline.stop();
//        }
//        shrinkNode();
//        event.consume();
//    }
//
//    private void growNode(double targetWidth) {
//        timeline.getKeyFrames().clear();
//        if (originalWidth == null) {
//            originalWidth = new KeyValue(prefWidthProperty(), getWidth());
//        }
//        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, originalWidth);
//
//        KeyFrame toKeyFrame = new KeyFrame(duration, new KeyValue(prefWidthProperty(), targetWidth));
//        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
//        timeline.play();
//    }
//
//    private void shrinkNode() {
//        timeline.getKeyFrames().clear();
//
//        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(prefWidthProperty(), getWidth()));
//        KeyFrame toKeyFrame = new KeyFrame(duration, originalWidth);
//        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
//        timeline.play();
//    }

    public int getSerieIndexFromTile(AnimatedTilePane tile) {
        return getSerieIndex((AnimatedSeriePane) (tile.getParent()));
    }

    public int getSerieIndex(AnimatedSeriePane serie) {
        AnimatedFlowPane board = (AnimatedFlowPane) serie.getParent();
        return board.getChildren().indexOf(serie)-1;
    }

    public int getIndexOfTileInSerie(AnimatedTilePane currTile) {
        AnimatedSeriePane serie = (AnimatedSeriePane) (currTile.getParent());
        return serie.getChildren().indexOf(currTile);
    }

    public int getIndexOfTileInHand(AnimatedTilePane currTile) {
        FlowPane hand = ((FlowPane) currTile.getParent());
        return hand.getChildren().indexOf(currTile);
    }

    public void onSingleMoveDone(boolean isLegalMove) {
        this.isLegalMove = isLegalMove;
    }

    public void setSingleMove(SingleMove singleMove) {
        this.singleMove.set(singleMove);
    }

}
