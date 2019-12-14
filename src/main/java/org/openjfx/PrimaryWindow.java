package org.openjfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PrimaryWindow extends Application {
    private static Scene scene;
    FileHandler fileHandler = new FileHandler();
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 900, 600);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("PGErulez"); // function which set name of program

        try{
            stage.getIcons().add(new Image(new FileInputStream("img/pge.png")));//adding icon
        }catch(Exception e) {
            System.err.println("Logo doesn't find");
        }
        scene.getStylesheets().add(fileHandler.fileToStylesheetString(new File("styles/lightStyle.css")));

        //  controller.initialize();
        stage.setMinHeight(600);
        stage.setMinWidth(900);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e->closeProgram());
    }
    public static void closeProgram(){
        Platform.exit();
        System.exit(0);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Scene getScene(){
        return scene;
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Laucher.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
