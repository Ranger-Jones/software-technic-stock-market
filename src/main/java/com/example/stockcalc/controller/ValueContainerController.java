package com.example.stockcalc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ValueContainerController {
    @FXML
    private Label valueLabel;

    @FXML
    private Label value;

    @FXML
    private Label valueType;

    public void setValues(String label, String val, String valType){
        valueLabel.setText(label);
        value.setText(val);
        valueType.setText(valType);
    }
}
