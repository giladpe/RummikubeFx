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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import rummikub.view.ResetableScreen;

/**
 *
 * @author Arthur
 */
public class AnimatedFlowPane extends FlowPane implements ResetableScreen {
 
    //TEST
    //private static final int BOARD_SIZE = 5;
        
    public AnimatedFlowPane() {
        super();
        createGridPane();
    }
    
    private void createGridPane() {
        this.setMinSize(300, 300);
        //pane.setPrefWidth(300);
        //pane.setPrefHeight(300);
        this.setHgap(30);
        this.setVgap(45);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(25));

        
        createNewSeries();

        //Node cell = createCell();
        //this.getChildren().add(cell);
        
//
//        this.setOnDragDropped((DragEvent event) -> {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
//                FlowPane cell1 = (FlowPane)createCell();
//                cell1.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
//                //cell.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
//                this.getChildren().add(cell1);
//                //cell.setBackground(new Background(new BackgroundImage(db.getDragView(), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
//                //cell.setText(db.getString());
//                success = true;
//                
//                //cell.getChildren().add();
//            }
//            event.setDropCompleted(success);
//            event.consume();
//        });
    }

    
    private Node createSeries() {
        //final Label cell = new Label();
        final FlowPane series = new FlowPane();
        //cell.setMinSize(30, 40);
        //cell.setMaxSize(30, 40);
        series.setPrefSize(30, 40);
        //series.set
        series.setHgap(5);
        series.setAlignment(Pos.TOP_LEFT);
        series.setStyle("-fx-border-color: gray; -fx-border-width: 1");

        setSeriesEvents(series);

        return series;
      }
    
    private void setSeriesEvents(FlowPane series) {
        series.setOnDragOver((event) -> {
            if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class ) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        series.setOnDragEntered((event) -> {
            if (series.getChildren().isEmpty()) {
                if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                 series.setStyle("-fx-background-color: green");
                }
            }
            

            event.consume();
        });

        series.setOnDragExited((event) -> {
            series.setStyle("-fx-background-color: none");
            series.setStyle("-fx-border-color: gray; -fx-border-width: 1");
            event.consume();
        });

        series.setOnDragDropped((event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                int index = series.getChildren().isEmpty()? 0 : series.getChildren().indexOf(db.getContent(DataFormat.RTF));
                //int index = cell.getChildren().indexOf(db.getContent(DataFormat.RTF));
                series.getChildren().add(index, (AnimatedTilePane)db.getContent(DataFormat.RTF));
                //cell.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
                
                //cell.setBackground(new Background(new BackgroundImage(db.getDragView(), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
              //cell.setText(db.getString());
              success = true;
              
              //cell.getChildren().add();
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void createNewSeries() {
            //final Label cell = new Label();
        FlowPane newSeries = new FlowPane();
        Label label = new Label();
        label.setText("Add New Series");
        newSeries.getChildren().add(label);
        newSeries.setMinSize(120, 40);
        newSeries.setMaxSize(120, 40);
        newSeries.setPrefSize(120, 40);
        newSeries.setAlignment(Pos.CENTER);
        newSeries.setStyle("-fx-border-color: gray; -fx-border-width: 1");
        this.getChildren().add(newSeries);
        setNewSeriesEvents(newSeries);
        
    }

    private void setNewSeriesEvents(FlowPane newSeries) {
        newSeries.setOnDragEntered((event) -> {
            if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                newSeries.setStyle("-fx-background-color: red");
            }
            event.consume();
        });

        newSeries.setOnDragExited((event) -> {
            newSeries.setStyle("-fx-background-color: none");
            newSeries.setStyle("-fx-border-color: gray; -fx-border-width: 1");
            event.consume();
        });
        
        
        newSeries.setOnDragOver((event) -> {
            if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class ) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        
        newSeries.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                FlowPane cell = (FlowPane)createSeries();
                int index = this.getChildren().indexOf(newSeries);
                cell.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
                this.getChildren().add(index,cell);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

//        newSeries.setOnDragDropped((event) -> {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
//                int index = newSeries.getChildren().isEmpty()? 0 : newSeries.getChildren().indexOf(db.getContent(DataFormat.RTF));
//                //int index = cell.getChildren().indexOf(db.getContent(DataFormat.RTF));
//                newSeries.getChildren().add(index, (AnimatedTilePane)db.getContent(DataFormat.RTF));
//                //cell.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
//                
//                //cell.setBackground(new Background(new BackgroundImage(db.getDragView(), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
//              //cell.setText(db.getString());
//              success = true;
//              
//              //cell.getChildren().add();
//            }
//            event.setDropCompleted(success);
//            event.consume();
//        });
        
    }

    @Override
    public void resetScreen() {
        this.getChildren().clear();
        createNewSeries();
    }
    
    
}



//************************Test Zone*****************************//


//
//    private Node createGridPane() {
//        FlowPane pane = new FlowPane();
//        pane.setMinSize(300, 300);
//        //pane.setPrefWidth(300);
//        //pane.setPrefHeight(300);
//        pane.setHgap(2);
//        pane.setVgap(2);
//        pane.setAlignment(Pos.CENTER);
//        pane.setPadding(new Insets(25));
//
//        Node cell = createCell();
//
//        pane.getChildren().add(cell);
//
//        pane.setOnDragDropped((DragEvent event) -> {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
//                FlowPane cell1 = (FlowPane)createCell();
//                cell1.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
//                //cell.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
//                pane.getChildren().add(cell1);
//                //cell.setBackground(new Background(new BackgroundImage(db.getDragView(), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
//                //cell.setText(db.getString());
//                success = true;
//                
//                //cell.getChildren().add();
//            }
//            event.setDropCompleted(success);
//            event.consume();
//        });
//        
//        
//    
////        for (int i = 0; i < BOARD_SIZE; i++) {
////            for (int j = 0; j < BOARD_SIZE; j++) {
////                Node cell = createCell();
////                
////                pane.add(cell, i, j);
////            }
////        }
//
//        return pane;
//    }



























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


