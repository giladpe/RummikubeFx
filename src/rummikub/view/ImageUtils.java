/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rummikub.view;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Arthur
 */
public class ImageUtils {
    public static final String RESOURCES_FOLDER = "/resources/";
    public static final String HUMAN_PLAYER_LOGO = "HumanLogo.png";
    public static final String COMPUTER_PLAYER_LOGO = "ComputerLogo.png";


    //public static final String IMAGE_FOLDER = RESOURCES_FOLDER + "images/";
    
    public static Image getImage (String imageName){
        InputStream imageInputStream = ImageUtils.class.getResourceAsStream(RESOURCES_FOLDER + imageName);
        return new Image(imageInputStream);
    }
    
    public static ImageView getImageView (String imageName){
        return new ImageView(getImage(imageName));
    }    
}
