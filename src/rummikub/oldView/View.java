
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.oldView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {

////////////////////////////////////////////////////////////////////////////////    
    public String getStringFromUser(boolean ignoreWhiteSpaces) {
        
        String inputString = "";
        Scanner scanner;
        boolean notfirstTime = false;
        do {
            if (notfirstTime) {
                
                System.out.println("Insert at least one char!");
            }
            scanner = new Scanner(System.in);
            inputString = scanner.nextLine();
            
            if (ignoreWhiteSpaces) {
                
                inputString = inputString.replace(" ", "");
                inputString = inputString.replace("\n", "");
                notfirstTime = true;
            }
        } while (inputString.equals("") && ignoreWhiteSpaces);
        
        return inputString;
    }
////////////////////////////////////////////////////////////////////////////////
    public int getIntFromUser(int from, int to) {

        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        do {
            validInput = true;
            //read an integer from the console
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException exception) {
                
                System.out.println("This is not a number!");
                validInput = false;
                //(-1) use as flag for range message
                choice = -1;
                scanner.nextLine();
            }
            //check if choice in  range 
            if ((choice < from || choice > to) && choice != -1) {
                
                validInput = false;
                System.out.println("Please insert number in range (" + from + "-" + to + ")!");
            }
        } while (validInput == false);
        
        return choice;
    }
////////////////////////////////////////////////////////////////////////////////
    public void continueMsg() {

        System.out.println("To continue press(1)");
        System.out.println("To the subMenue (0)");
    }
////////////////////////////////////////////////////////////////////////////////
    public void chooseSaveOption() {

        System.out.println("Save As (1)");
        System.out.println("Save To Last Place(0)");
    }
////////////////////////////////////////////////////////////////////////////////
    public void chooseSavePath() {

        System.out.println("Please Enter Full Path Where You Wish To Save The File\n");
    }
////////////////////////////////////////////////////////////////////////////////
    public void addTileMsg() {

        System.out.println("To add tile to serie (1)");
        System.out.println("To end serie(2)");
        System.out.println("To cancel(0)");
    }
////////////////////////////////////////////////////////////////////////////////
    public void serieNotValidMsg() {

        System.out.println("The serie you were trying to add is not leagel\n");
        System.out.println("If you wish to try adding a new serie enter (1)\n");
    }
////////////////////////////////////////////////////////////////////////////////
    public void gameNameMsg() {
        System.out.println("Please insert game name: ");
    }
////////////////////////////////////////////////////////////////////////////////
    public void playersAmountMsg() {
        System.out.println("How many players in the game: ");
    }
////////////////////////////////////////////////////////////////////////////////
    public void humansAmountMsg() {
        System.out.println("How many humans players: ");
    }
////////////////////////////////////////////////////////////////////////////////   
    public String loadingFromFileMenu() {
        
        String file = "";
        System.out.println("Please insert file location: ");
        file = getStringFromUser(false);
        return file;
    }
////////////////////////////////////////////////////////////////////////////////
    public void playerName(int playerNum) {
        
        System.out.println("Please insert name for player number " + playerNum + ": ");
    }
////////////////////////////////////////////////////////////////////////////////
    public void usedNameMsg() {
        
        System.out.println("This name has been taken");
    }
////////////////////////////////////////////////////////////////////////////////
    public void wellcomePlayersMsg(String playersNames) {
        
        System.out.println("Welcome " + playersNames);
    }
////////////////////////////////////////////////////////////////////////////////
    public void selectJokerColor() {
        
        System.out.println("Select Joker color:");
        System.out.println("For red color press (1)");
        System.out.println("For blue color press (2)");
        System.out.println("For black color press (3)");
        System.out.println("For yellow color press (4)");
    }
////////////////////////////////////////////////////////////////////////////////
    public void selectJokerValue(int from, int to) {
        
        System.out.println("Select Joker value (" + from + "-" + to + "): ");
    }
////////////////////////////////////////////////////////////////////////////////
    public void clearScreen() {
        
        System.out.print("\u001b[2J");
        System.out.flush();
    }
