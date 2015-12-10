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
public enum PrintColor {
    
  BLACK("\u001B[30m"),
  RED("\u001B[31m"),
  GREEN("\u001B[32m"),
  YELLOW("\u001B[33m"),
  BLUE("\u001B[34m"),
  PURPLE("\u001B[35m"),
  CYAN("\u001B[36m"),
  WHITE("\u001B[37m");

  private final String ansiColor;

  PrintColor(String ansiColor) {
    this.ansiColor = ansiColor;
  }

  public String getAnsiColor() {
    return ansiColor;
  }
}    

