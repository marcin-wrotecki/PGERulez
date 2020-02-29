package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class InfoWindowHandler {


    @FXML
    public static void showErrorWindow(String header, String message)  {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(header);
        alert.setHeaderText(message);
        alert.showAndWait();

    }

    @FXML
    public static void showSuccesWindow(String header, String message)  {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Sukces");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();

    }


}
