/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.Engine;

import rummikub.Engine.TilesLogic.Tile;
import rummikub.Engine.Player.ComputerPlayer;
import rummikub.Engine.Player.HumanPlayer;
import rummikub.Engine.Player.Player;
import java.util.ArrayList;
import java.util.Iterator;
import XmlClasses.Board;

public class Game {

    private int lastTurnCounter = 4;
    private boolean lastTurn = false;
    private boolean isGameSavedBefore = false;
    private int currPlayer;
    private ArrayList<Player> players;
    private ArrayList<Tile> tmpHand;
    private Table gameTable;
    private Table tmpTable;
    private GameDeck gameDeck;
    private String lastSavedPath;
    public Settings settings;
    private Object game;

/////////////////////////////////Constractor////////////////////////////////////////////
    public Game(Settings settings) {

        this.settings = settings;
        this.players = new ArrayList<>();
        this.gameTable = new Table();
        gameDeck = new GameDeck();
        currPlayer = (int) (Math.random() * settings.numberOfPlayers);//generate random player to start

    }

    /**
     * ************************************************************************************
     */
/////////////////////////////////Adding functions////////////////////////////////////////
    public void addNewSerie() {
        gameTable.addSerie();
    }

    public void setPlayersFromSettings() {
        ArrayList<String> sPlayersNames = settings.getPlayerNames();
        int numOfComputer = settings.numberOfComputerPlyers;
        int i = 0;
        for (; i < sPlayersNames.size(); i++) {
            tryToAddHumanPlayer(sPlayersNames.get(i));
        }
        for (i = 1; i <= numOfComputer; i++) {
            addComputerPlayer();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void takeTileFromSerie(int serieIndex, int tileIndex) {

        Tile tile = gameTable.getTileFromSerie(serieIndex, tileIndex);
        getCurrPlayer().addTileToHand(tile);//takes a tile from table to hand
        gameTable.removeTileFromSerie(serieIndex, tileIndex);// and remove it from the serie

        if (gameTable.getSerieSize(serieIndex) < 1) {
            //incase it's the last tile
            gameTable.getSerieList().remove(serieIndex);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void addTileToSerie(int serieIndex, int tileIndex) {

        Tile tile = getCurrPlayer().getHand().get(tileIndex);
        gameTable.getSerie(serieIndex).addTile(tile);
        getCurrPlayer().getHand().remove(tileIndex);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void addTileToSerie(int serieIndex, int tileIndex, int tilePosition) {

        Tile tile = getCurrPlayer().getHand().get(tileIndex);
        gameTable.getSerie(serieIndex).addTile(tile, tilePosition);
        getCurrPlayer().getHand().remove(tileIndex);
    }
/////////////////////////////////////////////////////////////////////////////////////////   

    public void addHumanPlayer(String playerName) {

        players.add(new HumanPlayer(playerName));
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void addComputerPlayer(String name) {
        players.add(new ComputerPlayer(name));
    }

    public void addComputerPlayer() {
        players.add(new ComputerPlayer());
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean tryToAddHumanPlayer(String playerName) {
        boolean retVal = true;
        if (isValidPlayerName(playerName)) {
            addHumanPlayer(playerName);
        } else {
            retVal = false;
        }
        return retVal;
    }

    /**
     * *********************************************************************************
     */
////////////////////////////////Iteration functions///////////////////////////////////
    public String endTurn() {

        int withdraw = 0;
        String retVal = "";
        if (isSameTable()) { // check if the player tried to change sometiong on the table

            if (withdrawTile()) {//if not take a tile and continue
                retVal = "Withdraw 1 tile ";
            } else {
                retVal = "No more tiles in deck";
            }

        } else if (!isValidMove()) {//check if the user made a valid move on the table

            restoreHandAndTable();//if not we restore the table and player hand
            for (int i = 0; i < 3; i++) {//and withdraw 3 tiles

                if (withdrawTile()) {

                    withdraw++;
                }
            }
            if (withdraw < 3) {
                retVal = "Withdraw " + withdraw + " tiles there are no more tiles in deck ";
            } else {
                retVal = "Withdraw 3 tiles ";
            }
            retVal += "Invalid move!";
        } else {
            if (getTurnScore() < 30 && getCurrPlayer().isFirstMove()) {

                restoreHandAndTable();
                retVal += "First move most to be 30+ value ";
                withdrawTile();

            } else {

                getCurrPlayer().setNotFirstMove();
                getCurrPlayer().addScore(getTurnScore());
                retVal += "Great!";

            }

        }
        return retVal;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void addStartTilesToPlayers() {

        for (int i = 0; i < 14; i++) {

            players.stream().forEach((itPlayer) -> {
                //take kube from table deck and place it in player deck//
                itPlayer.addTileToHand(gameDeck.withdraw());
            });
        }

    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void nextPlayer() {

        gameTable.TilesBelongToTable();
        incrementCurrPlayer();

    }
/////////////////////////////////////////////////////////////////////////////////////////   

    private boolean withdrawTile() {

        boolean res = false;

        if (gameDeck.getGameDeckSize() > 0) {

            Tile tile = gameDeck.withdraw();
            getCurrPlayer().addTile(tile);
            if (getCurrPlayer().isPlayerHuman()) {

            }
            res = true;
        }
        if (gameDeck.getGameDeckSize() == 0 && lastTurn == false) {

            lastTurn = true;
            lastTurnCounter = -(this.getNumberOfPlayers() + 1);
        }
        return res;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void OrganizeCurrPlayerHand() {

        getCurrPlayer().organizeHand();
    }
/////////////////////////////////////////////////////////////////////////////////////////   

    public String getWinner() {

        int minHandSize = 156;
        String minSizeName = "";

        for (Player player : players) {

            int handSize = player.getHandSize();

            if (handSize < minHandSize) {

                minHandSize = handSize;
                minSizeName = player.getName();

            } else if (handSize == minHandSize) {

                minSizeName += (" and " + player.getName());
            }
        }
        return minSizeName;
    }

    /**
     * *********************************************************************************
     */
///////////////////////////////Validation functions//////////////////////////////////    
    public boolean isSameTable() {

        boolean retVal = gameTable.equals(tmpTable);
        return retVal;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean isValidMove() {

        boolean retVal = gameTable.isValidTable();
        if (retVal) {
            //check if there is no table's tiles in player hand
            if (!getCurrPlayer().isValidHand()) {

                retVal = false;
            }
        }
        return retVal;
    }
/////////////////////////////////////////////////////////////////////////////////////////    

    private boolean isValidPlayerName(String playerName) {//need to transfer to Game class

        boolean retVal = true;

        for (Player itPlayer : players) {

            if (itPlayer.getName().equals(playerName)) {
                retVal = false;
            }
        }

        return retVal;
    }

    /**
     * ********************************************************************************
     */
////////////////////////////////Save and restore functions///////////////////////////    
    private void restoreHandAndTable() {
        gameTable = tmpTable;
        getCurrPlayer().setHand(tmpHand);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void saveCurrTableAndPlayer() {
        saveTable();
        saveCurrPlayerHand();
    }
/////////////////////////////////////////////////////////////////////////////////////////

    private void saveTable() {
        tmpTable = new Table(gameTable);

    }
/////////////////////////////////////////////////////////////////////////////////////////

    private void saveCurrPlayerHand() {

        tmpHand = getCurrPlayer().clone();
    }

    /**
     * ******************************************************************************
     */
/////////////////////////////////////PC functions////////////////////////////////////////
    private String pcTryAddSerie(ComputerPlayer ai) {

        ArrayList<ArrayList<Tile>> serieList;
        String pcMoves = "";

        serieList = ai.getAllSerieInHand();

        if (ai.isFirstMove()) {

            if (!ai.isUnderThirty(serieList)) {

                pcMoves = addPcSeries(serieList, ai);
                ai.setFirstMove(false);
            }
        } else {
            pcMoves = addPcSeries(serieList, ai);
        }
        return pcMoves;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    private String pcTryAddTiles(ComputerPlayer ai) {

        String pcMoves = "";
        ArrayList<Tile> hand = ai.getHand();
        ArrayList<Serie> serieList = gameTable.getSerieList();

        boolean deleteNeeded = false;
        int serieIndex = -1;

        for (Iterator<Tile> it = hand.iterator(); it.hasNext();) {
            Tile tile = it.next();

            for (Iterator<Serie> itr = serieList.iterator(); itr.hasNext() && !deleteNeeded;) {

                Serie serie = itr.next();
                if (gameTable.tryAddTileToSerie(serie, tile)) {

                    serieIndex = getSerieIndex(serie);
                    deleteNeeded = true;

                }
            }
            if (deleteNeeded) {
                pcMoves += "add tile: " + tile + " to serie: " + serieIndex + "\n";
                it.remove();
            }
            deleteNeeded = false;
        }
        return pcMoves;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public String pcTurn() {

        String pcMoves = "";
        String addTile = "";
        ComputerPlayer ai = (ComputerPlayer) getCurrPlayer();
        pcMoves += pcTryAddSerie(ai);

        if (!this.getCurrPlayer().isFirstMove()) {

            do {
                addTile = pcTryAddTiles(ai);
                pcMoves += addTile;
            } while (!addTile.equals(Utility.EMPTY_STRING));
        }
        return pcMoves;
    }
/////////////////////////////////////////////////////////////////////////////////////////    

    private String addPcSeries(ArrayList<ArrayList<Tile>> serieList, ComputerPlayer ai) {

        String pcMoves = "";
        for (ArrayList<Tile> tilesList : serieList) {

            ArrayList<Tile> serie = tilesList;

            for (Iterator<Tile> it = tilesList.iterator(); it.hasNext();) {
                Tile tile = it.next();

                for (Iterator<Tile> itr = ai.getHand().iterator(); itr.hasNext();) {
                    Tile Playertile = itr.next();

                    if (Playertile == tile) {

                        itr.remove();
                    }
                }
            }
            gameTable.addSerie(serie);
            pcMoves += "add serie:" + serie.toString() + "\n";
        }
        return pcMoves;
    }

    /**
     * *********************************************************************
     */
////////////////////////////////////Xml functions////////////////////////////////////
    public boolean isLoadedGameLegit() {

        if (!this.getGameTable().isValidTable()) {

            System.out.println("ERROR: the table you loaded is not legit,\nplease choose what you wish to do next");
            return false;
        }
        if (this.getGameTable().getScore() < 30 && this.getGameTable().getSerieList().size() > 0) {

            System.out.println("ERROR: the table you loaded is not legit!\nplease choose what you wish to do next");
            return false;
        }
        if (!isScoreOnTableLegit()) {

            System.out.println("ERROR: the table you loaded is not legit!\nplease choose what you wish to do next");
            return false;
        }
        return this.isAllTilesAccountedFor();
    }
//////////xm///////////////////////////////////////////////////////////////////////////////   

    public void initXmlGame() {

        for (Player player : players) {

            player.belongToPlayer();
        }
        getGameTable().TilesBelongToTable();
    }
/////////////////////////////////////////////////////////////////////////////////////////  

    private boolean isAllTilesAccountedFor() {

        for (Player player : players) {

            for (Tile tile : player.getHand()) {

                if (!this.gameDeck.getGameDeck().remove(tile)) {

                    return false;
                }
            }
        }
        for (Serie serie : this.getGameTable().getSerieList()) {

            for (Tile serieTile : serie.getSerie()) {

                if (!this.gameDeck.getGameDeck().remove(serieTile)) {

                    return false;
                }
            }
        }
        if (this.gameDeck.getGameDeck().isEmpty()) {

            this.setLastTurn(true);
        }
        return true;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean setGameTableFromXml(Board board) {

        int numOfSeries = board.getSequence().size();

        for (int i = 0; i < numOfSeries; i++) {

            if (!gameTable.addSerieFromXml(board.getSequence().get(i))) {

                return false;
            }
        }
        return true;
    }
/////////////////////////////////////////////////////////////////////////////////////////    

    public void setPlayersNotFirstMove() {

        for (Player player : players) {

            player.setFirstMove(false);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////    

    public void setPlayersFirstMove() {

        for (Player player : players) {

            player.setFirstMove(true);

        }
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void setLastTurn(boolean lastTurn) {

        this.lastTurn = lastTurn;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public String getLastSavedPath() {

        return lastSavedPath;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void setPathName(String FilePath) {

        this.lastSavedPath = FilePath;
    }
///////////////////////////////Getters functions/////////////////////////////////// /////   

    public boolean playerHasTilesInHand() {
        return (getCurrPlayer().getHandSize() > 0);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean isThereSerieOnTable() {
        return (getNumOfSeries() < 1);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean isCounterDone() {
        return (lastTurnCounter == 0);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean isNotLastTurn() {
        return !lastTurn;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public int currPlayerHandSize() {
        return getCurrPlayer().getHandSize();
    }
/////////////////////////////////////////////////////////////////////////////////////////

    private int getSerieIndex(Serie serie) {
        return gameTable.getSerieIndex(serie);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean isCounterNotOver() {
        return getLastTurnCounter() != 0;
    }

    /////////////////////////////////////////////////////////////////////////////////////////   
    public int getLastTurnCounter() {
        return lastTurnCounter;
    }

    /////////////////////////////////////////////////////////////////////////////////////////   
    public int getNumOfSeries() {
        return gameTable.getSerieList().size();
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean isIsGameSavedBefore() {
        return isGameSavedBefore;
    }

    /////////////////////////////////////////////////////////////////////////////////////////   
    public boolean isLastTurn() {
        return lastTurn;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public GameDeck getGameDeck() {
        return gameDeck;
    }
/////////////////////////////////////////////////////////////////////////////////////////   

    public int getSerieSize(int index) {
        return gameTable.getSerieSize(index);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public String getCurrPlayerName() {

        return players.get(currPlayer).getName();
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public int getCurrPlayerIndex() {
        return currPlayer;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public Player getCurrPlayer() {
        return players.get(currPlayer);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public boolean isCurrPlayerHuman() {
        return getCurrPlayer().isPlayerHuman();
    }
/////////////////////////////////////////////////////////////////////////////////////////   

    public int getTurnScore() {
        return (gameTable.getScore() - tmpTable.getScore());
    }
/////////////////////////////////////////////////////////////////////////////////////////    

    public Settings getSettings() {
        return settings;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Player> getPlayers() {
        return players;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public Table getGameTable() {
        return gameTable;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public int getNumberOfHumansPlayrs() {
        return settings.numberOfHumansPlayrs;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public String getGameName() {
        return settings.gameName;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public int getNumberOfPlayers() {
        return settings.numberOfPlayers;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public int getNumberOfComputerPlyers() {
        return settings.numberOfComputerPlyers;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public String getPlayersNames()//may be need to move 
    {
        String playersNames = "";
        playersNames = players.stream().map((itPlayer) -> itPlayer.getName() + ", ").reduce(playersNames, String::concat);
        playersNames = playersNames.substring(0, playersNames.length() - 2); //slice the last ", "
        return playersNames;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public String getPlayersHeand() {
        String retVal = "this is your heand: " + "\n" + players.get(currPlayer).toString();
        return retVal;
    }
///////////////////////////////Setters functions/////////////////////////////////////////

    public void incLastTurnCounter() {
        lastTurnCounter++;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void incrementCurrPlayer() {
        currPlayer = ((currPlayer + 1) % settings.numberOfPlayers);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void setIsGameSavedBefore(boolean isGameSavedBefore) {
        this.isGameSavedBefore = isGameSavedBefore;
    }
/////////////////////////////////////////////////////////////////////////////////////////    

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
/////////////////////////////////////////////////////////////////////////////////////////

    public void setGameTable(Table gameTable) {
        this.gameTable = gameTable;
    }
/////////////////////////////////////////////////////////////////////////////////////////   

    public void setCurrPlayer(int CurrPlayer) {
        this.currPlayer = CurrPlayer;
    }

    private boolean isScoreOnTableLegit() {

        int playersPlayedBefore = 0, sumOfTilesOnTable = 0;

        for (Player player : players) {

            if (!player.isFirstMove()) {

                playersPlayedBefore++;
            }
        }
        for (Serie serie : this.gameTable.getSerieList()) {

            sumOfTilesOnTable += serie.getScore();
        }
        if (sumOfTilesOnTable < 30 * playersPlayedBefore) {

            return false;
        }
        return true;
    }
///////////////////////////////// Inner class////////////////////////////////////////////

    public static class Settings {

        String gameName;
        int numberOfPlayers;
        int numberOfComputerPlyers;
        int numberOfHumansPlayrs;
        ArrayList<String> playersNameString;

        public Settings() {
        }

        public Settings(String gameName, int numberOfComputerPlyers, int numberOfHumansPlayrs) {
            this.gameName = gameName;
            this.numberOfComputerPlyers = numberOfComputerPlyers;
            this.numberOfPlayers = numberOfComputerPlyers + numberOfHumansPlayrs;
            this.numberOfHumansPlayrs = numberOfHumansPlayrs;
        }

        public Settings(String gameName, int numberOfComputerPlyers, int numberOfHumansPlayrs, ArrayList<String> playersNameString) {
            this.gameName = gameName;
            this.numberOfComputerPlyers = numberOfComputerPlyers;
            this.numberOfPlayers = numberOfComputerPlyers + numberOfHumansPlayrs;
            this.numberOfHumansPlayrs = numberOfHumansPlayrs;
            this.playersNameString = playersNameString;
        }

        public boolean isValidPlayerName(ArrayList<String> sPlayersNameList, String sPlayerName) {
            return !(isPlayerNameHasCompterName(sPlayerName) || simmilarToOneName(sPlayersNameList, sPlayerName));
        }

        static public boolean isValidPlayersNames(ArrayList<String> namesList) {
            return !isPlayersNamesHasCompterName(namesList) && hasDiffNames(namesList);
        }

        private static boolean simmilarToOneName(ArrayList<String> playerNames, String sPlayerName) {
            boolean bIsFound = false;

            for (int i = 0; i < playerNames.size() && !bIsFound; i++) {
                bIsFound = playerNames.get(i).equals(sPlayerName);
            }

            return bIsFound;
        }

        static private boolean isPlayerNameHasCompterName(String name) {
            return name.matches(Utility.COMPUTER_NAME);
        }

        static private boolean isPlayersNamesHasCompterName(ArrayList<String> namesList) {
            boolean hasComputerName = false;
            for (String name : namesList) {
                if (isPlayerNameHasCompterName(name)) {
                    hasComputerName = true;
                }
            }
            return hasComputerName;
        }

        private static boolean hasDiffNames(ArrayList<String> playersNames) {
            boolean foundSimmilar = false;
            for (int i = 0; i < (playersNames.size() - 1) && !foundSimmilar; i++) {
                for (int j = i + 1; j < playersNames.size() && !foundSimmilar; j++) {
                    foundSimmilar = playersNames.get(i).equals(playersNames.get(j));
                }
            }
            return !foundSimmilar;
        }

        public ArrayList<String> getPlayerNames() {
            return playersNameString;
        }

    }
/////////////////////////////////////////////////////////////////////////////////////////
}
