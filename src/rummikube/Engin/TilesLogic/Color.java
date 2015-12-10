/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.Engin.TilesLogic;

/**
 *
 * @author giladPe
 */
public enum Color {
    
   RED(500,"\u001B[31m"), BLUE(400,"\u001B[34m"), YELLOW(300,"\u001B[33m"), BLACK(200,"\u001B[30m");//,RESET(0,"\u001B[31m");
   
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

