
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class GameParametersController implements Initializable {

    @FXML
    ChoiceBox<Integer> numberOfPlayers;
    @FXML
    TextField gameName;
    @FXML
    HBox PlayerName1;
    @FXML
    HBox PlayerName2;
    @FXML
    HBox PlayerName3;
    @FXML
    HBox PlayerName4;

    @FXML
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

//    private void setNumberOfPlayersButtonEnabled() {
//        numberOfPlayers.setDisable(false);
//    }
    @FXML
    protected void handleBackToMenuButtonAction(ActionEvent event) {

    }

    @FXML
    private void handleNumberOfPlayersChoiceChange(ActionEvent event) {
        hidePlayersFields();
        int i;
        for (i = 0; i < this.numberOfPlayers.getValue(); i++) {
            this.players.get(i).setVisible(true);
        }
        for(;i<4;i++){
            for (Node hNode : this.players.get(i).getChildren()) {
                if(hNode.getId().equals("name")){
                    
                }
            }
                  
        }
    }
        @FXML
        private void hidePlayersFields
        
            () {
        for (int i = 0; i < 4; i++) {
                this.players.get(i).setVisible(false);
            }
        }

    }
