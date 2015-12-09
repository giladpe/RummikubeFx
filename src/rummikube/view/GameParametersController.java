
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import rummikube.Engin.Game;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class GameParametersController implements Initializable {

    private static final String EMPTY_STRING = "";
    private static final int NUM_OF_PLAYERS = 4;
    @FXML
    Button StartPlayingButton;
    //@FXML ChoiceBox<Integer> numberOfPlayers;
    @FXML
    TextField gameName;
    @FXML
    HBox configPlayer1;
    @FXML
    HBox configPlayer2;
    @FXML
    HBox configPlayer3;
    @FXML
    HBox configPlayer4;
    @FXML
    TextField playerName1;
    @FXML
    TextField playerName2;
    @FXML
    TextField playerName3;
    @FXML
    TextField playerName4;
    @FXML
    CheckBox isComputer1;
    @FXML
    CheckBox isComputer2;
    @FXML
    CheckBox isComputer3;
    @FXML
    CheckBox isComputer4;
    ArrayList<HBox> hBoxList = new ArrayList<>();
    ArrayList<CheckBox> checkBoxList = new ArrayList<>();
    ArrayList<TextField> playersNames = new ArrayList<>();
    Game testGame;
    @FXML
    ToggleGroup radioButtonGroup;
    @FXML
    RadioButton B2;
    @FXML
    RadioButton B3;
    @FXML
    RadioButton B4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPlayersField();       
        for (CheckBox cb : this.checkBoxList) {
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                        Boolean old_val, Boolean new_val) {
                    initStartPlayingButton();
                }
            });
        }
        for (TextField tb : this.playersNames) {
            tb.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    initStartPlayingButton();
                }
            });
        }

        this.gameName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                initStartPlayingButton();
            }
        });

        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (radioButtonGroup.getSelectedToggle() != null) {
                    handleRadioButtonChanged();
                }
            }
        });

        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (radioButtonGroup.getSelectedToggle() != null) {
                    handleRadioButtonChanged();
                }
            }
        });
//        radioButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
//        {
//        public void onCheckedChanged
//        {
//           handleB3Changed() {
//           handleB3Changed(); 
//        }});
    }

    private void handleB3Changed() {
        resetPlayerField(3);
        configPlayer3.setVisible(B3.isSelected());
    }

    private void handleB4Changed() {
        configPlayer4.setVisible(B4.isSelected());
        configPlayer3.setVisible(B4.isSelected() || B3.isSelected());
    }

    private void resetPlayerField(int index) {
        this.checkBoxList.get(index).setSelected(false);
        this.playersNames.get(index).setText(EMPTY_STRING);
        this.playersNames.get(index).setDisable(false);

    }

    private void initPlayersField() {

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
        this.B2.setToggleGroup(radioButtonGroup);
        this.B3.setToggleGroup(radioButtonGroup);
        this.B4.setToggleGroup(radioButtonGroup);
        this.B2.setUserData("2");
        this.B3.setUserData("3");
        this.B4.setUserData("4");
    }

    @FXML
    protected void handleGameNameTextChange(ActionEvent event) {
        initStartPlayingButton();
    }

    @FXML
    protected void handlePlayerNameTextChange(ActionEvent event) {      
        initStartPlayingButton();
    }

    @FXML
    protected void handleBackToMenuButtonAction(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Main Menu");
        URL url = this.getClass().getResource("MainMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        Parent root = (Parent) fxmlLoader.load(url.openStream());
        Scene scene = new Scene(root);
        (((Node) event.getSource()).getScene().getWindow()).hide();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    protected void handleStartPlayingButtonAction(ActionEvent event) {

    }

    @FXML
    protected void handleCheckBoxSelection(ActionEvent event) {
        int index = hBoxList.indexOf(((Node) event.getSource()).getParent());
        TextField checkBoxTextField = this.playersNames.get(index);

        if (((CheckBox) event.getSource()).isSelected()) {
            checkBoxTextField.setText(EMPTY_STRING);
            checkBoxTextField.setDisable(true);
        } else {
            checkBoxTextField.setDisable(false);
        }
    }

    @FXML
    private void handleRadioButtonChanged() {
        this.hBoxList.get(0).setVisible(true);
        this.hBoxList.get(1).setVisible(true);
        if (getNumOfPlayerValue() == 2) {
            resetPlayerField(2);
            resetPlayerField(3);
        }
        initPlayer3and4Field();
        initStartPlayingButton();

    }

    private void initPlayer3and4Field() {
        handleB3Changed();
        handleB4Changed();
    }

    @FXML
    private void hidePlayersFields() {
        hBoxList.stream().forEach((player) -> {
            player.setVisible(false);
        });
    }

    private boolean isAllPlayersSet() {
        int size;
        boolean result = true;
        if (isNumOfPlayerSet()) {
            size = getNumOfPlayerValue();
            for (int i = 0; i < size && result; i++) {
                if (!this.isPlayerFieldSet(i)) {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    private boolean isNumOfPlayerSet() {
        return this.radioButtonGroup.getSelectedToggle() != null;
    }

    private int getNumOfPlayerValue() {
        int res = Integer.parseInt(radioButtonGroup.getSelectedToggle().getUserData().toString());
        return res;
    }

    private boolean isPlayerFieldSet(int index) {
        String name=playersNames.get(index).getText(); 
        if (name!= null && name.length()>0) {
            return ( isValidTextField(name)|| this.checkBoxList.get(index).isSelected());
        }
        return false;
    }
    private boolean isValidTextField(String str){
    return !(Character.isWhitespace(str.charAt(0))||str.trim().isEmpty());
    }

    private void initStartPlayingButton() {
        this.StartPlayingButton.setDisable(!isAllFieldSet());
    }

    private boolean isAllFieldSet() {
        return isNumOfPlayerSet() && isAllPlayersSet() & isGameNameSet();
    }

    private boolean isGameNameSet() {
        if (gameName.getText() != null) {
            if (isValidTextField(gameName.getText())) {
                return true;
            }
        }
        return false;
    }

}
