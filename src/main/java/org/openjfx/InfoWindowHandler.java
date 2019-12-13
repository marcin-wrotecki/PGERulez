package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InfoWindowHandler {
    private static Stage stage=new Stage();
    private static Scene scene;

    @FXML
    public static void showErrorWindow(String header, String message)  {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(header);
        alert.setHeaderText(message);
        alert.showAndWait();

    }




}