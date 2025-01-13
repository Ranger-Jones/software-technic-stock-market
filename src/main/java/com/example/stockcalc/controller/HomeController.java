package com.example.stockcalc.controller;

import com.example.stockcalc.constants.FileNames;
import com.example.stockcalc.functions.SearchFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class HomeController {

    @FXML
    private TextField searchField;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label error;

    @FXML
    private BorderPane rootPane;

    @FXML
    private BorderPane errorContainer;

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(FileNames.HOME_IMAGE)).toString()));
    }

    @FXML
    void handleSearch() {
        String query = searchField.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.DETAIL_SCREEN));
        FXMLLoader fallback = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN));
        SearchFunctions.search(query, error, rootPane, loader, fallback);
    }

    public void setError(String error) {
        this.error.setText(error);
        this.errorContainer.setVisible(true);
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        String newQuery = searchField.getText();
        if (newQuery != null && !newQuery.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.DETAIL_SCREEN));
            FXMLLoader fallback = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN));

            SearchFunctions.search(newQuery, new Label(), rootPane, loader, fallback);
        }
    }
}
