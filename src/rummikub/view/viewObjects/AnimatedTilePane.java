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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
public class AnimatedTilePane extends Pane {

    Label tileLabel;
    String color = "";

    public AnimatedTilePane(Tile currTile) {
        super();
        color = currTile.getTileColor().getAnsiColor();
        tileLabel = new Label();
        getStyleClass().add("tile");
        //setId("tile");
        String style = "-fx-text-fill: " + color;
        setMinSize(28, 35);
        setMaxSize(28, 35);
        tileLabel.getStyleClass().add("tileText");
        tileLabel.setText(currTile.getEnumTileNumber().toString());
        tileLabel.setStyle(style);
        getChildren().add(tileLabel);

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onMouseEnter();
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onMouseExit();
            }
        });

//        this.onMouseExitedProperty().addListener(new ChangeListener<EventHandler<? super MouseEvent>>() {
//            @Override
//            public void changed(ObservableValue<? extends EventHandler<? super MouseEvent>> observable, EventHandler<? super MouseEvent> oldValue, EventHandler<? super MouseEvent> newValue) {
//                ((AnimatedTilePane)observable).onMouseLeave();
//            }
//        });
//                       ((AnimatedTilePane)event.getSource()).onMouseEnter();
    }

    //mouse
    private void onMouseEnter() {
        System.out.println("Enter,");

        
        tileLabel.setFont(new Font("Arial", 18));
    }

    private void onMouseExit() {
        System.out.println("Exit,");

        String style = "-fx-text-fill: " + color + "; -fx-font-size: 14px;";
        tileLabel.getStyleClass().add(style);
    }

}
