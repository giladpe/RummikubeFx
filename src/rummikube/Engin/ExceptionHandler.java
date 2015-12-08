/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikube.Engin;

import java.io.FileNotFoundException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 *
 * @author crypto
 */
public class ExceptionHandler extends Exception {
    
    public static void handleException(Exception ex) {//Handle Exceptions that may occuer during the game
        
        if(ex.getCause() == null || ex.getCause().getClass() == JAXBException.class || ex.getCause().getClass() == SAXException.class){

              System.out.println("There is an Error in the file you choose!");
              System.out.println(ex.getLocalizedMessage());
        }
       else if(ex.getCause().getClass() == FileNotFoundException.class){
                     
             System.out.println("The File Path You Entered Is Not Legit");
             System.out.println(ex.getLocalizedMessage());
        }
       else if(ex.getCause().getClass() == UnmarshalException.class)
        {
            System.out.println("There is an Error in the file data!");
            System.out.println(ex.getLocalizedMessage());
        }
       else if(ex.getCause().getClass() == SAXParseException.class){
            
            System.out.println("There is an Error in the file data!");
            System.out.println(ex.getLocalizedMessage());
        }
        
    }
    
}
