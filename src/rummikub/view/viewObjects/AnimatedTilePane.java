/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Objects;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.util.Duration;
import rummikub.gameLogic.controller.rummikub.SingleMove;
import rummikub.gameLogic.model.gameobjects.Tile;

/**
 *
 * @author giladPe
 */
public class AnimatedTilePane extends HBox {

    private static final boolean TILE_MOVED_TO_BOARD = true;
    private Label tileLabel;
    private Tile tile;
    //public SingleMove singleMove;
    //private Timeline timeline = new Timeline();
    //private KeyValue originalWidth;
    //private Duration duration = Duration.seconds(0.2);

    private SimpleBooleanProperty isTileMovedFromHandToBoard;
    private SimpleBooleanProperty isTileMovedFromBoardToBoard;
    public static final double TILE_WIDTH = 30;
    public static final double TILE_SPACING = 1.5;
    public static final double TILE_TOTAL_WIDTH = TILE_SPACING + TILE_WIDTH;
    private Point sourceLocation;
    private Point targetLocation;
    //int move;
    private SingleMove.MoveType move;
    private static final boolean BOARD_TO_BOARD=true;

    public AnimatedTilePane(Tile currTile) {
        super();
        //setOnDragEntered(this::onDragEnter);
        //setOnDragExited(this::onDragLeave);
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
        this.setOnDragDetected(this::OnDragDetected);
        this.setOnDragDropped(this::OnDragDropped);
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

    private void OnDragDetected(MouseEvent event) {
        Dragboard db = this.startDragAndDrop(TransferMode.ANY);
        WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
        ClipboardContent content = new ClipboardContent();
        content.put(DataFormat.RTF, this);
        db.setContent(content);
        db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);
        event.consume();
    }

//    private void OnDragDropped(DragEvent event) {
//        Dragboard db = event.getDragboard();
//        boolean successfulDrag = db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class;
//        boolean isDroppedOnSerie = this.getParent().getClass() == AnimatedSeriePane.class;
//        int indexTarget;
//        AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);
//        successfulDrag = successfulDrag && isDroppedOnSerie;
//        
//        if (successfulDrag) {
//            AnimatedSeriePane holdingSerie = (AnimatedSeriePane)this.getParent();  
//            indexTarget = holdingSerie.getChildren().indexOf(this);
//            indexTarget = checkIfToAddTileAfterTheDroppedOnTile(indexTarget);
//
//            if (holdingSerie.containsTile(currTile)) {
//                holdingSerie.removeTile(currTile);
//            } 
//            //else {
//              //maybe we need to remove it from the serie who held that tile before  
//            //}
//
//            holdingSerie.addTile(indexTarget, currTile);
//            
////      this.timeline = new Timeline(new KeyFrame(Duration.millis(800), (ActionEvent event1) -> { 
////            if (holdingSerie.getChildren().isEmpty()) {
////                ((AnimatedFlowPane) holdingSerie.getParent()).removeEmptySerie(holdingSerie);
////            } else {
////                holdingSerie.setMinWidth(holdingSerie.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
////                holdingSerie.setPrefWidth(holdingSerie.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
////            }
////                 }));
//        }
//        
//     //   holdingSerie.fixSize(event);
//        //((AnimatedSeriePane)currTile.getParent()).getChildren().remove(currTile);
//        event.setDropCompleted(successfulDrag);
//        event.consume();
//    }
    private void OnDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class;
        boolean isDroppedOnSerie = this.getParent().getClass() == AnimatedSeriePane.class;
        int indexSource;
        FlowPane holdingSerie = ((FlowPane) this.getParent());
        AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);

        if (isDroppedOnSerie && success && event.getGestureSource() != event.getGestureTarget()) {

            indexSource = holdingSerie.getChildren().indexOf(this);
            indexSource = checkIfToAddTileAfterTheDroppedOnTile(indexSource);

            if (holdingSerie.getChildren().contains(currTile)) {
                holdingSerie.getChildren().remove(currTile);
                holdingSerie.getChildren().add(indexSource, currTile);

            } else {
                holdingSerie.getChildren().add(indexSource, currTile);
            }
//      this.timeline = new Timeline(new KeyFrame(Duration.millis(800), (ActionEvent event1) -> { 
//            if (holdingSerie.getChildren().isEmpty()) {
//                ((AnimatedFlowPane) holdingSerie.getParent()).removeEmptySerie(holdingSerie);
//            } else {
//                holdingSerie.setMinWidth(holdingSerie.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
//                holdingSerie.setPrefWidth(holdingSerie.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
//            }
//                 }));
            success = true;
        }

        // ((AnimatedFlowPane)holdingSerie.getParent()).fixSize();
        event.setDropCompleted(success);
        event.consume();
    }

