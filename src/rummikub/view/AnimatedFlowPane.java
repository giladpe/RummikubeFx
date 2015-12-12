/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.util.Arrays;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Arthur
 */
    class AnimatedFlowPane extends FlowPane {

        Duration duration = Duration.seconds(0.2);
        private double gap = 15.0;
        
        public AnimatedFlowPane(double hgap, double vgap) {
            super(0, vgap);
            this.gap = hgap;
        }

        public void addChild(Node node) {
            addChild(node, getChildren().size());
        }

        public void addChild(Node node, int index) {
            AnimatedTile ac = new AnimatedTile();
            ac.setWidth(gap);
            ac.heightProperty().bind(this.heightProperty());
            getChildren().addAll(index, Arrays.asList(ac, node));
        }
    }



//************************Test Zone*****************************//

//        public AnimatedFlowPane() {
//            super();
//        }
//
//        public AnimatedFlowPane(Orientation orientation) {
//            super(orientation);
//        }
//
//        public AnimatedFlowPane(Orientation orientation, double hgap, double vgap) {
//            super(orientation, 0, vgap);
//            this.gap = hgap;
//        }
//
//        public AnimatedFlowPane(Node... children) {
//            super(children);
//        }
//
//        public AnimatedFlowPane(Orientation orientation, Node... children) {
//            super(orientation, children);
//        }
//
//        public AnimatedFlowPane(double hgap, double vgap, Node... children) {
//            super(0, vgap, children);
//            this.gap = hgap;
//        }
//
//        public AnimatedFlowPane(Orientation orientation, double hgap, double vgap, Node... children) {
//            super(orientation, 0, vgap, children);
//            this.gap = hgap;
//        }


//***************************END********************************//


