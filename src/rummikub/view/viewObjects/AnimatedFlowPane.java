/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import java.awt.Point;
import java.util.Iterator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import rummikub.gameLogic.controller.rummikub.SingleMove;
import rummikub.view.ResetableScreen;
import static rummikub.view.viewObjects.AnimatedTilePane.TILE_WIDTH;

/**
 *
 * @author Arthur
 */
public class AnimatedFlowPane extends FlowPane implements ResetableScreen {

    static int INDEX_OF_NEW_SERIE_ADDING_ARREA = 1;

    public static final double TILE_SPACING = 1.5;

    public AnimatedFlowPane() {
        super();
        createFlowPane();
    }

    private void createFlowPane() {
        this.setMinSize(300, 300);
        this.setHgap(30);
        this.setVgap(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(25));
        createNewSerieAddinArea();
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
            if (event.getDragboard().getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        newSerieAddingArea.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            AnimatedTilePane currTile = (AnimatedTilePane) db.getContent(DataFormat.RTF);
            SingleMove singleMove;
            boolean success = false;
            if (db.getContent(DataFormat.RTF).getClass() == AnimatedTilePane.class) {
                AnimatedSeriePane newSerie = new AnimatedSeriePane();
                newSerie.getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
                    onAddingAndRemovingSerie(newSerie);
                });

                int xTarget = this.getChildren().isEmpty() ? 0 : this.getChildren().size() - 1;
                int yTarget = 0;
                Point pTarget = new Point(xTarget, yTarget);
                FlowPane flowPane = ((FlowPane) currTile.getParent());

                if (flowPane.getClass() == AnimatedSeriePane.class) {
                    //board to new serie in board
                    int xSource = currTile.getSerieIndexFromTile(currTile);
                    int ySource = currTile.getIndexOfTileInSerie(currTile);
                    Point pSource=new Point(xSource, ySource);
                    singleMove = new SingleMove(pTarget, pSource, SingleMove.MoveType.BOARD_TO_BOARD);
                } else {
                    int ySource = currTile.getIndexOfTileInHand(currTile);
                    singleMove = new SingleMove(pTarget, ySource, SingleMove.MoveType.HAND_TO_BOARD);
                }
                currTile.setSingleMove(singleMove);
                success = currTile.getIsLegalMove();
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    @Override
    public void resetScreen() {
        this.getChildren().clear();
        createNewSerieAddinArea();
    }

    public void removeEmptySerie(FlowPane holdingSerie) {
        this.getChildren().remove(holdingSerie);
    }

    public void updateSeriesSourceLocation() {
        int i = 0;
        for (Node serie : this.getChildren()) {
            if (i > 0) {
                ((AnimatedSeriePane) serie).updateSerieTilesSource();
            }
            i++;
        }
    }
    private void onAddingAndRemovingSerie(AnimatedSeriePane newSerie) {
        if (newSerie.getChildren().isEmpty()) {
            removeEmptySerie(newSerie);
        } else {
            newSerie.setSize();
        }
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

