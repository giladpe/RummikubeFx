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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import rummikub.gameLogic.model.gameobjects.Tile;

/**
 *
 * @author Arthur
 */
public class AnimatedFlowPaneAndTileTest extends FlowPane {
    
        Duration duration = Duration.seconds(0.2);
        private double gap = 15.0;

//        public AnimatedFlowPane() {
//            super();
//        }
//
//        public AnimatedFlowPane(Orientation orientation) {
//            super(orientation);
//        }

        public AnimatedFlowPaneAndTileTest(double hgap, double vgap) {
            super(0, vgap);
            this.gap = hgap;
        }
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

        class AnimatedConainer extends Button  {

            private Timeline timeline = new Timeline();
            private KeyValue originalWidth;
            private Label tileLabel;

            public AnimatedConainer(Tile currTile) {
                super();
                initTile(currTile);
            }
            
            public AnimatedConainer() {
                super();
                initTile(null);
            }
            
            private void initTile(Tile currTile) {
                getStyleClass().add("tile");
                setMinSize(30, 40);
                setMaxSize(30, 40);
                tileLabel = new Label();
                tileLabel.getStyleClass().add("tileText");
                setAlignment(Pos.TOP_CENTER);
                setPadding(new Insets(2,0 , 0,0));
                setTileEvents();
                getChildren().add(tileLabel);
                
                if (currTile != null) {
                    String style = "-fx-text-fill: " +  currTile.getTileColor().getAnsiColor();
                    tileLabel.setStyle(style);
                    tileLabel.setText(currTile.getEnumTileNumber().toString());
                }
            }


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
                    //originalWidth = new KeyValue(widthProperty(), getWidth());
                }
                KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, originalWidth);

                //KeyFrame toKeyFrame = new KeyFrame(duration, new KeyValue(widthProperty(), targetWidth));
                //timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
                timeline.play();
            }

            private void shrinkNode() {
                timeline.getKeyFrames().clear();

                //KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(widthProperty(), getWidth()));
                KeyFrame toKeyFrame = new KeyFrame(duration, originalWidth);
                //timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
                timeline.play();
            }

            private void setTileEvents() {
                
                setOnDragEntered(this::onDragEnter);
                setOnDragExited(this::onDragLeave);

                setOnDragOver((DragEvent event) -> {
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                });

                setOnDragDropped((event) -> {
                    Dragboard db = event.getDragboard();
//                Paint paint = (Paint)db.getContent(OBJCET_DATA_FORMAT);
                    int index = getChildren().indexOf(this);
                    addChild(new Rectangle(40, 80, Color.FIREBRICK), index);
                    event.setDropCompleted(true);
                    event.consume();
                });
            }
        }

        public void addChild(Node node) {
            addChild(node, getChildren().size());
        }

        private void addChild(Node node, int index) {
            AnimatedConainer ac = new AnimatedConainer();
            //ac.setWidth(gap);
            //ac.heightProperty().bind(this.heightProperty());
            getChildren().addAll(index, Arrays.asList(ac, node));
        }
    }
