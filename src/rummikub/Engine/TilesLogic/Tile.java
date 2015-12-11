/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.Engine.TilesLogic;

import java.util.Objects;

/**
 *
 * @author giladPe
 */
public class Tile implements Comparable<Tile> {

    private Color color;
    private Value value;
    private BelongTo belongTo;

////////////////////////////////////////////////////////////////////////////////
    public Tile(Color color, Value value) {
        this.color = color;
        this.value = value;
        belongTo = BelongTo.GAMEDECK;
    }
////////////////////////////////////////////////////////////////////////////////    
    public Tile(Color color, Value value,BelongTo belongTo) {
        this.color = color;
        this.value = value;
        this.belongTo = belongTo;
    }
////////////////////////////////////////////////////////////////////////////////
    public int getValue() {
        return value.toInt();
    }
////////////////////////////////////////////////////////////////////////////////
    public BelongTo getBelongTo() {
        return belongTo;
    }
////////////////////////////////////////////////////////////////////////////////
    public void setColor(Color color) {
        this.color = color;
    }
////////////////////////////////////////////////////////////////////////////////
    public void setValue(Value value) {
        this.value = value;
    }
////////////////////////////////////////////////////////////////////////////////
    public void setBelongTo(BelongTo belongTo) {
        this.belongTo = belongTo;
    }
////////////////////////////////////////////////////////////////////////////////
    public String toString() {
        
        String retVal = "";
        
        if (this.value.toInt() == 0) {
            
            retVal = this.color.print() + "[J] " + Color.BLACK.print();
            
        } else {
            
            retVal = this.color.print() + "[" + this.value.toInt() + "] " + Color.BLACK.print();
        }
        return retVal;
    }
////////////////////////////////////////////////////////////////////////////////
    public Value getEnumValue() {
        return value;
    }
////////////////////////////////////////////////////////////////////////////////
    public Color getColor() {
        return color;
    }
////////////////////////////////////////////////////////////////////////////////
    public int getColorCode() {
        return color.getCode();
    }
////////////////////////////////////////////////////////////////////////////////
    ///this function is for sorting
    @Override
    public int compareTo(Tile compareToKube) {
        
        int kubeVal = this.getValue() + this.getColorCode();
        int compareToKubeVal = compareToKube.getValue() + compareToKube.getColorCode();
        
        return (kubeVal - compareToKubeVal);
    }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public int hashCode() {
        
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.color);
        hash = 59 * hash + Objects.hashCode(this.value);
        hash = 59 * hash + Objects.hashCode(this.belongTo);
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
        final Tile other = (Tile) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        return this.belongTo == other.belongTo;
    }
////////////////////////////////////////////////////////////////////////////////  
}
