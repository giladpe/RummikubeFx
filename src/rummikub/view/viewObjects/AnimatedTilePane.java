/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import java.awt.MouseInfo;
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
    //A
    private Tile tile;
    private SingleMove singleMove;
    //private Timeline timeline = new Timeline();
    //private KeyValue originalWidth;
    //private Duration duration = Duration.seconds(0.2);

    private SimpleBooleanProperty isTileMovedFromHandToBoard;
    public static final double TILE_WIDTH = 30;

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
        // ((FlowPane)this.getParent()).setMinWidth((((FlowPane)this.getParent()).getChildren().size()-1) * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
        //this.getParent().setPrefWidth(this.getParent().getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));                
        event.consume();
    }
    
    private void OnDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        int indexSource;
        FlowPane holdingSerie = ((FlowPane) this.getParent());
        AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);

        if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {

            indexSource = holdingSerie.getChildren().indexOf(this);
            checkIfToAddTileAfterTheDroppedOnTile(indexSource);

            if (holdingSerie.getChildren().contains(currTile)) {
                holdingSerie.getChildren().remove(currTile);
                holdingSerie.getChildren().add(indexSource, currTile);
            }
            else {
                holdingSerie.getChildren().add(indexSource, currTile);
            }
//      this.timeline = new Timeline(new KeyFrame(Duration.millis(800), (ActionEvent event1) -> { 
            if (holdingSerie.getChildren().isEmpty()) {
                ((AnimatedFlowPane)holdingSerie.getParent()).removeEmptySerie(holdingSerie);
            } 
            else {
                holdingSerie.setMinWidth(holdingSerie.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
                holdingSerie.setPrefWidth(holdingSerie.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
            }
 
//                 }));
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    
    private void OnDragDone(DragEvent event) {
        boolean isTileDroppedInBoard, isTileDroppedInHand;
        
        if (event.getTransferMode() == TransferMode.MOVE) {
            
            isTileDroppedInBoard = ((Node)event.getAcceptingObject()).getParent().getParent().getClass() == AnimatedFlowPane.class;
            isTileDroppedInHand = !isTileDroppedInBoard;
                
            if (isTileDroppedInBoard) {
                if (this.isTileMovedFromHandToBoard.get()) {
                    //make singleMove From Hand To Board
                    this.isTileMovedFromHandToBoard.set(TILE_MOVED_TO_BOARD);    
                }
                else {
                    //make singleMove From Board To Board
                }
            }
            else if (isTileDroppedInHand) {
                //make singleMove From Board To Hand
                this.isTileMovedFromHandToBoard.set(!TILE_MOVED_TO_BOARD);    
            }
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
        this.tile = currTile;
        this.isTileMovedFromHandToBoard = new SimpleBooleanProperty(false);
    }

    public void addListener(ChangeListener<Boolean> newListener) {
        this.isTileMovedFromHandToBoard.addListener(newListener);
    }

    public boolean getIsTileMovedToBoard() {
        return this.isTileMovedFromHandToBoard.get();
    }

    public SingleMove getSingleMove() {
        return singleMove;
    }

        
    private void checkIfToAddTileAfterTheDroppedOnTile(int index) {
        int whereToDrop = MouseInfo.getPointerInfo().getLocation().x;
        boolean retVal;
        double res = ((HBox) this).localToScreen(Point2D.ZERO).getX();
        
        res = whereToDrop - res;
        retVal = res > 15;
        
        if (retVal) {
            index++;
        }
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
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.tileLabel);
        hash = 83 * hash + Objects.hashCode(this.tile);
        hash = 83 * hash + Objects.hashCode(this.singleMove);
        hash = 83 * hash + Objects.hashCode(this.isTileMovedFromHandToBoard);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimatedTilePane other = (AnimatedTilePane) obj;
        if (!Objects.equals(this.tileLabel, other.tileLabel)) {
            return false;
        }
        if (!Objects.equals(this.tile, other.tile)) {
            return false;
        }
        if (!Objects.equals(this.singleMove, other.singleMove)) {
            return false;
        }
        if (!Objects.equals(this.isTileMovedFromHandToBoard, other.isTileMovedFromHandToBoard)) {
            return false;
        }
        return true;
    }

    ////////////test
    Timeline timeline = new Timeline();
    private KeyValue originalWidth;

    Duration duration = Duration.seconds(0.2);
    private double gap = 15.0;

    private void onDragEnter(DragEvent event) {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
        }
        double targetWidth = Double.parseDouble(event.getDragboard().getString()) + 2;
        growNode(targetWidth);
        event.consume();
    }

    private void onDragLeave(DragEvent event) {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
        }
        shrinkNode();
        event.consume();
    }

    private void growNode(double targetWidth) {
        timeline.getKeyFrames().clear();
        if (originalWidth == null) {
            originalWidth = new KeyValue(prefWidthProperty(), getWidth());
        }
        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, originalWidth);

        KeyFrame toKeyFrame = new KeyFrame(duration, new KeyValue(prefWidthProperty(), targetWidth));
        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
        timeline.play();
    }

    private void shrinkNode() {
        timeline.getKeyFrames().clear();

        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(prefWidthProperty(), getWidth()));
        KeyFrame toKeyFrame = new KeyFrame(duration, originalWidth);
        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
        timeline.play();
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
