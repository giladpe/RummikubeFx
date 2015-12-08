/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.Engin.Player;
import java.util.Collections;
import java.util.ArrayList;
import rummikubpro.Engin.TilesLogic.BelongTo;
import rummikubpro.Engin.TilesLogic.Color;
import rummikubpro.Engin.TilesLogic.Tile;
import rummikubpro.Engin.TilesLogic.Value;

abstract public class Player {

    private String name;
    private ArrayList<Tile> hand;
    private int score=0;
    boolean firstMove=true;
    private String playerType;//human or computer

////////////////////////////////////////////////////////////////////////////////    
    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean isFirstMove() {
        return firstMove;
    }
////////////////////////////////////////////////////////////////////////////////
    public String getPlayerType() {
        return playerType;
    }
////////////////////////////////////////////////////////////////////////////////   
    public boolean isPlayerHuman(){
        return "Human".equals(playerType);   
    }
////////////////////////////////////////////////////////////////////////////////    
    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }
////////////////////////////////////////////////////////////////////////////////   
    public int getHandSize() {

        return hand.size();
    }
////////////////////////////////////////////////////////////////////////////////    
    public Player() {
    }
////////////////////////////////////////////////////////////////////////////////   
    public Player(String name) {//c'tor
        
        this.name = name;
        this.hand = new ArrayList<>();
        score = 0;
        firstMove = true;
    }
////////////////////////////////////////////////////////////////////////////////    
    public void addTile(Tile tile){
    hand.add(tile);
    }
////////////////////////////////////////////////////////////////////////////////   
    public Player(String name, ArrayList<Tile> hand, int score, boolean firstMove) {
        this.name = name;//c'tor for loading game
        this.hand = hand;
        this.score = score;
        this.firstMove = firstMove;
    }
////////////////////////////////////////////////////////////////////////////////
    public void setName(String name) {
        this.name = name;
    }
////////////////////////////////////////////////////////////////////////////////
    public void setHand(ArrayList<Tile> hand) {
        this.hand = hand;
    }
////////////////////////////////////////////////////////////////////////////////
    public String getName() {
        return name;
    }
////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Tile> getHand() {
        return hand;
    }
////////////////////////////////////////////////////////////////////////////////    
    @Override
    public ArrayList<Tile> clone(){
        
        ArrayList<Tile> clone=new ArrayList<Tile>();
        
        for (Tile tile : hand) {
            
            clone.add(tile);
        }
        return clone;
    }
////////////////////////////////////////////////////////////////////////////////
    public void addTileToHand(Tile tile) {
        hand.add(tile);
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean setNewTileFromXml(String color, int value) {
        
        boolean isTileLegit = this.addTileFromXml(color, value);
        return isTileLegit;
    }
////////////////////////////////////////////////////////////////////////////////
    public Tile getTileFromHande(int index) {
        return hand.get(index);
    }
////////////////////////////////////////////////////////////////////////////////
    public int getScore() {
        return score;
    }
////////////////////////////////////////////////////////////////////////////////
    public void addScore(int add) {
        this.score += add;
    }
////////////////////////////////////////////////////////////////////////////////
    public void setScore(int newScore) {
        this.score = newScore;
    }
////////////////////////////////////////////////////////////////////////////////   
    @Override
    public String toString() {
        
        String handOutPut = "";
        int i = 0;
        for (Tile tile : this.hand) {
            handOutPut += i + "-" + tile.toString() + " ";
            i++;
        }      
        return this.getName() + ": " + handOutPut;
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean isValidHand() {
        
        return hand.stream().noneMatch((hand1) -> (!hand1.getBelongTo().equals(BelongTo.PLAYER)));
    }
////////////////////////////////////////////////////////////////////////////////
    public void swap(int index1, int index2) {
        
        Tile tile1 = hand.get(index1);
        Tile tile2 = hand.get(index2);
        hand.set(index2, tile1);
        hand.set(index1, tile2);
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean addTileFromXml(String color, int value) {

        Value valToAdd = Value.TWO;
        Color colorToAdd = Color.BLACK;
        boolean isValFound=false;
        boolean isColorFound=false;
        
        for (Value val : Value.values()) {
            
            if (val.getValue() == value) {
                
                valToAdd = val;
                isValFound=true;
                break;
            }                
        }
        if(!isValFound)//problem with value
        {
            return false;
        }
        for (Color colorItr : Color.values()) {
            
            if (colorItr.name().equals(color)) {
                
                colorToAdd = colorItr;
                isColorFound=true;
                break;
            } 
        }
        if(!isColorFound){
            
            return false;
        }     
        Tile tile = new Tile(colorToAdd, valToAdd);
        hand.add(tile);
        return true;
    }
////////////////////////////////////////////////////////////////////////////////
    public void organizeHand() {
        Collections.sort(hand);
    }
////////////////////////////////////////////////////////////////////////////////
    public void setNotFirstMove() {
        firstMove=false;
    }
////////////////////////////////////////////////////////////////////////////////    
    public int findTileIndex(Tile tile){
        
        for (int i = 0; i < hand.size(); i++) {
            
            if(tile.equals(hand.get(i))){
                
                return i;
            }
        }
        return -1;
    }
////////////////////////////////////////////////////////////////////////////////
    public void belongToPlayer() {
        
        for (Tile tile : hand) {
            
            tile.setBelongTo(BelongTo.PLAYER);
        }
    }
////////////////////////////////////////////////////////////////////////////////    
}
