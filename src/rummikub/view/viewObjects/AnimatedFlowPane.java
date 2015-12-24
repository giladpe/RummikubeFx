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

        
        createNewSerieAddinArea();

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

    
    private Node createSerie() {
        //final Label cell = new Label();
        final FlowPane series = new FlowPane();
        //cell.setMinSize(30, 40);
        //cell.setMaxSize(30, 40);
        series.setPrefSize(30, 40);
        //series.set
        //series.setHgap(5);
       // series.setAlignment(Pos.CENTER_LEFT);
        series.setStyle("-fx-border-color: gray; -fx-border-width: 1");

        setSerieEvents(series);

        return series;
      }
    
    private void setSerieEvents(FlowPane series) {
//        series.setOnDragOver((event) -> {
//            if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class ) {
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//            event.consume();
//        });

        series.setOnDragEntered((event) -> {
            if (series.getChildren().isEmpty()) {
                if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                 series.setStyle("-fx-border-color: blue; -fx-border-width: 3");
                }
            }
            

            event.consume();
        });

        series.setOnDragExited((event) -> {
            series.setStyle("-fx-border-color: gray; -fx-border-width: 1");
            event.consume();
        });

//        series.setOnDragDropped((event) -> {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
//                //int index = series.getChildren().isEmpty()? 0 : series.getChildren().indexOf(db.getContent(DataFormat.RTF));
//                //int index = cell.getChildren().indexOf(db.getContent(DataFormat.RTF));
//                int index = series.getChildren().indexOf();
//
//                series.getChildren().add(index, (AnimatedTilePane)db.getContent(DataFormat.RTF));
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

    private void createNewSerieAddinArea() {
            //final Label cell = new Label();
        FlowPane newSerieAddingArea = new FlowPane();
        Label label = new Label();
        label.setText("Add New Series");
        newSerieAddingArea.getChildren().add(label);
        newSerieAddingArea.setMinSize(120, 40);
        newSerieAddingArea.setMaxSize(120, 40);
        newSerieAddingArea.setPrefSize(120, 40);
        newSerieAddingArea.setAlignment(Pos.CENTER);
        newSerieAddingArea.setStyle("-fx-border-color: gray; -fx-border-width: 1");
        this.getChildren().add(newSerieAddingArea);
        setNewSeriesEvents(newSerieAddingArea);
    }

    private void setNewSeriesEvents(FlowPane newSerieAddingArea) {
        newSerieAddingArea.setOnDragEntered((event) -> {
            if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                newSerieAddingArea.setStyle("-fx-background-color: red");
            }
            event.consume();
        });

        newSerieAddingArea.setOnDragExited((event) -> {
            newSerieAddingArea.setStyle("-fx-background-color: none");
            newSerieAddingArea.setStyle("-fx-border-color: gray; -fx-border-width: 1");
            event.consume();
        });
        
        newSerieAddingArea.setOnDragOver((event) -> {
            if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class ) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        
        newSerieAddingArea.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                FlowPane serie = (FlowPane)createSerie();
//              int index = this.getChildren().indexOf(newSeries);
                
                serie.getChildren().add((AnimatedTilePane)db.getContent(DataFormat.RTF));
                this.getChildren().add(serie);
                //newSerieAddingArea (0),next(1)
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
        createNewSerieAddinArea();
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


