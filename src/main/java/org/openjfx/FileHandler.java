package org.openjfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileHandler {


    public static volatile String styleFile="styles/darkStyle.css";

    //functions in charge of loading properly string of css file
    public static String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( IOException e ) {
            return null;
        }
    }

}
