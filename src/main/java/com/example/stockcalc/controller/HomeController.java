// Updated HomeController.java
package com.example.stockcalc.controller;

import com.example.stockcalc.functions.SearchFunctions;
import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.server.StockAPIClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HomeController {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/detail.fxml"));
        FXMLLoader fallback = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/home.fxml"));
        //SearchFunctions.search("AAPL", error, rootPane, loader, fallback);
        logoImage.setImage(new Image(getClass().getResource("/com/example/stockcalc/image/teddy.png").toString()));
    }

    @FXML
    void handleSearch() {
        String query = searchField.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/detail.fxml"));
        FXMLLoader fallback = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/home.fxml"));
        SearchFunctions.search(query, error, rootPane, loader, fallback);
    }

    public void setError(String error) {
        this.error.setText(error);
        this.errorContainer.setVisible(true);
    }

    public void clearError(){
        this.error.setText("");
        this.errorContainer.setVisible(false);
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        String newQuery = searchField.getText();
        if (newQuery != null && !newQuery.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/detail.fxml"));
            FXMLLoader fallback = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/home.fxml"));

            SearchFunctions.search(newQuery, new Label(), rootPane, loader, fallback);

            //topPane.getChildren().remove(searchField);
        }
    }
}
