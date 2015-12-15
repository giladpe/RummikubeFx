/*
 * this class is responsible for contolling the game parameter selection scene
 */

package rummikub.view;

import rummikub.Rummikub;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javax.swing.text.Utilities;
import javax.xml.bind.JAXBException;
import rummikub.gameLogic.model.logic.Settings;
import org.xml.sax.SAXException;
import rummikub.gameLogic.view.ioui.Utils;


public class GameParametersController implements Initializable, ControlledScreen, ResetableScreen {



    // Constants:
    public static final int TWO_PLAYERS = 2;
    public static final int THREE_PLAYERS = 3;
    public static final int FOUR_PLAYERS = 4;
    public static final int PLAYER1 = 0;
    public static final int PLAYER2 = 1;
    public static final int PLAYER3 = 2;
    public static final int PLAYER4 = 3;
    private final String DUP_NAME_MSG = "Name is already exict!";
    private final String EMPTY_GAME_NAME_MSG = "Insert name for the game!";
    private final String NO_HUMAN_MSG = "Chose atleast one human player!";
    private final String RADIO_BUTTON_VAL_2 = "2"; 
    private final String RADIO_BUTTON_VAL_3 = "3";  
    private final String RADIO_BUTTON_VAL_4 = "4";  
    private final String EMPTY_STRING="";
    // FXML private members:
    @FXML private Button StartPlayingButton;
    @FXML private Label errorMsg;
    @FXML private TextField gameName;
    @FXML private HBox configPlayer1;
    @FXML private HBox configPlayer2;
    @FXML private HBox configPlayer3;
    @FXML private HBox configPlayer4;
    @FXML private TextField playerName1;
    @FXML private TextField playerName2;
    @FXML private TextField playerName3;
    @FXML private TextField playerName4;
    @FXML private CheckBox isComputer1;
    @FXML private CheckBox isComputer2;
    @FXML private CheckBox isComputer3;
    @FXML private CheckBox isComputer4;
    @FXML private ToggleGroup radioButtonGroup;
    @FXML private RadioButton B2;
    @FXML private RadioButton B3;
    @FXML private RadioButton B4;
    
    // Private members:
    private ScreensController myController;
    private final ArrayList<HBox> hBoxList = new ArrayList<>();
    private final ArrayList<CheckBox> checkBoxList = new ArrayList<>();
    private final ArrayList<TextField> playersNames = new ArrayList<>();
    private Settings gameSettings;

    //FXML Protected methods:
    
    @FXML
    protected void handleGameNameTextChange(ActionEvent event) {
        initStartPlayingButton();
    }

    @FXML
    protected void handlePlayerNameTextChange(ActionEvent event) {
        initStartPlayingButton();
    }

    @FXML
    protected void handleBackToMenuButtonAction(ActionEvent event) {
        this.myController.setScreen(Rummikub.MAINMENU_SCREEN_ID,this);
        //resetScreen();
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

        return atleastOnePlayerIsHuman() && isLegalConditions;
    }
    
    //TODO: finish wrting methods to work with scene
    @FXML
    protected void handleStartPlayingButtonAction(ActionEvent event) {
        String gameNameString = this.gameName.getText();
        int numOfPlayers = getNumOfPlayers();
        int numOfComputerPlayers = getNumOfComputerPlayers();
        ArrayList<String> sPlayersNames = getPlayersTextFieldList();
        PlayScreenController gameScreen = (PlayScreenController)this.myController.getControllerScreen(Rummikub.PLAY_SCREEN_ID);
        
        this.gameSettings = new Settings(gameNameString,numOfPlayers, numOfComputerPlayers, sPlayersNames);
        gameScreen.createNewGame(gameSettings);
        gameScreen.show();
        this.myController.setScreen(Rummikub.PLAY_SCREEN_ID,gameScreen);
        resetScreen();

//        //A: gillad dont forget to remove it please:
//        Controller test = new Controller();
//        try {
//            test.startNewGameWithSettings(gameSettings);
//        } catch (JAXBException | SAXException | FileNotFoundException ex) {
//            Logger.getLogger(GameParametersController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    // Private methods:
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
    }
    
    private boolean isNumOfPlayersSet() {
        return this.radioButtonGroup.getSelectedToggle() != null;
    }

    private int getNumOfPlayers() {
        return Integer.parseInt(((RadioButton)radioButtonGroup.getSelectedToggle()).getText());
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
        return isNumOfPlayersSet() && isGameNameSet() && isDiffNames() && isAllPlayersSet();
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
        errorMsg.setText(EMPTY_STRING);
    }

    private void handleTwoPlayersButton() {
        resetPlayerField(PLAYER3);
        resetPlayerField(PLAYER4);
        setVisableFildsAfterRadioButtonSelection(TWO_PLAYERS);
    }

    private void handleThreePlayersButton() {
        resetPlayerField(PLAYER4);
        setVisableFildsAfterRadioButtonSelection(THREE_PLAYERS);
    }

    private void handleFourPlayersButton() {
        setVisableFildsAfterRadioButtonSelection(FOUR_PLAYERS);
    }
    
    private void setVisableFildsAfterRadioButtonSelection(int numOfFildsToSetVisable) {
        for (int i = 0; i < numOfFildsToSetVisable; i++) {
            this.hBoxList.get(i).setVisible(true);
        }
        
        for (int i = numOfFildsToSetVisable; i < this.hBoxList.size(); i++) {
            this.hBoxList.get(i).setVisible(false);
        }
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
        ArrayList<String> sPlayersNames = new ArrayList<>();
        this.playersNames.stream().forEach((fName) -> {
            String sName = fName.getText();
            if (this.isValidTextField(sName)) {
                sPlayersNames.add(fName.getText());
            } });
        return sPlayersNames;
    }

