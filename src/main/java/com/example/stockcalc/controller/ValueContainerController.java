package com.example.stockcalc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ValueContainerController {

    @FXML
    private Label valueLabel;   // Label für den Namen des Werts (z.B. "Market Cap", "Volume")

    @FXML
    private Label value;        // Label für den tatsächlichen Wert (z.B. 1000, 20000)

    @FXML
    private Label valueType;    // Label für den Werttyp (z.B. USD)

    // Setzt die Werte für die Labels
    public void setValues(String label, String val, String valType){
        valueLabel.setText(label);    // Setzt das Bezeichnungs-Label (z.B. "Marktkapitalisierung")
        value.setText(val);           // Setzt das Wert-Label (z.B. "10000")
        valueType.setText(valType);   // Setzt das Typ-Label (z.B. "USD")
    }
}
