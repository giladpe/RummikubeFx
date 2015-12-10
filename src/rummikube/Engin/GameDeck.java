/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.Engin;


import XmlClasses.Board.Sequence;
import java.util.ArrayList;
import java.util.Random;
import rummikube.Engin.TilesLogic.BelongTo;
import rummikube.Engin.TilesLogic.Color;
import rummikube.Engin.TilesLogic.Tile;
import rummikube.Engin.TilesLogic.Value;

/**
 *
 * @author giladPe
 */
public class GameDeck {

    private ArrayList<Tile> gameDeck;
    //private Deck gameDeck;

    public GameDeck() {
        gameDeck = new ArrayList<>();
        createFullDeck();
        shuffle();
    }
////////////////////////////////////////////////////////////////////////////////
    public void createFullDeck() {
        
        for (int i = 0; i < 2; i++) {
            
            for (Color tileColor : Color.values()) {
                
                for (Value tileValue : Value.values()) {
                    
                    if (!(tileValue == Value.JOKER)) {
                        
                        gameDeck.add(new Tile(tileColor, tileValue));
                    }

                }

            }
        }
        gameDeck.add(new Tile(Color.BLACK, Value.JOKER));
        gameDeck.add(new Tile(Color.RED, Value.JOKER));
    }
////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Tile> getGameDeck() {
        return gameDeck;
    }
////////////////////////////////////////////////////////////////////////////////
    public int getGameDeckSize() {
        return gameDeck.size();
    }
////////////////////////////////////////////////////////////////////////////////
    public void setGameDeck(ArrayList<Tile> gameDeck) {
        this.gameDeck = gameDeck;
    }
////////////////////////////////////////////////////////////////////////////////
    public void shuffle() {
        
        ArrayList<Tile> tmpTiles = new ArrayList<>();
        Random rand = new Random();
        int randIndex = 0;
        int tilesSize = getGameDeckSize();
        
        for (int i = 0; i < tilesSize; i++) {
            //Generate Random number from 0 - tiles.size
            randIndex = rand.nextInt(getGameDeckSize());
            tmpTiles.add(gameDeck.get(randIndex));
            //Remove the tile from list
            gameDeck.remove(randIndex);

        }
        gameDeck = tmpTiles;
    }
////////////////////////////////////////////////////////////////////////////////
    public Tile withdraw() {
       
        Tile tile = getTopTile();
        tile.setBelongTo(BelongTo.PLAYER);
        gameDeck.remove(0); // remove from deck
        return tile;
    }
////////////////////////////////////////////////////////////////////////////////
    public Tile getTopTile() {
        return gameDeck.get(0);
    }
////////////////////////////////////////////////////////////////////////////////
    public Color getTopTileColor() {
        return gameDeck.get(0).getColor();
    }
////////////////////////////////////////////////////////////////////////////////
    public Value getTopTileEnumValue() {
        return gameDeck.get(0).getEnumValue();
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean remove(Tile tile) {
        return gameDeck.remove(tile);
    }
////////////////////////////////////////////////////////////////////////////////
    public void remove(int tileIndex) {
        gameDeck.remove(tileIndex);
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean addSerieFromXML(Sequence seq) {

        //gameDeck.
        return true;
    }
////////////////////////////////////////////////////////////////////////////////    
}
