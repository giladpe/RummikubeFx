
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class GameParametersController implements Initializable {
    private static final String EMPTY_STRING = "";
    
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
    ArrayList<HBox> playersHbox=new ArrayList<>();
    ArrayList<CheckBox> isComputerArr=new ArrayList<>();
    ArrayList<TextField> playersNames=new ArrayList<>();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberOfPlayers.setItems(observableArrayList(2, 3, 4));
        numberOfPlayers.onActionProperty().set((EventHandler<ActionEvent>) (ActionEvent event) -> {
            handleNumberOfPlayersChoiceChange(event);
        });
        
    }
    void initPlayersField(){
        
        playersHbox.add(configPlayer1);
        playersHbox.add(configPlayer2);
        playersHbox.add(configPlayer3);
        playersHbox.add(configPlayer4);
        isComputerArr.add(configPlayer1);
        isComputerArr.add(configPlayer2);
        isComputerArr.add(configPlayer3);
        isComputerArr.add(configPlayer4);
    }
    @FXML
    protected void handleGameNameTextChange(ActionEvent event) {
        final String gameNameString = gameName.getText();

        //* starts with dont works
        if (!(gameNameString.isEmpty() || gameNameString.startsWith("\\s"))) {
            numberOfPlayers.setDisable(false);

            //numberOfPlayers.setItems(observableArrayList(2,3,4));
        } else {
            this.numberOfPlayers.setDisable(true);

            playersHbox.stream().forEach((player) -> {
                //player.setDisable(true);
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
    protected void handleBackToMenuButtonAction(ActionEvent event) {

    }
    
    @FXML
    protected void handleStartPlayingButtonAction(ActionEvent event) {

    }
    
    @FXML
    protected void handleCheckBoxSelection(ActionEvent event) {
        int index =  playersHbox.indexOf(((Node)event.getSource()).getParent());
        if (((CheckBox)event.getSource()).isSelected()) {
            playersHbox.get(index).getChildren().stream().forEach((hNode) -> {
               if (hNode.getClass() == TextField.class) {
                   ((TextField)hNode).setText(EMPTY_STRING);
                   ((TextField)hNode).setDisable(true);
               } });           
        }
        else {
            playersHbox.get(index).getChildren().stream().forEach((hNode) -> {
            if (hNode.getClass() == TextField.class) {
                ((TextField)hNode).setDisable(false);
            } });
        }

    }
    
    

    @FXML
    private void handleNumberOfPlayersChoiceChange(ActionEvent event) {
        hidePlayersFields();
        Iterator<HBox> iterator = playersHbox.iterator();
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
            
//            for (Node hNode : currPlayer.getChildren()) {
//                if (hNode.getClass() == TextField.class) {
//                    ((TextField)hNode).setText(EMPTY_STRING);
//                }
//                else if (hNode.getClass() == CheckBox.class) {
//                    ((CheckBox)hNode).setSelected(false);
//                }
//            }
        }
        
//        int i;
//        for (i = 0; i < this.numberOfPlayers.getValue(); i++) {
//            this.playersHbox.get(i).setVisible(true);
//        }
//        
//        for(;i<4;i++){
//            for (Node hNode : this.playersHbox.get(i).getChildren()) {
//                if(hNode.getId().equals("name")){
//                    
//                }
//            }
//                  
//        }
    }
    
    @FXML
    private void hidePlayersFields() {
        playersHbox.stream().forEach((player) -> { player.setVisible(false); });
    }

}
