/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub;

import XmlClasses.Board;
import XmlClasses.PlayerType;
import rummikub.XmlUtils.XmlUtils;
import XmlClasses.Players;
import XmlClasses.Rummikub;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import rummikub.Engine.ExceptionHandler;
import rummikub.Engine.Game;
import rummikub.Engine.Utility;
import rummikub.oldView.MainMenu;
import rummikub.oldView.SubMenu;
import rummikub.oldView.View;

/**
 *
 * @author giladPe
 */
public class Controller {

    private final View ui;
    private Game game;

    //public static final int Zero = 0;
////////////////////////////////////////////////////////////////////////////////    
    public Controller() {
        ui = new View();
    }
////////////////////////////////////////////////////////////////////////////////

    public void runGame() {

        boolean errorHappend = true;

        while (errorHappend) {

            try {
                mainMenu();

            } catch (Exception ex) {

                ExceptionHandler.handleException(ex);

                try {
                    FileWriter fstream = new FileWriter("exception.txt", true);
                    BufferedWriter out = new BufferedWriter(fstream);
                    PrintWriter pWriter = new PrintWriter(out, true);
                    ex.printStackTrace(pWriter);
                } catch (Exception ie) {
                    throw new RuntimeException("Could not write Exception to file", ie);
                }
                continue;
            }
            errorHappend = false;
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void subMenu() throws JAXBException, SAXException, FileNotFoundException {
        //sub menu msg
        int PlyerChoice = new SubMenu().getChoice(); //get input
        //heandle input
        switch (PlyerChoice) {
            case Utility.Exit:
                System.exit(PlyerChoice);
                break;
            case Utility.NewGame:
                startNewGame();
                break;
            case Utility.SaveGame:
                saveGameToXml();
                break;
            case Utility.LoadGame:
                loadGameFromXml();
                break;
            case Utility.ResumeGame:
                //// print table and currplayer hand
                break;
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void mainMenu() throws JAXBException, SAXException, FileNotFoundException {

        int PlyerChoice = new MainMenu().getChoice(); //get input
        //heandle input
        switch (PlyerChoice) {
            case Utility.Exit:
                System.exit(PlyerChoice);
                break;
            case Utility.NewGame:
                startNewGame();
                break;
            case Utility.SaveGame:
                saveGameToXml();
                break;
            case Utility.LoadGame:
                loadGameFromXml();
                break;

        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void initNewGame() throws JAXBException, SAXException {
        //game.settings
        //boolean userWantToPlay = true;
        this.game = new Game(getSettings());
        game.setPlayersFromSettings();
        //getPlayersInfo();
        game.addStartTilesToPlayers();
        ///call start itearation////////////
    }

    public void initNewGame(Game.Settings settings) throws JAXBException, SAXException {
        //game.settings
        //boolean userWantToPlay = true;
        this.game = new Game(settings);
        game.setPlayersFromSettings();
        //getPlayersInfo();
        game.addStartTilesToPlayers();
        ///call start itearation////////////
    }
////////////////////////////////////////////////////////////////////////////////

    private void initNewSavedGame(Rummikub rummikub, int numOfHumanPlayers, int numOfComputerPlayers) throws JAXBException, SAXException {
        //boolean userWantToPlay = true;
        setSettings(rummikub, numOfHumanPlayers, numOfComputerPlayers);
    }
////////////////////////////////////////////////////////////////////////////////

    private void setSettings(Rummikub rummikub, int numOfHumanPlayers, int numOfComputerPlayers) {

        String gameName;
        Game.Settings settings = new Game.Settings();
        gameName = rummikub.getName();
        settings = new Game.Settings(gameName, numOfComputerPlayers, numOfHumanPlayers);
        this.game = new Game(settings);

    }

    private void setSettings(Rummikub rummikub, int numOfHumanPlayers, int numOfComputerPlayers, ArrayList<String> sPlayersNames) {
        String gameName;
        Game.Settings settings = new Game.Settings();
        gameName = rummikub.getName();
        settings = new Game.Settings(gameName, numOfComputerPlayers, numOfHumanPlayers, sPlayersNames);
        this.game = new Game(settings);

    }
////////////////////////////////////////////////////////////////////////////////

    private Game.Settings getSettings() {

        String gameName;
        int numOfPlayers, numComputers, numHumans;
        ArrayList<String> playersNames = new ArrayList<String>();

        ui.gameNameMsg();
        gameName = ui.getStringFromUser(true);

        ui.playersAmountMsg();
        numOfPlayers = ui.getIntFromUser(Utility.MinPlayers, Utility.MaxPlayers);

        ui.humansAmountMsg();
        numHumans = ui.getIntFromUser(Utility.MinChoice, numOfPlayers);

        numComputers = numOfPlayers - numHumans;

        playersNames = getPlayersNames(numOfPlayers, numHumans);
        return  new Game.Settings(gameName, numComputers, numHumans, playersNames);

    }

    private ArrayList<String> getPlayersNames(int numOfPlayers, int numOfHumans) {
        ArrayList<String> playersNamesList = new ArrayList<>();
        String playerName;

        for (int i = 1; i <= numOfHumans; i++) {
            ui.playerName(i);
            playerName = ui.getStringFromUser(true);
            while (!isValidPlayerName(playersNamesList, playerName)) {
                ui.usedNameMsg();
                playerName = ui.getStringFromUser(true);
            }
            playersNamesList.add(playerName);
        }
       

        ui.wellcomePlayersMsg(playersNamesList.toString());
        return playersNamesList;
    }
////////////////////////////////////////////////////////////////////////////////

//    private void getPlayersInfo() {
//
//        int numHuman = game.getNumberOfHumansPlayrs();
//        int numComp = game.getNumberOfComputerPlyers();
//        String playerName;
//
//        for (int i = 1; i <= numComp; i++) {
//
//            game.addComputerPlayer();
//        }
//
//        for (int i = 1; i <= numHuman; i++) {
//
//            ui.playerName(i);
//            playerName = ui.getStringFromUser(true);
//            while (!game.tryToAddHumanPlayer(playerName)) {
//                ui.usedNameMsg();
//                playerName = ui.getStringFromUser(true);
//            }
//        }
//        ui.wellcomePlayersMsg(game.getPlayersNames());
//        //init Computer names
//
//    }
////////////////////////////////////////////////////////////////////////////////

    private void gameIterations() throws JAXBException, SAXException, FileNotFoundException {

        boolean userWantToPlay = true;

        while (userWantToPlay) {

            game.saveCurrTableAndPlayer();
            userWantToPlay = gameSubIteration();
        }

    }
////////////////////////////////////////////////////////////////////////////////

    private String humanTurn() throws JAXBException, FileNotFoundException, SAXException {

        int userChoice;
        boolean firstTurnIteration = true;
        do {
            ui.printString(game.getGameTable().toString());
            ui.turnMsg(firstTurnIteration);
            ui.printString(game.getPlayersHeand());
            userChoice = ui.getIntFromUser(Utility.MinChoice, Utility.MaxTurnChoice);
            handleItrChoice(userChoice);
            firstTurnIteration = false;
        } while (userChoice != Utility.EndTurn);

        return game.endTurn();
    }
////////////////////////////////////////////////////////////////////////////////

    private String computerTurn() {

        String turnMoves = game.pcTurn();
        ui.printPcMove(turnMoves, game.getCurrPlayerName());
        return game.endTurn();
    }
////////////////////////////////////////////////////////////////////////////////

    private boolean gameSubIteration() throws JAXBException, SAXException, FileNotFoundException {

        boolean userWantToPlay = true;
        String playerName = game.getCurrPlayerName();

        if (game.isLastTurn()) {
            if (game.isCounterDone()) {
                ui.printEndDeckWinner(game.getWinner());
                userWantToPlay = false;
            }
            game.incLastTurnCounter();
        }
        if ((game.isCounterNotOver() || game.isNotLastTurn()) && userWantToPlay) {
            String endTurn;
            ui.turnMsg(game.getGameTable().toString(), playerName);

            if (game.isCurrPlayerHuman()) {

                endTurn = humanTurn();

            } else {

                endTurn = computerTurn();
            }
            ui.printEndTurnMsg(endTurn, playerName);
        }
        if (isPlayerDoneHand()) {
            userWantToPlay = false;
        } else {
            game.nextPlayer();
        }
        return userWantToPlay;

    }
////////////////////////////////////////////////////////////////////////////////

    private boolean isPlayerDoneHand() {

        if (!game.playerHasTilesInHand()) {

            ui.printEndHandWinner(game.getWinner());

            return true;
        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////

    private void handleItrChoice(int userChoice) throws JAXBException, SAXException, FileNotFoundException {

        switch (userChoice) {

            case Utility.MinChoice:
                subMenu();
                break;
            case Utility.NewSerie:
                newSerie();//TODO
                break;
            case Utility.AddToSerie:
                addTileToSerie();
                break;

            case Utility.TakeFromSerie:
                takeTileFromSerie();
                break;
            case Utility.EndTurn:
                ///
                break;
            case 5:
                editHand();
                break;
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void newSerie() {

        int choice = 0, tileIndex;

        game.addNewSerie();

        do {

            if (game.playerHasTilesInHand()) {

                ui.printString(game.getPlayersHeand());
                ui.chooseTileIndexFromHandMsg();
                tileIndex = ui.getIntFromUser(0, game.getCurrPlayer().getHandSize() - 1);
                game.addTileToSerie(game.getNumOfSeries() - 1, tileIndex);
                ui.addTileMsg();
                choice = ui.getIntFromUser(Utility.MinChoice, Utility.MaxNewSerieChoice);

            } else {

                choice = Utility.Done;
                ui.emptyHandMsg();
            }
        } while (choice == Utility.AddTile);

    }
////////////////////////////////////////////////////////////////////////////////

    private void addToExictSerie() {

        int serieIndex, serieSize, tilePosition, tileHandIndex;

        if (game.playerHasTilesInHand()) {

            ui.chooseTileIndexFromHandMsg();
            tileHandIndex = ui.getIntFromUser(Utility.MinChoice, game.getCurrPlayer().getHandSize() - 1);

            ui.chooseSerieMsg();
            serieIndex = ui.getIntFromUser(Utility.MinChoice, game.getNumOfSeries() - 1);
            serieSize = game.getSerieSize(serieIndex);

            ui.chooseTileIndexMsg();
            tilePosition = ui.getIntFromUser(0, serieSize);
            game.addTileToSerie(serieIndex, tileHandIndex, tilePosition);

        } else {
            ui.emptyHandMsg();
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void takeFromSerie() {

        int serieIndex, serieSize, tilePosition;

        ui.chooseSerieMsg();
        serieIndex = ui.getIntFromUser(0, game.getNumOfSeries() - 1);
        serieSize = game.getSerieSize(serieIndex);

        if (serieSize > 0) {

            ui.chooseTileIndexMsg();
            tilePosition = ui.getIntFromUser(0, serieSize - 1);
            game.takeTileFromSerie(serieIndex, tilePosition);
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void loadGameFromXml() throws JAXBException, SAXException, FileNotFoundException {

        int CurrPlayerToAdd = 0;

        XmlClasses.Rummikub savedRummiKubGame = new XmlClasses.Rummikub();

        ui.chooseLoadPath();//get the path from user
        String userInput = ui.getStringFromUser(false);

        savedRummiKubGame = XmlUtils.getSavedGame(userInput);
        startNewGameFromXml(savedRummiKubGame);

        for (Players.Player player : savedRummiKubGame.getPlayers().getPlayer()) { // go overs all players we loaded

            if (player.getType() == PlayerType.HUMAN) {

                game.tryToAddHumanPlayer(player.getName());//sets the name from XmlUtils

            } else {
                game.addComputerPlayer(player.getName());
                
            }
            if (player.getName().equals(savedRummiKubGame.getCurrentPlayer())) {
                game.setCurrPlayer(CurrPlayerToAdd);
            }
            if (!getTilesForPlayerFromXml(player, CurrPlayerToAdd)) {//get tiles for current player
                ui.printErrorInLoadedGame();// if we get here there is a problem in file
                mainMenu();
            }
            game.getPlayers().get(CurrPlayerToAdd).setFirstMove(!(player.isPlacedFirstSequence()));
            CurrPlayerToAdd++;
        }
        if (!getBoardFromXml(savedRummiKubGame.getBoard())) {//set the game table

            ui.printErrorInLoadedGame();
            mainMenu();
        }

        if (game.isLoadedGameLegit()) {//check if all the details we got from XmlUtils is legit

            game.initXmlGame();
            gameIterations();//start the game
            mainMenu();

        } else {
            ui.printErrorInLoadedGame();
            mainMenu();
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private boolean getBoardFromXml(Board board) {

        boolean isBoardLegit = false;

        isBoardLegit = game.setGameTableFromXml(board);//sets the game table

        return isBoardLegit;
    }
////////////////////////////////////////////////////////////////////////////////

    private boolean getTilesForPlayerFromXml(Players.Player player, int playerIndex) {

        int numOfTiles = player.getTiles().getTile().size();
        boolean isTileLegit = false;

        for (int i = 0; i < numOfTiles; i++) {

            isTileLegit = game.getPlayers().get(playerIndex).setNewTileFromXml(player.getTiles().getTile().get(i).getColor().value(),
                    player.getTiles().getTile().get(i).getValue());
        }
        return isTileLegit;
    }
////////////////////////////////////////////////////////////////////////////////

    private void saveGameToXml() throws JAXBException, SAXException, FileNotFoundException {

        XmlClasses.Rummikub RummikubGameToSave = new XmlClasses.Rummikub();
        int userChoice = 0;
        String FilePath;
        if (game.isIsGameSavedBefore()) {// check if game as been saved before

            ui.chooseSaveOption();//if so the user should choose if to save to last place of new one
            userChoice = ui.getIntFromUser(0, 1);

            if (userChoice == 1) {// if the user wants a save to a new file

                ui.chooseSavePath();//choose a new path
                FilePath = ui.getStringFromUser(false);
                game.setPathName(FilePath);
                prepreGameSettingsToSave(RummikubGameToSave, FilePath);

            } else {// if not then we save to last saved file path

                prepreGameSettingsToSave(RummikubGameToSave, game.getLastSavedPath());

            }
        } else {//if it's the first save the user must enter full path
            ui.chooseSavePath();
            FilePath = ui.getStringFromUser(false);
            game.setPathName(FilePath);
            game.setIsGameSavedBefore(true);
            prepreGameSettingsToSave(RummikubGameToSave, FilePath);
        }

    }
////////////////////////////////////////////////////////////////////////////////

    private void prepreGameSettingsToSave(Rummikub rummikub, String path) throws JAXBException, SAXException, FileNotFoundException {

        XmlUtils.createGameToSaveForXml(game, rummikub, path);

    }
////////////////////////////////////////////////////////////////////////////////

    private void startNewGame() throws JAXBException, SAXException, FileNotFoundException {

        initNewGame();
        gameIterations();
        mainMenu();
    }

    public void startNewGameWithSettings(Game.Settings settings)throws JAXBException, SAXException, FileNotFoundException {
        initNewGame(settings);
        gameIterations();
        mainMenu();
    }
////////////////////////////////////////////////////////////////////////////////

    private void startNewGameFromXml(Rummikub rummikub) throws JAXBException, SAXException {

        int numOfHumanPlayers = 0, numOfComputerPlayers = 0;

        for (Players.Player player : rummikub.getPlayers().getPlayer()) {

            if (player.getType() == PlayerType.HUMAN) {

                numOfHumanPlayers++;
            }
            if (player.getType() == PlayerType.COMPUTER) {

                numOfComputerPlayers++;
            }

        }
        initNewSavedGame(rummikub, numOfHumanPlayers, numOfComputerPlayers);
    }
////////////////////////////////////////////////////////////////////////////////

    private void editHand() {

        int choice;

        if (game.currPlayerHandSize() > 1) {

            ui.orgenaizePlayerHandMsg();
            choice = ui.getIntFromUser(Utility.MinChoice, Utility.MaxEditHandChoice);

            if (choice == 1) {//organize the player hand

                game.OrganizeCurrPlayerHand();

            } else if (choice == 2) {

                ui.swapIndexMsg();
                int index1 = ui.getIntFromUser(Utility.MinChoice, game.getCurrPlayer().getHandSize());
                int index2 = ui.getIntFromUser(Utility.MinChoice, game.getCurrPlayer().getHandSize());
                game.getCurrPlayer().swap(index1, index2);
            }
        } else {
            ui.onlyOneTileMsg();
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void addTileToSerie() {

        if (game.isThereSerieOnTable()) { //check if the table is not empty
            ui.noSerieInTableMsg();//if so we notifty the user

        } else {

            addToExictSerie(); // add tile to a serie on table
        }
    }
////////////////////////////////////////////////////////////////////////////////

    private void takeTileFromSerie() {

        if (game.isThereSerieOnTable()) {

            ui.noSerieInTableMsg();

        } else {
            takeFromSerie(); // takes tile from a serie on the table
        }
    }

    public boolean isValidPlayerName(ArrayList<String> sPlayersNameList, String sPlayerName) {
        return !(sPlayerName.matches(Utility.COMPUTER_NAME) || simmilarToOneName(sPlayersNameList, sPlayerName));
    }

    private static boolean simmilarToOneName(ArrayList<String> playerNames, String sPlayerName) {
        boolean bIsFound = false;

        for (int i = 0; i < playerNames.size() && !bIsFound; i++) {
            bIsFound = playerNames.get(i).equals(sPlayerName);
        }

        return bIsFound;
    }

////////////////////////////////////////////////////////////////////////////////
}
