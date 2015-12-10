
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
    private static final int TWO_PLAYERS = 2;
    private static final int THREE_PLAYERS = 3;
    private static final int FOUR_PLAYERS = 4;
    private static final int PLAYER1 = 0;
    private static final int PLAYER2 = 1;
    private static final int PLAYER3 = 2;
    private static final int PLAYER4 = 3;
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
        this.checkBoxList.stream().forEach((cb) -> {
            cb.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                initStartPlayingButton();
            });
        });
        this.playersNames.stream().forEach((tb) -> {
            tb.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                initStartPlayingButton();
            });
        });

        this.gameName.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            initStartPlayingButton();
        });

        radioButtonGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (radioButtonGroup.getSelectedToggle() != null) {
                handleRadioButtonChanged();
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
        int numberOfPlayer = getNumOfPlayers();
        switch (numberOfPlayer) {

            case FOUR_PLAYERS:
                handleFourPlayersButton();
                break;
            case THREE_PLAYERS:
                handleThreePlayersButton();
                break;
            case TWO_PLAYERS:
            default:
                handleTwoPlayersButton();
                break;

        }
        initStartPlayingButton();
    }

    @FXML
    private boolean isAllPlayersSet() {
        int numOfPlayers;
        boolean isLegalConditions = isNumOfPlayersSet();
        if (isLegalConditions) {
            numOfPlayers = getNumOfPlayers();
            for (int i = 0; i < numOfPlayers && isLegalConditions; i++) {
                isLegalConditions = this.isPlayerFieldSet(i);
            }
        }
        
        return isLegalConditions && atleastOnePlayerIsHuman();
    }

    private boolean isNumOfPlayersSet() {
        return this.radioButtonGroup.getSelectedToggle() != null;
    }

    private int getNumOfPlayers() {
        return Integer.parseInt(radioButtonGroup.getSelectedToggle().getUserData().toString());
    }

    private boolean isPlayerFieldSet(int index) {
        boolean result = false;
        String name = playersNames.get(index).getText();
        if (this.checkBoxList.get(index).isSelected()) {
            result = true;
        } else if (name != null && name.length() > 0) {
            result = (isValidTextField(name));
        }
        return result;
    }

    private boolean isValidTextField(String str) {
        return !(str.trim().isEmpty() || Character.isWhitespace(str.charAt(0)));
    }

    private void initStartPlayingButton() {
        this.StartPlayingButton.setDisable(!isAllFieldSet());
    }

    private boolean isAllFieldSet() {
        return isNumOfPlayersSet() && isAllPlayersSet() && isGameNameSet();
    }

    private boolean isGameNameSet() {
        if (gameName.getText() != null) {
            if (isValidTextField(gameName.getText())) {
                return true;
            }
        }
        return false;
    }

    private void handleTwoPlayersButton() {

        resetPlayerField(PLAYER3);
        resetPlayerField(PLAYER4);
        this.hBoxList.get(PLAYER1).setVisible(true);
        this.hBoxList.get(PLAYER2).setVisible(true);
        this.hBoxList.get(PLAYER3).setVisible(false);
        this.hBoxList.get(PLAYER4).setVisible(false);
    }

    private void handleThreePlayersButton() {
        resetPlayerField(PLAYER4);
        this.hBoxList.get(PLAYER1).setVisible(true);
        this.hBoxList.get(PLAYER2).setVisible(true);
        this.hBoxList.get(PLAYER3).setVisible(true);
        this.hBoxList.get(PLAYER4).setVisible(false);
    }

    private void handleFourPlayersButton() {
        this.hBoxList.get(PLAYER1).setVisible(true);
        this.hBoxList.get(PLAYER2).setVisible(true);
        this.hBoxList.get(PLAYER3).setVisible(true);
        this.hBoxList.get(PLAYER4).setVisible(true);
    }

    private boolean atleastOnePlayerIsHuman() {
        boolean foundHuman = false;

        for (int i = 0; i < getNumOfPlayers() && !foundHuman; i++) {
            foundHuman = !this.checkBoxList.get(i).isSelected();
        }

        return foundHuman;
    }
}
