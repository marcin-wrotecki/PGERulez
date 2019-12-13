package org.openjfx;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryWindowController {

    @FXML
    private void switchToSecondary() throws IOException {
        PrimaryWindow.setRoot("secondary");
    }
}
