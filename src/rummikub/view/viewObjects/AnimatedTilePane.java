/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private Label tileLabel;
    //A
    private Tile tile;
    private SingleMove singleMove;
    //private Timeline timeline = new Timeline();
    //private KeyValue originalWidth;
    //private Duration duration = Duration.seconds(0.2);




    private SimpleBooleanProperty isTileMovedToBoard;

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
        
        //this.setOnDragEntered(this::onDragEnter);
        //this.setOnDragExited(this::onDragLeave);

        
        this.setOnMouseEntered((MouseEvent event) -> {
            tileLabel.setScaleX(1.3);
            tileLabel.setScaleY(1.3);
        });
        
        this.setOnMouseExited((MouseEvent event) -> {
            tileLabel.setScaleX(1);
            tileLabel.setScaleY(1);
        });
        
        this.setOnDragDetected((event) -> {
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);

            ClipboardContent content = new ClipboardContent();  
            content.put(DataFormat.RTF, this);
            
            //content.put(DataFormat.RTF, tile);
            // content.putString(number + "");
            db.setContent(content);
            db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);
            event.consume();
        });

        this.setOnDragDone((event) -> {
            TransferMode a= event.getTransferMode();
            if (event.getTransferMode() == TransferMode.MOVE ) {
                this.isTileMovedToBoard.set(!this.isTileMovedToBoard.get());
            }
            event.consume();
        });
    }

    private void initTile(Tile currTile) {
        getStyleClass().add("tile");
        String style = "-fx-text-fill: " +  currTile.getTileColor().getAnsiColor();
        setMinSize(30, 40);
        setMaxSize(30, 40);
        tileLabel = new Label();
        tileLabel.getStyleClass().add("tileText");
        tileLabel.setText(currTile.getEnumTileNumber().toString());
        tileLabel.setStyle(style);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(2,0 , 0,0));
        setTileEvents();
        getChildren().add(tileLabel);
        
        //A
        this.tile = currTile;
        this.isTileMovedToBoard = new SimpleBooleanProperty(false);
    }
    
    
    public void addListener(ChangeListener<Boolean> newListener) {
        this.isTileMovedToBoard.addListener(newListener);
    }
    
    public boolean getIsTileMovedToBoard() {
        return this.isTileMovedToBoard.get();
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