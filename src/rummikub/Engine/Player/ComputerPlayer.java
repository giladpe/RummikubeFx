/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.Engine.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rummikub.Engine.Solution;
import rummikub.Engine.TilesLogic.Tile;

/**
 *
 * @author crypto
 */
public class ComputerPlayer extends Player {

    public ComputerPlayer(String name) {
        super(name);
        super.setPlayerType("Computer");
    }
////////////////////////////////////////////////////////////////////////////////
    public ArrayList<ArrayList<Tile>> getAllSerieInHand() {
        
        Collections.sort(getHand());
        ArrayList<Tile> tmp = this.clone();
        ArrayList<ArrayList<Tile>> resSerie = new ArrayList<>();
        int serieSize = 0;

        do {
            Solution sol = new Solution(tmp);
            ArrayList<Tile> serie = sol.getSol(this.isFirstMove());
            serieSize = serie.size();
            
            if (serieSize >= 3) {
                
                resSerie.add(serie);
                removeTilesFromList(tmp, serie);
            }
        } while (serieSize >= 3);
        
        return resSerie;
    }
////////////////////////////////////////////////////////////////////////////////
    public void removeTilesFromList(ArrayList<Tile> removeFrom, ArrayList<Tile> remove) {
        
        for (Tile tile : remove) {
            
            for (int i = 0; i < removeFrom.size(); i++) { // remove tiles from tile list
                
                if (removeFrom.get(i) == tile) {
                    
                    removeFrom.remove(i);
                    break;
                }
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean removeFromHand(ArrayList<Tile> remove) {
        
        return remove.stream().noneMatch((tile) -> (!getHand().remove(tile)));
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean isUnderThirty(ArrayList<ArrayList<Tile>> serieList) {
        
        int moveVal = 0;
        
        for (List<Tile> serie : serieList) {
            
            for (Tile tile : serie) { 
                
                moveVal += tile.getValue();
            }
        }
        return (moveVal < 30);
    }
////////////////////////////////////////////////////////////////////////////////
}