    boolean isDiffNames() {
        //////////////////need to check if there is methode 
        boolean isDiffNames = Settings.isValidPlayersNames(getPlayersTextFieldList());
        if (isCurrMsgSameAs(DUP_NAME_MSG)) {
            clearMsg();
        }
        if (!isDiffNames) {
            this.errorMsg.setText(DUP_NAME_MSG);
        }
        return isDiffNames;
    }

    private int getNumOfComputerPlayers() {
        int numOfComputerPlayers = 0;
        numOfComputerPlayers = this.checkBoxList.stream().filter((cb) -> (cb.isSelected())).map((_item) -> 1).reduce(numOfComputerPlayers, Integer::sum);
        
        return numOfComputerPlayers;
    }

    private void resetFeilds (Iterable collectionToReset, Consumer action) {
        collectionToReset.forEach(action);
    }
    
    //Public methods:
    @Override
    public void setScreenParent(ScreensController parentScreen) {
        this.myController = parentScreen;
    }

    @Override
    public void resetScreen() {
        this.errorMsg.setText(EMPTY_STRING);
        resetFeilds(this.playersNames, (Consumer) (Object playerName) -> {
            ((TextField)playerName).setText(EMPTY_STRING);
            ((TextField)playerName).setDisable(false);
        }); 
        resetFeilds(this.radioButtonGroup.getToggles(), (Consumer)(Object rButton) -> {((RadioButton)rButton).setSelected(false);});
        resetFeilds(this.checkBoxList, (Consumer)(Object cBox) -> {((CheckBox)cBox).setSelected(false);});
        resetFeilds(this.hBoxList, (Consumer)(Object hBox) -> {((HBox)hBox).setVisible(false);});
        this.gameName.setText(EMPTY_STRING);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
                
        initPlayersField();
        
        this.checkBoxList.stream().forEach((cb) -> {
            cb.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                initStartPlayingButton();});
        });
        
        this.playersNames.stream().forEach((nameTextField) -> {
            nameTextField.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                initStartPlayingButton();});
        });

        this.gameName.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            initStartPlayingButton();
        });

        this.radioButtonGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (radioButtonGroup.getSelectedToggle() != null) {
                handleRadioButtonChanged();
            }
        });

        this.radioButtonGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (radioButtonGroup.getSelectedToggle() != null) {
                handleRadioButtonChanged();
            }
        });
        
    }
    
    public Settings getGameSettings() {
        return gameSettings;
    }
}

//************************Test Zone*****************************//

//    private int getNumOfComputerPlayers() {
//        int numOfComputerPlayers = 0;
//        for (CheckBox cb : this.checkBoxList) {
//            if (cb.isSelected()) {
//                numOfComputerPlayers++;
//            }
//        }
//        return numOfComputerPlayers;
//    }

//    private void handleTwoPlayersButton() {
//        resetPlayerField(Utility.PLAYER3);
//        resetPlayerField(Utility.PLAYER4);
//        this.hBoxList.get(PLAYER1).setVisible(true);
//        this.hBoxList.get(PLAYER2).setVisible(true);
//        this.hBoxList.get(PLAYER3).setVisible(false);
//        this.hBoxList.get(PLAYER4).setVisible(false);
//    }

//    private void handleThreePlayersButton() {
//        resetPlayerField(Utility.PLAYER4);
//        this.hBoxList.get(PLAYER1).setVisible(true);
//        this.hBoxList.get(PLAYER2).setVisible(true);
//        this.hBoxList.get(PLAYER3).setVisible(true);
//        this.hBoxList.get(PLAYER4).setVisible(false);
//    }
//
//    private void handleFourPlayersButton() {
//        this.hBoxList.get(PLAYER1).setVisible(true);
//        this.hBoxList.get(PLAYER2).setVisible(true);
//        this.hBoxList.get(PLAYER3).setVisible(true);
//        this.hBoxList.get(PLAYER4).setVisible(true);
//    }

//    @Override
//    public void resetScreen() {
//        this.errorMsg.setText(Utility.EMPTY_STRING);
//        //this.hBoxList.forEach(null);
//        this.playersNames.forEach((playerName)-> {playerName.setText(Utility.EMPTY_STRING);});
//        this.radioButtonGroup.getToggles().forEach((rButton) -> {rButton.setSelected(false);});
//        this.checkBoxList.stream().forEach((cBox) -> { cBox.setSelected(false);});
//        this.gameName.setText(Utility.EMPTY_STRING);
//    }



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
//    private int hasError() {
//        int hasError = Utility.NO_ERROR;
//        if (!isDiffNames()) {
//            hasError = Utility.DUP_NAME;
//        } else if (!isValidTextField(this.gameName.getText())) {
//            hasError = Utility.EMPTY_GAME_NAME;
//        }
//        return hasError;
//
//    }


//        @FXML
//    protected void handleBackToMenuButtonAction(ActionEvent event) throws IOException {
//        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        primaryStage.setTitle("Main Menu");
//        URL url = this.getClass().getResource("MainMenu.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(url);
//        Parent root = (Parent) fxmlLoader.load(url.openStream());
//        Scene scene = new Scene(root);
//        (((Node) event.getSource()).getScene().getWindow()).hide();
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

//************************END*****************************//
