/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import rummikub.gameLogic.model.gameobjects.Tile;

/**
 *
 * @author giladPe
 */
public class AnimatedTilePane extends HBox {

    private Label tileLabel;
    //A
    private Tile tile;

    private SimpleBooleanProperty isTileMovedToBoard;

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
            if (event.getTransferMode() == TransferMode.MOVE) {
                this.isTileMovedToBoard.set(true);
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