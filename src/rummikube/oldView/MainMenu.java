/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.oldView;

public class MainMenu extends View {

    private int choice;
////////////////////////////////////////////////////////////////////////////////
    public MainMenu() {
        
        System.out.println("Welcome To Rummikub Game!");
        System.out.println("To start new game press (1)");
        System.out.println("To Load game from file press (2)");
        System.out.println("To exit press (0)");
        choice = getIntFromUser(0, 2);
    }
////////////////////////////////////////////////////////////////////////////////
    public int getChoice() {
        return choice;
    }
////////////////////////////////////////////////////////////////////////////////
    public void setChoice(int choice) {
        this.choice = choice;
    }
////////////////////////////////////////////////////////////////////////////////    
}
