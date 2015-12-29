/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import java.util.Arrays;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static rummikub.view.viewObjects.AnimatedFlowPane.TILE_SPACING;
import static rummikub.view.viewObjects.AnimatedTilePane.TILE_WIDTH;

/**
 *
 * @author giladPe
 */
public class AnimatedSeriePane extends FlowPane {

    public AnimatedSeriePane() {
        setPrefHeight(40);
        setOrientation(Orientation.VERTICAL);
        setHgap(TILE_SPACING);
        setStyle("-fx-border-color: gray; -fx-border-width: 1");

//        setSerieEvents();
        //setOnDragEntered(this::onDragEnter);
        //setOnDragExited(this::onDragLeave);
//        setOnDragOver((DragEvent event) -> {
//            event.acceptTransferModes(TransferMode.ANY);
//            event.consume();
//        });
//        setOnDragDropped((event) -> {
//            Dragboard db = event.getDragboard();
//            AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);
//            int index = getChildren().indexOf(this);
//            addChild(currTile, index);
//            event.setDropCompleted(true);
//            event.consume();
//        });

        //return series;
    }
        public void setSize() {
        this.setMinWidth(this.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
        this.setPrefWidth(this.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
    }

//    private void setSerieEvents() {
//
//        this.setOnDragEntered((event) -> {
//            if (this.getChildren().isEmpty()) {
//                if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
//                    this.setStyle("-fx-border-color: blue; -fx-border-width: 3");
//                }
//            }
//
//            event.consume();
//        });
//
//        this.setOnDragExited((event) -> {
//            this.setStyle("-fx-border-color: gray; -fx-border-width: 1");
//            event.consume();
//        });
//
//    }
    ////////////test
    
public void updateSerieTilesSource(){
for (Node tile : this.getChildren()) {
            ((AnimatedTilePane) tile).setSourceLocation();
            //((AnimatedTilePane)tile).tile
        }
}
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
            originalWidth = new KeyValue(minWidthProperty(), getWidth());
        }
        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, originalWidth);

        KeyFrame toKeyFrame = new KeyFrame(duration, new KeyValue(minWidthProperty(), targetWidth));
        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
        timeline.play();
    }

    private void shrinkNode() {
        timeline.getKeyFrames().clear();
        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(minWidthProperty(), getWidth()));
        KeyFrame toKeyFrame = new KeyFrame(duration, originalWidth);
        timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
        timeline.play();
    }

    private void addChild(Node node, int index) {
        AnimatedSeriePane ac = new AnimatedSeriePane();
        ac.setPrefWidth(gap);
        ac.setWidth(gap);
        //ac.heightProperty().bind(this.prefHeightProperty());
        getChildren().addAll(index, Arrays.asList(ac, node));
    }

    public void removeTileFromSerie(int i) {
        this.getChildren().remove(i);
        if(!this.getChildren().isEmpty()){
            this.updateSerieTilesSource();
        }
        
    }
}
