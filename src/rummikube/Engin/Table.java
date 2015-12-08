/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.Engin;

import XmlClasses.Board.Sequence;
import rummikubpro.Engin.TilesLogic.Tile;
import rummikubpro.Engin.TilesLogic.BelongTo;
import java.util.ArrayList;
import java.util.Objects;
import rummikubpro.Engin.TilesLogic.Color;
import rummikubpro.Engin.TilesLogic.Value;


public class Table {

    private ArrayList<Serie> seriesList;
////////////////////////////////////////////////////////////////////////////////
    public int getScore() {
        
        int score = 0;
        
        for (Serie serie : seriesList) {
            
            score += serie.getScore();
        }
        return score;
    }
////////////////////////////////////////////////////////////////////////////////
    public Table() {
        seriesList = new ArrayList<>();
    }
////////////////////////////////////////////////////////////////////////////////
    public int getSerieIndex(Serie serie) {
        
        int index = 0;
        
        for (Serie tableSerie : seriesList) {
            
            if (tableSerie == serie) {
                
                return index;
            }
            index++;
        }
        return -1;
    }
////////////////////////////////////////////////////////////////////////////////
    public Table(Table table) {
        
        ArrayList<Serie> cloneTable = new ArrayList<>();
        
        for (Serie serie : table.getSerieList()) {
            
            cloneTable.add(new Serie(serie.getSerie()));
        }
        this.seriesList = cloneTable;
    }
////////////////////////////////////////////////////////////////////////////////
    public void addSerie() {
        seriesList.add(new Serie());
    }
////////////////////////////////////////////////////////////////////////////////
    public void addSerie(Serie serie) {
        seriesList.add(serie);
    }
////////////////////////////////////////////////////////////////////////////////
    public void addSerie(ArrayList<Tile> tilesList) {
        
        Serie serie = new Serie();
        serie.setSerie(tilesList);
        seriesList.add(serie);
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean addSerieFromXml(Sequence sequence) {

        Serie serie = new Serie();
        int numOfTiles = sequence.getTile().size();
        boolean isTileLegit= false;
        
        for (int i = 0; i < numOfTiles; i++) {

             isTileLegit = this.addTileFromXml(serie, sequence.getTile().get(i).getColor().value(),
                                                             sequence.getTile().get(i).getValue());
    
        }
        seriesList.add(serie);
        return isTileLegit;
    }
////////////////////////////////////////////////////////////////////////////////
    public boolean addTileFromXml(Serie serie, String color, int value) {

        Value valToAdd = Value.TWO;
        Color colorToAdd = Color.BLACK;
        boolean isValFound = false;
        boolean isColorFound = false;

        for (Value val : Value.values()) {

            if (val.getValue() == value) {

                valToAdd = val;
                isValFound = true;
                break;
            }
        }
        if (!isValFound)//problem with value
        {
            return false;
        }
        for (Color colorItr : Color.values()) {

            if (colorItr.name().equals(color)) {

                colorToAdd = colorItr;
                isColorFound = true;
                break;
            }
        }
        if (!isColorFound) {

            return false;
        }

        Tile tile = new Tile(colorToAdd, valToAdd);
        serie.addTile(tile);
        return true;
    }
////////////////////////////////////////////////////////////////////////////////
    public int getSerieSize(int index) {
        return seriesList.get(index).getSize();
    }
////////////////////////////////////////////////////////////////////////////////
    public void setSerieList(ArrayList<Serie> serieList) {
        this.seriesList = serieList;
    }
////////////////////////////////////////////////////////////////////////////////
    public Serie getSerie(int serieIndex) {
        return seriesList.get(serieIndex);
    }
////////////////////////////////////////////////////////////////////////////////
    public Tile getTileFromSerie(Serie serie, int index) {
        return serie.getTile(index);
    }
////////////////////////////////////////////////////////////////////////////////
    public void removeTileFromSerie(int serieIndex, int tileIndex) {
        getSerie(serieIndex).removeTileFromSerie(tileIndex);
    }
////////////////////////////////////////////////////////////////////////////////
    public Tile getTileFromSerie(int serieIndex, int tileIndex) {
        return getSerie(serieIndex).getTile(tileIndex);
    }
////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Serie> getSerieList() {
        return seriesList;
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        
        String tableSer = "";
        if (seriesList.size() < 1) {
            
            tableSer += "No Series on the Game Table!";
            
        } else {
            
            tableSer += "Table Score: " + getScore() + " \n" + "Table Series: \n";
            int i = 0;
            
            for (Serie serie : seriesList) {
                
                tableSer += i + ": " + serie + " \n";
                i++;
            }
        }
        return tableSer;
    }
////////////////////////////////////////////////////////////////////////////////
    public void TilesBelongToTable() {

        for (Serie serie : seriesList) {
            
            for (Tile tile : serie.getSerie()) {
                
                tile.setBelongTo(BelongTo.TABLE);
            }
        }

    }
////////////////////////////////////////////////////////////////////////////////
    public boolean isValidTable() {

        return seriesList.stream().noneMatch((Serie serie) -> (!serie.isValidSerie()));
    }
////////////////////////////////////////////////////////////////////////////////
    boolean tryAddTileToSerie(Serie serie, Tile tile) {

        int endSerie = serie.getSize();
        boolean added = true;
        serie.addToStart(tile);//check possible options to add a tile to serie

        if (!serie.isValidSerie()) {

            serie.removeFromStart();
            serie.addToEnd(tile);

            if (!serie.isValidSerie()) {
                serie.removeFromEnd();
                added = false;
            }
        }
        return added;
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.seriesList);
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
        final Table other = (Table) obj;
        
        if (!Objects.equals(this.seriesList, other.seriesList)) {
            
            return false;
        }
        return true;
    }
////////////////////////////////////////////////////////////////////////////////
}