// private void OnDragDone(DragEvent event) {
//        Dragboard db = event.getDragboard();
//        boolean isTileDroppedInBoard, isTileDroppedInHand;
//        AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);
//        Point pTarget, pSource;
//
//        if (event.getTransferMode() == TransferMode.MOVE) {
//
//            isTileDroppedInBoard = (((Node) event.getTarget()).getParent()).getParent().getClass() == AnimatedFlowPane.class;
//            isTileDroppedInHand = !isTileDroppedInBoard;
//
//            if (isTileDroppedInBoard) {
//                pTarget = new Point(currTile.getIndexOfMySerieInBorad(event), currTile.getIndexOfMeInSerie(event));
//                if (!this.isTileMovedFromHandToBoard.get()) {
//                    //make singleMove From Hand To Board
//                    //pTarget = new Point(currTile.getIndexOfMySerieInBorad(event), currTile.getIndexOfMeInSerie(event));
//                    this.singleMove = new SingleMove(pTarget, (int)sourceLocation.getY(), SingleMove.MoveType.HAND_TO_BOARD);
//                    this.isTileMovedFromHandToBoard.set(TILE_MOVED_TO_BOARD);
//                }
//                else {
//                    //make singleMove From Board To Board
//                    //pTarget = new Point(currTile.getIndexOfMySerieInBorad(event), currTile.getIndexOfMeInSerie(event));
//                    pSource = sourceLocation;
//                    this.singleMove = new SingleMove(pTarget, pSource, SingleMove.MoveType.BOARD_TO_BOARD);
//                }
//                
//                this.sourceLocation.setLocation(pTarget);
//            } 
//            else if (isTileDroppedInHand) {
//                //make singleMove From Board To Hand
//                pSource = new Point(currTile.getIndexOfMySerieInBorad(event), currTile.getIndexOfMeInSerie(event));
//                this.singleMove = new SingleMove(pSource, SingleMove.MoveType.BOARD_TO_HAND);
//                this.isTileMovedFromHandToBoard.set(!TILE_MOVED_TO_BOARD);
//            }
//        }
//
//        event.consume();
//    }
//MY-DONE
    private void OnDragDone(DragEvent event) {
        boolean isTileDroppedInBoard, isTileDroppedInHand;
        Point pTarget, pSource;
        Dragboard db = event.getDragboard();
        AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);

        if (event.getTransferMode() == TransferMode.MOVE) {

            isTileDroppedInBoard = ((Node) event.getTarget()).getParent().getParent().getClass() == AnimatedFlowPane.class;
            isTileDroppedInHand = !isTileDroppedInBoard;

            if (isTileDroppedInBoard) {
                pTarget = new Point(currTile.getIndexOfMySerieInBorad(event), currTile.getIndexOfMeInSerie(event));
                if (!this.isTileMovedFromHandToBoard.get()) {
                    //this.singleMove = new SingleMove(pTarget, (int)sourceLocation.getY(), SingleMove.MoveType.HAND_TO_BOARD);
                    this.targetLocation.setLocation(pTarget);
                    this.move = SingleMove.MoveType.HAND_TO_BOARD;
                    this.isTileMovedFromHandToBoard.set(TILE_MOVED_TO_BOARD);
                    this.isTileMovedFromBoardToBoard.set(!BOARD_TO_BOARD);
                } else {
                    //make singleMove From Board To Board
                    //pSource = sourceLocation;
                    this.targetLocation.setLocation(pTarget);
                    this.move = SingleMove.MoveType.BOARD_TO_BOARD;
                    this.isTileMovedFromBoardToBoard.set(BOARD_TO_BOARD);
// this.singleMove = new SingleMove(pTarget, pSource, SingleMove.MoveType.BOARD_TO_BOARD);

                }
            } else if (isTileDroppedInHand) {
                //make singleMove From Board To Hand
                pSource = new Point(currTile.getIndexOfMySerieInBorad(event), currTile.getIndexOfMeInSerie(event));
                //this.singleMove = new SingleMove(pSource, SingleMove.MoveType.BOARD_TO_HAND);
                this.sourceLocation.setLocation(pSource);
                this.move = SingleMove.MoveType.BOARD_TO_HAND;
                this.isTileMovedFromHandToBoard.set(!TILE_MOVED_TO_BOARD);
                this.isTileMovedFromBoardToBoard.set(!BOARD_TO_BOARD);
            }
        }
    //    this.test();

        event.consume();
    }

    private int getIndexOfMySerieInBorad(DragEvent event) {
        AnimatedTilePane currTile = (AnimatedTilePane) event.getDragboard().getContent(DataFormat.RTF);
        AnimatedFlowPane parent = (AnimatedFlowPane) (currTile.getParent().getParent());
        return parent.getChildren().indexOf(currTile.getParent()) - AnimatedFlowPane.INDEX_OF_NEW_SERIE_ADDING_ARREA;
    }

    private int getIndexOfMeInSerie(DragEvent event) {
        AnimatedTilePane currTile = (AnimatedTilePane) event.getDragboard().getContent(DataFormat.RTF);
        AnimatedSeriePane parent = (AnimatedSeriePane) (currTile.getParent());
        return parent.getChildren().indexOf(currTile);
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
        this.tile = currTile;
        this.isTileMovedFromHandToBoard = new SimpleBooleanProperty(false);
        this.isTileMovedFromBoardToBoard = new SimpleBooleanProperty(false);
        this.sourceLocation = new Point();
        this.targetLocation = new Point();
    }
    public void addBoardListener(ChangeListener<Boolean> newListener) {
        this.isTileMovedFromBoardToBoard.addListener(newListener);
    }

    public boolean getIsTileMovedFromHandToBoard() {
        return this.isTileMovedFromHandToBoard.get();
    }

    public void addHandListener(ChangeListener<Boolean> newListener) {
        this.isTileMovedFromHandToBoard.addListener(newListener);
    }

    public boolean getIsTileMovedFromBoardToBoard() {
        return this.isTileMovedFromBoardToBoard.get();
    }
