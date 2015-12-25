package rummikub.view.viewObjects;

import java.util.Arrays;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Liron Blecher
 */
public class LironAnimatedFlowPaneApplication extends Application {

    public final static DataFormat OBJCET_DATA_FORMAT = new DataFormat("object.data.format");

    class AnimatedFlowPane extends FlowPane {

        Duration duration = Duration.seconds(0.2);
        private double gap = 15.0;

        public AnimatedFlowPane() {
            super();
        }

        public AnimatedFlowPane(Orientation orientation) {
            super(orientation);
        }

        public AnimatedFlowPane(double hgap, double vgap) {
            super(0, vgap);
            this.gap = hgap;
        }

        public AnimatedFlowPane(Orientation orientation, double hgap, double vgap) {
            super(orientation, 0, vgap);
            this.gap = hgap;
        }

        public AnimatedFlowPane(Node... children) {
            super(children);
        }

        public AnimatedFlowPane(Orientation orientation, Node... children) {
            super(orientation, children);
        }

        public AnimatedFlowPane(double hgap, double vgap, Node... children) {
            super(0, vgap, children);
            this.gap = hgap;
        }

        public AnimatedFlowPane(Orientation orientation, double hgap, double vgap, Node... children) {
            super(orientation, 0, vgap, children);
            this.gap = hgap;
        }

        class AnimatedConainer extends HBox {

            Timeline timeline = new Timeline();
            private KeyValue originalWidth;

            public AnimatedConainer() {
                super();
                
                Rectangle rec=new Rectangle();
                rec.setFill(Color.TRANSPARENT);
                rec.setOnDragEntered(this::onDragEnter);
                rec.setOnDragExited(this::onDragLeave);

                rec.setOnDragOver((DragEvent event) -> {
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                });

                rec.setOnDragDropped((event) -> {
                    Dragboard db = event.getDragboard();
//                Paint paint = (Paint)db.getContent(OBJCET_DATA_FORMAT);
                    int index = getChildren().indexOf(this);
                    addChild(new Rectangle(40, 80, Color.FIREBRICK), index);
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

                if (originalWidth == null) {
                    originalWidth = new KeyValue(prefWidthProperty(), getWidth());
                }
                KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, originalWidth);

                KeyFrame toKeyFrame = new KeyFrame(duration, new KeyValue(prefWidthProperty(), targetWidth));
                timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
                timeline.play();
            }

            private void shrinkNode() {
                timeline.getKeyFrames().clear();

                KeyFrame fromKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(prefWidthProperty(), getWidth()));
                KeyFrame toKeyFrame = new KeyFrame(duration, originalWidth);
                timeline.getKeyFrames().addAll(fromKeyFrame, toKeyFrame);
                timeline.play();
            }
        }

        public void addChild(Node node) {
            addChild(node, getChildren().size());
        }

        private void addChild(Node node, int index) {
            AnimatedConainer ac = new AnimatedConainer();
            ac.setPrefWidth(gap);
            getChildren().addAll(index, Arrays.asList(ac, node));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        AnimatedFlowPane animatedFlowPane = new AnimatedFlowPane(10, 0);

        for (int i = 0; i < 3; i++) {
            animatedFlowPane.addChild(new Button("btn" + i));
        }

        Rectangle draggableRectanle = new Rectangle(40, 80, Color.FIREBRICK);
        draggableRectanle.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = draggableRectanle.startDragAndDrop(TransferMode.ANY);
            WritableImage snapshot = draggableRectanle.snapshot(new SnapshotParameters(), null);
            db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);
            ClipboardContent content = new ClipboardContent();
//            content.put(OBJCET_DATA_FORMAT, draggableRectanle);
            content.putString(Double.toString(snapshot.getWidth()));
            db.setContent(content);
            event.consume();
        });

        FlowPane mainPane = new FlowPane(Orientation.VERTICAL, 0, 50, animatedFlowPane, draggableRectanle);
        mainPane.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(mainPane);

        Scene scene = new Scene(root, 500, 250);

        primaryStage.setTitle("Animated FlowPane Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
