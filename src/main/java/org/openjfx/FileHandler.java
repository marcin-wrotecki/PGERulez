package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FileHandler {


    public static volatile String styleFile="styles/darkStyle.css";
    private static boolean decider = true;
    //functions in charge of loading properly string of css file
    public  String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( IOException e ) {
            return null;
        }
    }


    public ArrayList<String> readFile(String pathToFile){
        ArrayList<String> result= new ArrayList<>();
        String row;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(pathToFile));
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file to read");
            InfoWindowHandler.showErrorWindow("Podano niepoprawny plik","Nie można otworzyć pliku podana ścieżka moze być niepoprawna");
            return null;
        }
        int i = 0;
        try {
            while ((row = fileReader.readLine()) != null) {
                row=row.replace(',','.');
                result.add(row);

            }
            fileReader.close();
        }
        catch(IOException e){
            InfoWindowHandler.showErrorWindow("Podano niepoprawny plik","Nie można otworzyć pliku podana ścieżka moze być niepoprawna");

        };

        return result;
    }

    public void writeToFile(String fileName,ArrayList<String> data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("result/"+fileName));


        for(String s : data) {
            writer.append(s);
            writer.append("\n");
        }

        writer.close();
        InfoWindowHandler.showSuccesWindow("Zapis do pliku powiódł się!","Utworzono plik: "+fileName);
        }
        catch (IOException e) {
            System.out.println("Can't write to file");
        }
    }
    @FXML
    public void changeContrast() throws IOException {
        decider = !decider;
        if(decider){
            FileHandler.styleFile="styles/darkStyle.css";
            PrimaryWindow.getScene().getStylesheets().setAll(fileToStylesheetString(new File(FileHandler.styleFile)));
        }
        else {
            FileHandler.styleFile="styles/lightStyle.css";
            PrimaryWindow.getScene().getStylesheets().setAll(fileToStylesheetString(new File(FileHandler.styleFile)));
        }

    }


}
