/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.XmlOptions;

import XmlClasses.PlayerType;
import XmlClasses.Players;
import XmlClasses.Rummikub;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import rummikube.Engin.Game;
import rummikube.Engin.Player.Player;
import rummikube.Engin.Serie;
import rummikube.Engin.Table;
import rummikube.Engin.TilesLogic.Tile;

public class xml {
    
    private static final String RESOURCES = "resources";
    private static final String SCHEMA_NAME = "rummikub.xsd";
////////////////////////////////////////////////////////////////////////////////         
public static Rummikub getSavedGame(String filePath) throws SAXException,JAXBException,FileNotFoundException
{
    URL csdURL = xml.class.getResource("/" + RESOURCES + "/" + SCHEMA_NAME);//get schema from resources folder
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = schemaFactory.newSchema(csdURL);

    //get the XML content
   
      JAXBContext context = JAXBContext.newInstance(Rummikub.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
    //attach the Schema to the unmarshaller so it will use it to run validations
      //on the content of the XML
      unmarshaller.setSchema(schema);

      Rummikub rummikub = (Rummikub) unmarshaller.unmarshal(new File(filePath));//get the game from xml file
      
    return rummikub;
}
////////////////////////////////////////////////////////////////////////////////
public static void saveGame(Rummikub rummikub,String FilePath) throws SAXException,JAXBException,FileNotFoundException
{          
        
        URL csdURL = xml.class.getResource("/" + RESOURCES + "/" + SCHEMA_NAME);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(csdURL);//get schema from resources folder
 
        JAXBContext jc = JAXBContext.newInstance(Rummikub.class);
          
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setSchema(schema);//set the schema for validation
        
        OutputStream os = new FileOutputStream( FilePath );
        marshaller.marshal(rummikub,os);//save to xml file in the choosen path
             
} 
////////////////////////////////////////////////////////////////////////////////
public static void createGameToSaveForXml(Game game,Rummikub rummikub,String FilePath) throws SAXException,JAXBException,FileNotFoundException
{    
 Players xmlPlayer = new Players();//create type player from xml generated classes
 
    for (Player player : game.getPlayers()) {//go over all the player from xml file
      
           createPlayerForXml(xmlPlayer,player);//create player from our own player class
    }
    rummikub.setPlayers(xmlPlayer);//set the players
    rummikub.setName(game.getGameName());//set the game name
    rummikub.setCurrentPlayer(game.getCurrPlayer().getName());//set the current player
    
    createBoardForXml(rummikub,game.getGameTable());//get the game board to save

    saveGame(rummikub,FilePath);//go save the game in xml after we finish to convert fields
}
////////////////////////////////////////////////////////////////////////////////
public static void  createBoardForXml(Rummikub rummikub,Table table){
    
  XmlClasses.Board board = new XmlClasses.Board();
  XmlClasses.Board.Sequence sequence = new XmlClasses.Board.Sequence();
   
  for(Serie serie : table.getSerieList()){//create board to save for xml file
      
     getTilesForXmlSerie(sequence,serie);
     board.getSequence().add(sequence);
     sequence = new XmlClasses.Board.Sequence();
  }
    rummikub.setBoard(board);
}
////////////////////////////////////////////////////////////////////////////////
public static void getTilesForXmlSerie( XmlClasses.Board.Sequence sequence,Serie serie){
    
   XmlClasses.Tile Xmltile = new XmlClasses.Tile();
  
    for(Tile tile : serie.getSerie())//get the tiles for series
    {
        for(XmlClasses.Color color : XmlClasses.Color.values())
        {
            if(color.name().equals(tile.getColor().name())){
                
                Xmltile.setColor(color);
                break;
            }
                
        }
        Xmltile.setValue(tile.getValue());//then convert them to xml generated class tile
       sequence.getTile().add(Xmltile);
        Xmltile = new XmlClasses.Tile();
    }
}
////////////////////////////////////////////////////////////////////////////////
public static void createPlayerForXml(Players xmlPlayers,Player player){
    
   Players.Player xmlPlayer = new Players.Player();//prepere player for save
   Players.Player.Tiles tiles= new Players.Player.Tiles();//prepere deck for player to save
   
   xmlPlayer.setName(player.getName());//sets the player name 
   xmlPlayer.setPlacedFirstSequence(player.isFirstMove());
   if(player.isPlayerHuman()){
       
       xmlPlayer.setType(PlayerType.HUMAN);//sets the player type
   }
   else {
       
        xmlPlayer.setType(PlayerType.COMPUTER);
   }
  getTilesForXmlPlayer(tiles,player);
  xmlPlayer.setTiles(tiles);
  xmlPlayers.getPlayer().add(xmlPlayer);
}
////////////////////////////////////////////////////////////////////////////////
public static void getTilesForXmlPlayer(Players.Player.Tiles tiles,Player player){
    
   XmlClasses.Tile Xmltile = new XmlClasses.Tile();
   
    for(Tile tile : player.getHand())//get the tiles in the player hand that we need to save
    {
        for(XmlClasses.Color color : XmlClasses.Color.values())
        {
            if(color.name().equals(tile.getColor().name())){
                
                Xmltile.setColor(color);     
                break;
            }
                
        }
        Xmltile.setValue(tile.getValue());
        tiles.getTile().add(Xmltile);
        Xmltile = new XmlClasses.Tile();
    }
}
////////////////////////////////////////////////////////////////////////////////
}

