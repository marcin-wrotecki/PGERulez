module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires poi.ooxml;
    requires poi;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}