/////////////////Chan

    public SingleMove getSingleMove() {
        SingleMove single;
        if (this.move == SingleMove.MoveType.HAND_TO_BOARD) {
            single = new SingleMove(targetLocation, (int) sourceLocation.getY(), SingleMove.MoveType.HAND_TO_BOARD);
        } else if (this.move == SingleMove.MoveType.BOARD_TO_HAND) {
            single = new SingleMove(sourceLocation, SingleMove.MoveType.BOARD_TO_HAND);
        } else {
            single = new SingleMove(targetLocation, sourceLocation, SingleMove.MoveType.BOARD_TO_BOARD);
        }
        return single;
    }

    public SingleMove test() {
        SingleMove single;
        if (this.move == SingleMove.MoveType.HAND_TO_BOARD) {
            System.out.println("HAND_TO_BOARD");
            System.out.println("Target: X=" + targetLocation.getX() + " Y=" + targetLocation.getY());
            System.out.println("Source: Y=" + (int) sourceLocation.getY());
            System.out.println("-------------------");

            single = new SingleMove(targetLocation, (int) sourceLocation.getY(), SingleMove.MoveType.HAND_TO_BOARD);

        } else if (this.move == SingleMove.MoveType.BOARD_TO_HAND) {
            System.out.println("BOARD_TO_HAND");
            System.out.println("Source: X=" + sourceLocation.getX() + " Y=" + sourceLocation.getY());
            System.out.println("-------------------");
            single = new SingleMove(sourceLocation, SingleMove.MoveType.BOARD_TO_HAND);
        } else {
            single = new SingleMove(targetLocation, sourceLocation, SingleMove.MoveType.BOARD_TO_BOARD);
            System.out.println("BOARD_TO_BOARD");
            System.out.println("Target: X=" + targetLocation.getX() + " Y=" + targetLocation.getY());
            System.out.println("Source: X=" + sourceLocation.getX() + " Y=" + sourceLocation.getY());
            System.out.println("-------------------");
        }
        return single;
    }

    private int checkIfToAddTileAfterTheDroppedOnTile(int index) {
        int whereToDrop = MouseInfo.getPointerInfo().getLocation().x;
        boolean retVal;
        double res = whereToDrop - ((HBox) this).localToScreen(Point2D.ZERO).getX();
        retVal = res > TILE_WIDTH / 2;

        if (retVal) {
            index++;
        }
        return index;
    }

    public void setSourceLocation() {
        FlowPane parent = (FlowPane) this.getParent();
        int xLocation = 0;
        int yLocation = parent.getChildren().indexOf(this);
        if (parent.getClass() == AnimatedSeriePane.class) {
            xLocation = ((AnimatedFlowPane) parent.getParent()).getChildren().indexOf(parent) - AnimatedFlowPane.INDEX_OF_NEW_SERIE_ADDING_ARREA;
        }
        this.sourceLocation.setLocation(xLocation, yLocation);
//        System.out.println("Hand:");
//        System.out.println("Source: X=" + sourceLocation.getX() + " Y=" + sourceLocation.getY());
//        System.out.println("-------------------");
    }

    //i replaced that function with my version of it to look beter
//    private boolean toAddAfter() {
//        int whereToDrop = MouseInfo.getPointerInfo().getLocation().x;
//        boolean retVal;
//        double res = ((HBox) this).localToScreen(Point2D.ZERO).getX();
//        
//        res = whereToDrop - res;
//        retVal = res > 15;
//        
//        return retVal;
//
//    }
//    
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
    public void updateSource() {
        System.out.println("Update");
        System.out.println("Target: X=" + targetLocation.getX() + " Y=" + targetLocation.getY());
        System.out.println("Source: X=" + sourceLocation.getX() + " Y=" + sourceLocation.getY());
            
        this.sourceLocation = this.targetLocation;
    }
}

//        this.setOnDragDetected((event) -> {
//                  WritableImage snapshot = snapshot(new SnapshotParameters(), null);
//      Dragboard db = startDragAndDrop(TransferMode.ANY);
//            db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);
////            WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
////            this.getScene().setCursor(new ImageCursor(snapshot,
////                    snapshot.getWidth() / 2,
////                    snapshot.getHeight() / 2));
////            this.opacityProperty().set(0.5);
////            Dragboard db = startDragAndDrop(TransferMode.ANY);
//
////            ClipboardContent content = new ClipboardContent();
////            content.
////            db.setContent();
//
//            event.consume();
//        });
//        
//        
//        this.setOnDragDone((event) -> {
//            if (event.getTransferMode() == TransferMode.MOVE) {
//                
//            }
//            event.consume();
//        });
