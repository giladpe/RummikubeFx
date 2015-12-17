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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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

    Label tileLabel;
    
    

    public AnimatedTilePane(Tile currTile) {
        super();
        tileLabel = new Label();
        getStyleClass().add("tile");
        String style = "-fx-text-fill: " +  currTile.getTileColor().getAnsiColor();
        setMinSize(30, 40);
        setMaxSize(30, 40);
        tileLabel.getStyleClass().add("tileText");
        tileLabel.setText(currTile.getEnumTileNumber().toString());
        tileLabel.setStyle(style);
        tileLabel.setFont(new Font(50));
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(2,0 , 0,0));
        
       //this.Seton
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //onMouseEnter();
                //Font f = new Font( 50 );

            }
        });
//        this.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                //onMouseExit();
//                tileLabel.setFont( new Font( 14 ));
                        tileLabel.setFont( new Font( 20));

//            }
//        });

//        this.onMouseExitedProperty().addListener(new ChangeListener<EventHandler<? super MouseEvent>>() {
//            @Override
//            public void changed(ObservableValue<? extends EventHandler<? super MouseEvent>> observable, EventHandler<? super MouseEvent> oldValue, EventHandler<? super MouseEvent> newValue) {
//                ((AnimatedTilePane)observable).onMouseLeave();
//            }
//        });
//                       ((AnimatedTilePane)event.getSource()).onMouseEnter();
        
        getChildren().add(tileLabel);
    }

    //mouse
    private void onMouseEnter() {
        //System.out.println("Enter,");
        //tileLabel.setFont( new Font( 24 ));
        ((Label)(this.getChildren().get(0))).setFont(new Font( 24 ));
    }

    private void onMouseExit() {
        //System.out.println("Exit,");
        //tileLabel.setFont( new Font( 14 ));
        ((Label)(this.getChildren().get(0))).setFont(new Font( 14 ));
    }
}
