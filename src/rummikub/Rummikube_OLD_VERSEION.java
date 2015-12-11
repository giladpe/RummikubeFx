package rummikub;

////Gilad And Artur
//package rummikube;
//
//import java.net.URL;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import rummikube.Engin.Game;
////import rummikube.view.MainMenuController;
//
///**
// *
// * @author giladPe
// */
//public class Rummikube_OLD_VERSEION extends Application {
//    private Game game; 
//    @Override
//    
//    public void start(Stage primaryStage) throws Exception {  
//    
////       Controller gameControl=new Controller();
////      gameControl.runGame();
//        primaryStage.setTitle("Rummikube");
//        URL url = getClass().getResource("view/MainMenu.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(url);
//        Parent root = (Parent)fxmlLoader.load(url.openStream());
//        //MainMenuController menu = (MainMenuController) fxmlLoader.getController();        
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//    public Game getGame(){return game;}
//}




//
//
///* public void start(Stage primaryStage)throws Exception{
//       Pane root=new Pane();
//       root.setPrefSize(800,600);
//       InputStream file =  Files.newInputStream(Paths.get("/images/MenuPic.jpg"));
//       Image img = new Image(file);
//       file.close();
//       
//       ImageView imgView =new ImageView(img);
//       root.getChildren().addAll(imgView);
//       
//       Scene scene = new Scene(root);
//       primaryStage.setScene(scene);
//       primaryStage.show();*/