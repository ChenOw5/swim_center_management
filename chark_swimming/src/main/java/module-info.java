module com.example.chark_swimming_center {
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

    exports chark_swimming_center;
    opens chark_swimming_center to javafx.graphics, javafx.fxml;
}