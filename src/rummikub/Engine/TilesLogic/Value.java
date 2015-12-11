/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.Engine.TilesLogic;

/**
 *
 * @author michaelKr
 */
public enum Value {
    
   JOKER(0),ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12), THIRTEEN(13);

    public int getValue() {
        return value;
    }

   int value;
////////////////////////////////////////////////////////////////////////////////
    public void setValue(int value) {
        
        this.value = value;
    }
////////////////////////////////////////////////////////////////////////////////   
   private Value(int value){
       
   this.value=value;
   }
 ////////////////////////////////////////////////////////////////////////////////  
   int toInt(){
       
       return value;
   }
 //////////////////////////////////////////////////////////////////////////////// 
}



