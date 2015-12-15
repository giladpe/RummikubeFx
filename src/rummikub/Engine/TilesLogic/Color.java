/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.Engine.TilesLogic;

/**
 *
 * @author giladPe
 */
public enum Color {
    
   RED(500,"#ff0022"), BLUE(400,"#4400ff"), YELLOW(300,"#ffff00"), BLACK(200,"#0a0a09");//,RESET(0,"\u001B[31m");
   
   int colorCode;
   String printColor;
   //color code use for sorting kubes
   private Color(int colorCode,String printColor){
       
   this.colorCode=colorCode;
   this.printColor=printColor;
   
   }
 ////////////////////////////////////////////////////////////////////////////////  
   public int getCode(){
       
   return colorCode;  
   }
////////////////////////////////////////////////////////////////////////////////   
   public String print(){
       
       return printColor;
   }
////////////////////////////////////////////////////////////////////////////////  
}

