package org.openjfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryWindowController implements Initializable {

    @FXML
    Button fileButton, confirmButton,ContrastButton;

    @FXML
    private TextField filePath,resultFileName;


    public Stage stage;
    FileHandler fileHandler = new FileHandler();

    ArrayList<String> linesOfFile = new ArrayList<>();

    private String defaultResultFileName = new String("wyniki.csv");
    ObjectiveFunction objectiveFunction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ContrastButton.setGraphic(new ImageView(new Image(new FileInputStream("img/contrast.png"))));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Can't find contrast.png image");
        }

        resultFileName.setText(defaultResultFileName);


        ContrastButton.setOnAction(e-> {
                try {
                    fileHandler.changeContrast();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.out.println("Can't change contrast");
                }
            });

        fileButton.setOnAction(e ->showDialogWindowToChooseFile(filePath));
        filePath.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String[] tab = newValue.split("\\\\");
                Pattern p = Pattern.compile("([\\p{Alnum}ąężźćśĄĘŻŹŚ _]+\\.csv)");
                Matcher m = p.matcher(tab[tab.length - 1]);
                if (!m.matches())
                    filePath.getStyleClass().add("warningTextField");
                else
                    filePath.getStyleClass().removeAll("warningTextField");
            }
        });


        confirmButton.setOnAction(e-> changePathFile());


    }

    @FXML
    private void changePathFile(){

        if (!filePath.getStyleClass().contains("warningTextField")) {
            linesOfFile=fileHandler.readFile(filePath.getText());
            objectiveFunction = new ObjectiveFunction(linesOfFile);
            ArrayList<String> temp = new ArrayList<>();
            temp.add(linesOfFile.get(0));
            for(int i = 0; i<objectiveFunction.resultData.size();i++)
                temp.add(objectiveFunction.resultData.get(i).toString());
            temp.add(linesOfFile.get(linesOfFile.size()-1));
            if(linesOfFile!=null)
                fileHandler.writeToFile(resultFileName.getText(),temp);

        } else {
            InfoWindowHandler.showErrorWindow("Podano niepoprawny plik", "Proszę podać plik o rozszerzeniu .csv");

        }
    }
    public Stage getStage() {
        return stage;
    }


    @FXML
    public void showDialogWindowToChooseFile(TextField filePath) {

        try {
            FileChooser fc = new FileChooser();
            File selectedFile = fc.showOpenDialog(getStage()); //dlatego, że otwieramy plik do odczytu
            // showSaveDialog - sluzy do wskazania pliku do zapisu lub nadpisania
            filePath.setText(selectedFile.toString());//zwroci cala sciezke z pliku
        } catch (Exception e) {
            //nic nie rob - mozna dodac jakas funkcjonalnosc
        }
    }



}
