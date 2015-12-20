/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import com.sun.javafx.beans.event.AbstractNotifyListener;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import rummikub.gameLogic.model.gameobjects.Tile;

/**
 *
 * @author giladPe
 */
public class AnimatedTilePane extends HBox {

    private Label tileLabel;
    


    public AnimatedTilePane(Tile currTile) {
        super();
        initTile(currTile);
    }


    private void setTileEvents() {
        this.setOnMouseEntered((MouseEvent event) -> {
            tileLabel.setScaleX(1.3);
            tileLabel.setScaleY(1.3);
        });
        
        this.setOnMouseExited((MouseEvent event) -> {
            tileLabel.setScaleX(1);
            tileLabel.setScaleY(1);
        });
        
        this.setOnDragDetected((event) -> {
            WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);

            //ClipboardContent content = new ClipboardContent();
            // content.putString(number + "");
            // db.setContent(content);
             db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);

             event.consume();
        });

        this.setOnDragDone((event) -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                this.setScaleX(5);
             //label.setText("");
            }
            event.consume();
        });
        
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
    }
}
