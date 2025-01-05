module com.example.stockcalc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires io.github.cdimascio.dotenv.java;

    opens com.example.stockcalc to javafx.fxml;
    opens com.example.stockcalc.model to com.fasterxml.jackson.databind;
    opens com.example.stockcalc.controller to javafx.fxml;

    exports com.example.stockcalc;
}