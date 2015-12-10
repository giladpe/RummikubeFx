
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import rummikube.Engin.Game;
import rummikube.Engin.Utility;
import static rummikube.Engin.Utility.PLAYER1;
import static rummikube.Engin.Utility.PLAYER2;
import static rummikube.Engin.Utility.PLAYER3;
import static rummikube.Engin.Utility.PLAYER4;

/**
 * FXML Controller class
 *
 * @author giladPe
 */
public class GameParametersController implements Initializable {

    private final String DUP_NAME_MSG = "Name is already exict!";
    private final String EMPTY_GAME_NAME_MSG = "Insert name for the game!";
    private final String NO_HUMAN_MSG = "Chose atleast one human player!";
    private Game.Settings gameSettings;
    @FXML
    Button StartPlayingButton;
    //@FXML ChoiceBox<Integer> numberOfPlayers;
    @FXML
    Label errorMsg;
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
        this.playersNames.stream().forEach((nameTextField) -> {
            nameTextField.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                initStartPlayingButton();
                //handelErrorMsg();
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
        this.playersNames.get(index).setText(Utility.EMPTY_STRING);
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
    protected void handleStartPlayingButtonAction(ActionEvent event) {
        String gameName = this.gameName.getText();
        int numOfPlayers = getNumOfPlayers();
        int numOfComputerPlayers = getNumOfComputerPlayers();
        int numOfHumansPlayers = numOfPlayers - numOfComputerPlayers;
        ArrayList <String> sPlayersNames=getPlayersTextFieldList();
        addComputerNames(sPlayersNames,numOfComputerPlayers);
        this.gameSettings = new Game.Settings(gameName, numOfComputerPlayers, numOfHumansPlayers, sPlayersNames);
        int i=0;
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
    protected void handleCheckBoxSelection(ActionEvent event) {
        int index = hBoxList.indexOf(((Node) event.getSource()).getParent());
        TextField checkBoxTextField = this.playersNames.get(index);

        if (((CheckBox) event.getSource()).isSelected()) {
            checkBoxTextField.setText(Utility.EMPTY_STRING);
            checkBoxTextField.setDisable(true);
        } else {
            checkBoxTextField.setDisable(false);
        }
    }

    @FXML
    private void handleRadioButtonChanged() {
        int numberOfPlayer = getNumOfPlayers();
        switch (numberOfPlayer) {

            case Utility.FOUR_PLAYERS:
                handleFourPlayersButton();
                break;
            case Utility.THREE_PLAYERS:
                handleThreePlayersButton();
                break;
            case Utility.TWO_PLAYERS:
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

        return   atleastOnePlayerIsHuman()&&isLegalConditions;
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
        return isNumOfPlayersSet() && isGameNameSet() && isDiffNames() &&isAllPlayersSet();
    }

    private boolean isGameNameSet() {
        if (isCurrMsgSameAs(EMPTY_GAME_NAME_MSG)) {
            clearMsg();
        }
        if (gameName.getText() != null) {
            if (isValidTextField(gameName.getText())) {
                return true;
            }
        }
        this.errorMsg.setText(this.EMPTY_GAME_NAME_MSG);
        return false;
    }

    private boolean isCurrMsgSameAs(String msg) {
        return this.errorMsg.getText().equals(msg);

    }

    private void clearMsg() {
        errorMsg.setText(Utility.EMPTY_STRING);
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
        if (isCurrMsgSameAs(NO_HUMAN_MSG)) {
            clearMsg();
        }
        for (int i = 0; i < getNumOfPlayers() && !foundHuman; i++) {
            foundHuman = !this.checkBoxList.get(i).isSelected();
        }
        if (!foundHuman) {
            this.errorMsg.setText(NO_HUMAN_MSG);
        }
        return foundHuman;
    }

    private ArrayList<String> getPlayersTextFieldList() {
        ArrayList<String> sPlayersNames = new ArrayList<String>();
        for (TextField fName : this.playersNames) {
            String sName = fName.getText();
            if (this.isValidTextField(sName)) {
                sPlayersNames.add(fName.getText());
            }
        }
        return sPlayersNames;
    }

    boolean isDiffNames() {
        
        boolean isDiffNames = Game.Settings.isValidPlayersNames(getPlayersTextFieldList());
        if (isCurrMsgSameAs(DUP_NAME_MSG)) {
            clearMsg();
        }
        if (!isDiffNames) {
            this.errorMsg.setText(DUP_NAME_MSG);
        }
        return isDiffNames;
    }

//    private void handelErrorMsg() {
//        String stringMsg;
//        
//            case Utility.EMPTY_GAME_NAME:
//                stringMsg = EMPTY_GAME_NAME_MSG;
//                break;
//            case Utility.NO_ERROR:
//            default:
//                stringMsg = Utility.EMPTY_STRING;
//                break;
//        }
//        this.errorMsg.setText(stringMsg);
//    }
    private int hasError() {
        int hasError = Utility.NO_ERROR;
        if (!isDiffNames()) {
            hasError = Utility.DUP_NAME;
        } else if (!isValidTextField(this.gameName.getText())) {
            hasError = Utility.EMPTY_GAME_NAME;
        }
        return hasError;

    }

    private int getNumOfComputerPlayers() {
        int numOfComputerPlayers=0;
        for (CheckBox cb : this.checkBoxList) {
            if(cb.isSelected()){
            numOfComputerPlayers++;
            }
        }
        return numOfComputerPlayers;
    }

    private void addComputerNames(ArrayList<String> sPlayersNames,int numOfComputerPlayers) {
       String computerName="";
        for (int i = 1; i <= numOfComputerPlayers; i++) {
            computerName = "COMP" + i;
            sPlayersNames.add(computerName);
        }
    }

}
