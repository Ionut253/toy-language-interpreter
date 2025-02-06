module com.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;
    requires java.xml;


    opens com.example.gui to javafx.fxml;
    exports com.example.gui;
}