
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
    @FXML HBox PlayerName1;
    @FXML HBox PlayerName2;
    @FXML HBox PlayerName3;
    @FXML HBox PlayerName4;
    
    ArrayList<HBox> players;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberOfPlayers.setItems(observableArrayList(2, 3, 4));
        numberOfPlayers.onActionProperty().set((EventHandler<ActionEvent>) (ActionEvent event) -> {
            handleNumberOfPlayersChoiceChange(event);
        });
        players = new ArrayList<>();
        players.add(PlayerName1);
        players.add(PlayerName2);
        players.add(PlayerName3);
        players.add(PlayerName4);
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

            players.stream().forEach((player) -> {
                player.setDisable(true);
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
        int index =  players.indexOf(((Node)event.getSource()).getParent());
        if (((CheckBox)event.getSource()).isSelected()) {
            players.get(index).getChildren().stream().forEach((hNode) -> {
               if (hNode.getClass() == TextField.class) {
                   ((TextField)hNode).setText(EMPTY_STRING);
                   ((TextField)hNode).setDisable(true);
               } });           
        }
        else {
            players.get(index).getChildren().stream().forEach((hNode) -> {
            if (hNode.getClass() == TextField.class) {
                ((TextField)hNode).setDisable(false);
            } });
        }

    }
    
    

    @FXML
    private void handleNumberOfPlayersChoiceChange(ActionEvent event) {
        hidePlayersFields();
        Iterator<HBox> iterator = players.iterator();
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
//            this.players.get(i).setVisible(true);
//        }
//        
//        for(;i<4;i++){
//            for (Node hNode : this.players.get(i).getChildren()) {
//                if(hNode.getId().equals("name")){
//                    
//                }
//            }
//                  
//        }
    }
    
    @FXML
    private void hidePlayersFields() {
        players.stream().forEach((player) -> { player.setVisible(false); });
    }

}
