/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view.viewObjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import rummikub.Engine.TilesLogic.Tile;
import rummikub.view.ImageUtils;

/**
 *
 * @author Arthur
 */
public class AnimatedTile extends Label {

    Timeline timeline = new Timeline();
    Duration duration = Duration.seconds(0.2);
    private KeyValue originalWidth;

    public AnimatedTile() {
        super();
        //setFill(Color.TRANSPARENT);
        setGraphic(ImageUtils.getImageView(ImageUtils.TILE_LOGO));
        setAlignment(Pos.CENTER);
        setTextAlignment(TextAlignment.JUSTIFY);
        setOnDragEntered(this::onDragEnter);
        setOnDragExited(this::onDragLeave);

        setOnDragOver((DragEvent event) -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        setOnDragDropped((event) -> {
            AnimatedFlowPane parent = ((AnimatedFlowPane) event.getSource());
            Dragboard db = event.getDragboard();
//                Paint paint = (Paint)db.getContent(OBJCET_DATA_FORMAT);
            int index = parent.getChildren().indexOf(this);
            parent.addChild(new Rectangle(40, 80, Color.FIREBRICK), index);
            event.setDropCompleted(true);
            event.consume();
        });

    }

    public AnimatedTile(Tile currTile) {
        super();
        //setFill(Color.TRANSPARENT);

        //setGraphic(ImageUtils.getImageView(ImageUtils.TILE_LOGO));
        setId("tile");
        String style ="-fx-text-fill: "+currTile.printColor()+"; -fx-font-size: 14px";
        setStyle(style);
        setBackground(new Background(new BackgroundImage(ImageUtils.getImage(ImageUtils.TILE_LOGO), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
       setAlignment(Pos.CENTER);
        
        setTextAlignment(TextAlignment.JUSTIFY);
        setPrefSize(28, 35);
        setTextFill(Paint.valueOf(currTile.printColor()));
        setText(currTile.getTileStringVal());

        

        setOnDragEntered(this::onDragEnter);
        setOnDragExited(this::onDragLeave);

        setOnDragOver((DragEvent event) -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        setOnDragDropped((event) -> {
            AnimatedFlowPane parent = ((AnimatedFlowPane) event.getSource());
            Dragboard db = event.getDragboard();
//                Paint paint = (Paint)db.getContent(OBJCET_DATA_FORMAT);
            int index = parent.getChildren().indexOf(this);
 //           parent.addChild(new Rectangle(40, 80, Color.FIREBRICK), index);
            event.setDropCompleted(true);
            event.consume();
        });
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

//        if (originalWidth == null) {
//            originalWidth = new KeyValue(widthProperty(), getWidth());
//        }
        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, originalWidth);

//        KeyFrame toKeyFrame = new KeyFrame(duration, new KeyValue(widthProperty(), targetWidth));
        //timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
        timeline.getKeyFrames().addAll(fromKeyFrame);
        timeline.play();
    }

    private void shrinkNode() {
        timeline.getKeyFrames().clear();

//        KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(widthProperty(), getWidth()));
        KeyFrame toKeyFrame = new KeyFrame(duration, originalWidth);
        //timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
        timeline.getKeyFrames().addAll(toKeyFrame);
        timeline.play();
    }
}
