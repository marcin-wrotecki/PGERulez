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
            BigDecimal sumY = new BigDecimal("0");
            BigDecimal sumPt = new BigDecimal("0");
            BigDecimal sumPe = new BigDecimal("0");
            BigDecimal sumZw = new BigDecimal("0");
            BigDecimal sumPtA = new BigDecimal("0");
            BigDecimal sumZg = new BigDecimal("0");
            BigDecimal sumPtB = new BigDecimal("0");
            BigDecimal sumPtC  = new BigDecimal("0");
            BigDecimal sumPtD = new BigDecimal("0");
            BigDecimal minPtS=new BigDecimal(objectiveFunction.resultData.get(1).PtS);
            BigDecimal minE=new BigDecimal(objectiveFunction.resultData.get(1).E);
            BigDecimal maxPtS =new BigDecimal(objectiveFunction.resultData.get(1).PtS);
            BigDecimal maxE =new BigDecimal(objectiveFunction.resultData.get(1).E);
            BigDecimal sumPeA= new BigDecimal("0");
            BigDecimal sumPeB= new BigDecimal("0");
            BigDecimal sumPeC= new BigDecimal("0");
            BigDecimal sumPeD= new BigDecimal("0");
            BigDecimal sumZgA= new BigDecimal("0");
            BigDecimal sumZwB= new BigDecimal("0");
            BigDecimal sumZwC= new BigDecimal("0");
            BigDecimal sumZwD= new BigDecimal("0");
            BigDecimal sumRA= new BigDecimal("0");
            BigDecimal sumRB= new BigDecimal("0");
            BigDecimal sumRC= new BigDecimal("0");
            BigDecimal sumRD= new BigDecimal("0");


            for(int i=0;i<objectiveFunction.resultData.size();i++)
            {
                sumY=sumY.add(new BigDecimal(objectiveFunction.resultData.get(i).Y));
                sumPt =sumPt .add(new BigDecimal(objectiveFunction.resultData.get(i).Pt ));
                sumPe =sumPe .add(new BigDecimal(objectiveFunction.resultData.get(i).Pe ));
                sumZw =sumZw .add(new BigDecimal(objectiveFunction.resultData.get(i).Zw ));
                sumPtA=sumPtA.add(new BigDecimal(objectiveFunction.resultData.get(i).PtA));
                sumZg =sumZg .add(new BigDecimal(objectiveFunction.resultData.get(i).Zg ));
                sumPtB=sumPtB.add(new BigDecimal(objectiveFunction.resultData.get(i).PtB));
                sumPtC=sumPtC.add(new BigDecimal(objectiveFunction.resultData.get(i).PtC));
                sumPtD=sumPtD.add(new BigDecimal(objectiveFunction.resultData.get(i).PtD));

                if(new BigDecimal(objectiveFunction.resultData.get(i).PtS).compareTo(minPtS)==1)
                    minPtS=new BigDecimal(objectiveFunction.resultData.get(i).PtS);

                if(new BigDecimal(objectiveFunction.resultData.get(i).PtS).compareTo(maxPtS)==-1)
                    maxPtS=new BigDecimal(objectiveFunction.resultData.get(i).PtS);

                if(new BigDecimal(objectiveFunction.resultData.get(i).E).compareTo(minE)==1)
                    minE=new BigDecimal(objectiveFunction.resultData.get(i).E);

                if(new BigDecimal(objectiveFunction.resultData.get(i).E).compareTo(maxE)==1)
                    maxE=new BigDecimal(objectiveFunction.resultData.get(i).E);

                sumPeA=sumPeA.add(new BigDecimal(objectiveFunction.resultData.get(i).PeA));
               sumPeB=sumPeB.add(new BigDecimal(objectiveFunction.resultData.get(i).PeB ));
               sumPeC=sumPeC.add(new BigDecimal(objectiveFunction.resultData.get(i).PeC ));
               sumPeD=sumPeD.add(new BigDecimal(objectiveFunction.resultData.get(i).PeD ));
               sumZgA=sumZgA.add(new BigDecimal(objectiveFunction.resultData.get(i).ZgA));
               sumZwB=sumZwB.add(new BigDecimal(objectiveFunction.resultData.get(i).ZwB ));
               sumZwC=sumZwC.add(new BigDecimal(objectiveFunction.resultData.get(i).ZwC));
               sumZwD=sumZwD.add(new BigDecimal(objectiveFunction.resultData.get(i).ZwD));
               sumRA= sumRA.add(new BigDecimal(objectiveFunction.resultData.get(i).RA));
               sumRB= sumRB.add(new BigDecimal(objectiveFunction.resultData.get(i).RB));
               sumRC= sumRC.add(new BigDecimal(objectiveFunction.resultData.get(i).RC));
               sumRD= sumRD.add(new BigDecimal(objectiveFunction.resultData.get(i).RD));

            }
            String temp2 =";;;;;;;;"+sumY.toString()+";"+sumPt.toString()+sumPe.toString()+";"+sumZw.toString()+";"+sumPtA.toString()+";"+sumZg.toString()+";"+sumPtB.toString()+";"+sumPtC.toString()+";"+sumPtD.toString()+";"+minPtS.toString()+"/"+maxPtS.toString()+";"+minE.toString()+"/"+maxE.toString()+";"+sumPeA.toString()+";"+sumPeB.toString()+";"+sumPeC.toString()+";"+sumPeD.toString()+";"+sumZgA.toString()+";"+sumZwB.toString()+";"+sumZwC.toString()+";"+sumZwD.toString()+";"+sumRA.toString()+";"+sumRB.toString()+";"+sumRC.toString()+";"+sumRD;
            temp.add(temp2.replace('.', ','));
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
