/*
 * the main class of the project that contain the main function that rins the game
 */
package rummikub.gameLogic.controller.rummikub;

// This class runs the game
public class Main {
    public static void main(String[] args) {
        Rummikub newGame = new Rummikub();
        newGame.startGame();
    }
}