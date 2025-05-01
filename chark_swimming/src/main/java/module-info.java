module com.example.Coursework_35155752 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires jdk.xml.dom;
    requires java.sql;

    exports Coursework_35155752;
    opens Coursework_35155752 to javafx.graphics, javafx.fxml;
}