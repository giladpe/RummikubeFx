/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.Engine;

import java.util.ArrayList;
import java.util.Objects;
import rummikub.Engine.TilesLogic.Tile;

public class Serie {

    private ArrayList<Tile> serie;
   
////////////////////////////////Constractors////////////////////////////////////    
    public Serie() {
        serie = new ArrayList<>();
    }
////////////////////////////////////////////////////////////////////////////////
    public Serie(ArrayList<Tile> serie) {
        this.serie = new ArrayList<>(serie);
    };
/**************************************************************/  
/////////////////////////Series validation functions//////////////////////////// 
    public boolean isValidSerie() {
        
        boolean validSerie = (isValidSet() || isValidStraight());
        return validSerie;
    }
////////////////////////////////////////////////////////////////////////////////    
    private boolean isValidSize(int min, int max) {
        return !(getSize() < min || getSize() > max);
    }
/////////////////////////////Straight functions/////////////////////////////////
    private boolean isValidStraight() {
        // check sequence size
        if (!isValidSize(Utility.MinSerieSize, Utility.MaxStraightSize)) {
            return false;
        }
        if (!isSameColor()) {
            return false;
        }
        return isStraight();
    }
////////////////////////////////////////////////////////////////////////////////
    private boolean isStraight() {
        
        int index = 0;
        int prevVal;
        
        while (isJoker(getTile(index))) {
            
            index++;
        }
        prevVal = getTile(index).getValue();
        if (prevVal - index <= 0) { //in case joker before ONE or 2joker befor TWO
            return false;
        }
        index++;
        for (; index < getSize(); index++) {
            
            if (isJoker(getTile(index))) {
                
                prevVal++;
                
            } else {
                
                    if (prevVal != (getTile(index).getValue() - 1)) {

                        return false;
                    }
                    prevVal++;
            }
        }
        return prevVal <= Utility.MaxStraightSize;
    }
////////////////////////////////////////////////////////////////////////////////    
private boolean isSameColor() {

        int i = 0;
        int serieColorCode = 0;
        int size = getSize();

        while (isJoker(getTile(i))) {
            i++;
        }
        serieColorCode = getTile(i).getColorCode();

        for (i = 0; i < size; i++) {
            if (!isJoker(getTile(i))) {
                if (getTile(i).getColorCode() != serieColorCode) {
                    return false;
                }
            }
        }
        return true;
    }  
//////////////////////////////Set functions/////////////////////////////////////
    private boolean isValidSet() {
        
        return (isValidSize(Utility.MinSerieSize, Utility.MaxSetSize) && isDiffColor() && isSameVal());
    }
////////////////////////////////////////////////////////////////////////////////
    private int[] fillColorBacket() {
        
        int[] colorArr = {0, 0, 0, 0};
        int currColor;
        
        for (Tile tile : serie) {
            
            if (!isJoker(tile)) {
                
                currColor = tile.getColorCode();
                
                switch (currColor) {
                    
                    case Utility.BLACK:
                        colorArr[0]++;
                        break;
                    case Utility.YELLOW:
                        colorArr[1]++;
                        break;
                    case Utility.BLUE:
                        colorArr[2]++;
                        break;
                    case Utility.RED:
                        colorArr[3]++;
                    default:
                        break;
                }
            }
        }
        return colorArr;
    }
////////////////////////////////////////////////////////////////////////////////
    private boolean isSameVal() {
        
        int[] valArr = {0, 0, 0, 0};
        int serieVal, i = 0;
        
        for (Tile tile : serie) {
            
            if (!isJoker(tile)) {
                
                valArr[i] = tile.getValue();
                i++;
            }
        }
        serieVal = valArr[0];
        for (int j = 0; j < valArr.length; j++) {
            
            if (valArr[j] != 0 && valArr[j] != serieVal) {
                
                return false;
            }
        }
        return true;
    }
////////////////////////////////////////////////////////////////////////////////
    private boolean isDiffColor() {
        
        int[] colorArr = fillColorBacket();
        
        for (int color : colorArr) {
            
            if (color > 1) {
                
                return false;
            }
        }
        return true;
    }  
//////////////////////////Globals functions/////////////////////////////////////
    public void addToStart(Tile tile) {
        serie.add(0, tile);
    }
////////////////////////////////////////////////////////////////////////////////
    public void addToEnd(Tile tile) {
        serie.add(tile);
    }
/////////////////////////////////////////////////////////////////////////////////   
    public boolean addTile(Tile tile) {
        serie.add(tile);
        return true;
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean addTile(Tile tile, int position) {
        serie.add(position, tile);
        return true;
    }
////////////////////////////////////////////////////////////////////////////////
    private boolean isJoker(Tile tile) {
        return (tile.getValue() == Utility.Joker);
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        
        String serieOutPut = "";
        int i = 0;
        for (Tile tile : this.serie) {
            
            serieOutPut += i + "-" + tile.toString() + " ";
            i++;
        }
        return serieOutPut;
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.serie);
        return hash;
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Serie other = (Serie) obj;
        return Objects.equals(this.serie, other.serie);
    }
/********************************************************/    
////////////////////////////Removing functions//////////////////////////////////
    public void removeFromStart() {
        serie.remove(0);
    }
////////////////////////////////////////////////////////////////////////////////
    public void removeFromEnd() {
        serie.remove(serie.size() - 1);
    }
////////////////////////////////////////////////////////////////////////////////
    public void removeTileFromSerie(int tileIndex) {
        serie.remove(tileIndex);
    }
////////////////////////////Getters functions///////////////////////////////////
public int getSize() {
    
        return serie.size();
    }
////////////////////////////////////////////////////////////////////////////////
    public int getScore() {
        int score = 0;
        for (Tile tile : serie) {
            score += tile.getValue();
        }
        return score;
    }
////////////////////////////////////////////////////////////////////////////////
    public Tile getTile(int index) {
        return serie.get(index);
    }
////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Tile> getSerie() {
        return serie;
    }   
/////////////////////////////////Setters functions//////////////////////////////
    public void setSerie(ArrayList<Tile> serie) {
        this.serie = serie;
    }
////////////////////////////////////////////////////////////////////////////////
}