////////////////////////////////////////////////////////////////////////////////
    public void turnMsg(boolean turnIteration) {
        
        String tmp;
        
        tmp = (turnIteration) ? "and withdraw kube press " : "";
        System.out.println("For creating new serie press (1) ");
        System.out.println("For adding tile to exict serie press (2) ");
        System.out.println("For taking tile from exict serie press (3) ");
        System.out.println("To end your turn " + tmp + "(4)");
        System.out.println("To edit your hand (5)");
        System.out.println("To Sub Menu press (0)");
    }
////////////////////////////////////////////////////////////////////////////////
    public void printPlayerMsg(String name) {
        System.out.println(name + " it's your turn");
    }
////////////////////////////////////////////////////////////////////////////////
    public void chooseSerieMsg() {
        System.out.println("Please choose serie index: ");
    }
////////////////////////////////////////////////////////////////////////////////
    public void chooseTileIndexMsg() {
        System.out.println("Please choose tile index in serie: ");
    }
////////////////////////////////////////////////////////////////////////////////
    public void chooseTileIndexFromHandMsg() {
        System.out.println("Please choose tile index from hand");
    }
////////////////////////////////////////////////////////////////////////////////
    public void noSerieInTableMsg() {
        System.out.println("There is no serie on the table!");
    }
////////////////////////////////////////////////////////////////////////////////
    public void orgenaizePlayerHandMsg() {
        
        System.out.println("For auto orgenize press (1)");
        System.out.println("To Swap 2 tiles press (2)");
        System.out.println("cancel (0)");
    }
////////////////////////////////////////////////////////////////////////////////
    public void swapIndexMsg() {
        System.out.println("Select 2 indexes to swap: ");
    }
////////////////////////////////////////////////////////////////////////////////
    public void printEndTurnMsg(String endTurn, String playerName) {
        System.out.println(playerName+": "+endTurn);
    }
////////////////////////////////////////////////////////////////////////////////
    public void printPcMove(String turnMoves, String playerName) {
        
        if (turnMoves.equals("")) {
            turnMoves = "No moves!";
        }
        System.out.println(playerName + " moves: \n" + turnMoves);
    }
////////////////////////////////////////////////////////////////////////////////
    public void printEndDeckWinner(String name) {
        
        System.out.println("Deck Is Over And The Player With The Smallest Deck Is:");
        System.out.println("                " + name);
        System.out.println("           (press Enter to continue)");
        getStringFromUser(false);

    }
////////////////////////////////////////////////////////////////////////////////
    public void turnMsg(String table, String currPlayerName) {

        System.out.println(table);
        printPlayerMsg(currPlayerName);

    }
////////////////////////////////////////////////////////////////////////////////
    public void printEndHandWinner(String name) {
        
        System.out.println("  The Big Winner Is:");
        System.out.println("     " + name);
        System.out.println("(press Enter to continue)");
        getStringFromUser(false);
    }
////////////////////////////////////////////////////////////////////////////////
    public void chooseLoadPath() {

        System.out.print("Please Enter Full File Path You Wish To Load From\n");
    }
////////////////////////////////////////////////////////////////////////////////
    public void printErrorInLoadedGame() {

        System.out.print("The Game You Loaded From File Is Not Legit!\n");
    }
////////////////////////////////////////////////////////////////////////////////
    public void PrintErrorInFile() {

        System.out.print("There is an Error in the file you choose!\n");
    }
////////////////////////////////////////////////////////////////////////////////
    public void PrintErrorFileNotFound() {

        System.out.print("The File Path You Entered Is Not Legit\n");
    }
////////////////////////////////////////////////////////////////////////////////
    public void emptyHandMsg() {
        System.out.println("your hand is empty.");
    }
////////////////////////////////////////////////////////////////////////////////
    public void onlyOneTileMsg() {
        System.out.println("You don't have enough tile in your hand to edit!");
    }
////////////////////////////////////////////////////////////////////////////////
    public void printString(String string) {
        System.out.println(string);
    }
////////////////////////////////////////////////////////////////////////////////
}
