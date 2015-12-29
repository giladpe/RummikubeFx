/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
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
    }
        public void setSize() {
        this.setMinWidth(this.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
        this.setPrefWidth(this.getChildren().size() * (TILE_WIDTH + AnimatedFlowPane.TILE_SPACING));
    }

    ////////////test
    
public void updateSerieTilesSource(){
    for (Node tile : this.getChildren()) {
        
        if (tile.getClass() == AnimatedTilePane.class) {
            ((AnimatedTilePane) tile).setSourceLocation();
        }
    }
}

//    public void removeTileFromSerie(int i) {
//        this.getChildren().remove(i);
//        if(!this.getChildren().isEmpty()){
//            this.updateSerieTilesSource();
//        }
        
//    }
}
