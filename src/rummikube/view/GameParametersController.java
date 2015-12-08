
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class GameParametersController implements Initializable {
    private static final String EMPTY_STRING = "";
     private static final int NUM_OF_PLAYERS=4;
    @FXML ChoiceBox<Integer> numberOfPlayers;
    @FXML TextField gameName;
    @FXML HBox configPlayer1;
    @FXML HBox configPlayer2;
    @FXML HBox configPlayer3;
    @FXML HBox configPlayer4;
    @FXML TextField playerName1;
    @FXML TextField playerName2;
    @FXML TextField playerName3;
    @FXML TextField playerName4;
    @FXML CheckBox isComputer1;
    @FXML CheckBox isComputer2;
    @FXML CheckBox isComputer3;
    @FXML CheckBox isComputer4;
    ArrayList<HBox> hBoxList=new ArrayList<>();
    ArrayList<CheckBox> checkBoxList=new ArrayList<>();
    ArrayList<TextField> playersNames=new ArrayList<>();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPlayersField();
        numberOfPlayers.setItems(observableArrayList(2, 3, 4));
    //    numberOfPlayers.onActionProperty().set((EventHandler<ActionEvent>) (ActionEvent event) -> {
     //       handleNumberOfPlayersChoiceChange(event);});
        numberOfPlayers.onActionProperty().setValue(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               handleNumberOfPlayersChoiceChange(event); 
            }
        });
        
    }
    private void initPlayersField(){
        
        this.hBoxList.add(configPlayer1);
        this.hBoxList.add(configPlayer2);
        this.hBoxList.add(configPlayer3);
        this.hBoxList.add(configPlayer4);
        this.checkBoxList.add(isComputer1);
        this.checkBoxList.add(isComputer2);
        this.checkBoxList.add(isComputer3);
        this.checkBoxList.add(isComputer4);
        this.playersNames.add(playerName1);
        this.playersNames.add(playerName2);
        this.playersNames.add(playerName3);
        this.playersNames.add(playerName4);
    }
    @FXML
    protected void handleGameNameTextChange(ActionEvent event) {
        final String gameNameString = gameName.getText();

        //* starts with dont works
        if (!(gameNameString.isEmpty() || gameNameString.startsWith("\\s"))) {
            numberOfPlayers.setDisable(false);
        } else {
            //numberOfPlayers.getSelectionModel().clearSelection();
            this.numberOfPlayers.setDisable(true);
            hBoxList.stream().forEach((player) -> {
                player.setVisible(false);
                
            });
        }
    }
    
    @FXML
    protected void handlePlayerNameTextChange(ActionEvent event) {
        final String gameNameString = gameName.getText();

        //* starts with dont works
        if (!(gameNameString.isEmpty() || gameNameString.startsWith("\\s" ))) {
            if (true/*|| check if not exsists*/) {
                
            }
        }
    }

    @FXML
    protected void handleBackToMenuButtonAction(ActionEvent event) throws IOException {
        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Main Menu");
        URL url = this.getClass().getResource("MainMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        Parent root = (Parent)fxmlLoader.load(url.openStream());
        Scene scene = new Scene(root);
        (((Node)event.getSource()).getScene().getWindow()).hide();
        primaryStage.setScene(scene);
        primaryStage.show();
         
    }
    
    @FXML
    protected void handleStartPlayingButtonAction(ActionEvent event) {

    }
    
    @FXML
    protected void handleCheckBoxSelection(ActionEvent event) {
        int index =  hBoxList.indexOf(((Node)event.getSource()).getParent());
        TextField checkBoxTextField =  this.playersNames.get(index);
        
        if (((CheckBox)event.getSource()).isSelected()) {
            checkBoxTextField.setText(EMPTY_STRING);
            checkBoxTextField.setDisable(true);
        }            
        else {
            checkBoxTextField.setDisable(false);
        }
    }
    
    

    @FXML
    private void handleNumberOfPlayersChoiceChange(ActionEvent event) {
        hidePlayersFields();
        Iterator<HBox> iterator = hBoxList.iterator();
        int i = 0;
        
        while (i < this.numberOfPlayers.getValue() && iterator.hasNext()) {
            HBox currPlayer = iterator.next();
            currPlayer.setVisible(true);
            i++;
        }
        
        while (iterator.hasNext()){
            HBox currPlayer = iterator.next();
            currPlayer.getChildren().stream().forEach((hNode) -> {
                if (hNode.getClass() == TextField.class) {
                    ((TextField)hNode).setText(EMPTY_STRING);
                }
                else if (hNode.getClass() == CheckBox.class) {
                    ((CheckBox)hNode).setSelected(false);
                } 
            });
            
        }        
    }
    
    @FXML
    private void hidePlayersFields() {
        hBoxList.stream().forEach((player) -> { player.setVisible(false); });
    }

}
