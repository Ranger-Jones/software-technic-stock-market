package com.example.stockcalc.controller;

import com.example.stockcalc.constants.FileNames;
import com.example.stockcalc.functions.LoadingFunctions;
import com.example.stockcalc.functions.SearchFunctions;
import com.example.stockcalc.functions.StartingData;
import com.example.stockcalc.model.TemporarySaving;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController {

    @FXML
    private HBox tickerBar;

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
    private BorderPane homePane;

    @FXML
    public void initialize() {
        tickerBar.getChildren().clear();

        LoadingFunctions.showLoadingScreen(rootPane);

        new Thread(() -> {
            List<TemporarySaving> data = StartingData.load();

            Platform.runLater(() -> {
                try {
                    rootPane.setCenter(homePane);

                    logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(FileNames.HOME_IMAGE)).toString()));

                    // Ticker-Elemente dynamisch hinzufügen
                    for (TemporarySaving temporarySaving : data) {
                        FXMLLoader tickerLoader = new FXMLLoader(getClass().getResource(FileNames.TICKER_NEWS));
                        Node valueContainer = tickerLoader.load();
                        TickerNewsController controller = tickerLoader.getController();
                        controller.setValues(
                                temporarySaving.getStockData()
                        );
                        tickerBar.getChildren().add(valueContainer);
                    }

                    // Layout der tickerBar erzwingen, um die Breite korrekt zu berechnen
                    tickerBar.layout();

                    // Verzögere das Starten der Animation, um sicherzustellen, dass die Breite korrekt berechnet wurde
                    Platform.runLater(this::startTickerAnimation);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Fehler beim Laden der Ansichten", e);
                }
            });
        }).start();
    }

    @FXML
    void handleSearch() {
        String query = searchField.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.DETAIL_SCREEN));
        FXMLLoader fallback = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN));
        SearchFunctions.search(query, error, rootPane, loader, fallback);
    }

    private void startTickerAnimation() {
        // Stelle sicher, dass die Breite von tickerBar korrekt ist, bevor die Animation gestartet wird
        double tickerWidth = tickerBar.getWidth();
        double sceneWidth = tickerBar.getScene().getWidth();  // Scene width

        System.out.println("Scene width: " + sceneWidth); // Debugging-Zeile
        System.out.println("TickerBar width: " + tickerWidth); // Debugging-Zeile

        if (tickerWidth > 0 && sceneWidth > 0) {
            // Erstelle die TranslateTransition Animation
            TranslateTransition transition = new TranslateTransition(Duration.seconds(10), tickerBar);
            transition.setFromX(sceneWidth);
            transition.setToX(-tickerWidth);
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setInterpolator(javafx.animation.Interpolator.LINEAR);
            transition.play();
        } else {
            // Wenn die Breite noch nicht verfügbar ist, versuche es später
            tickerBar.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                if (newWidth.doubleValue() > 0) {
                    TranslateTransition transition = new TranslateTransition(Duration.seconds(10), tickerBar);
                    transition.setFromX(sceneWidth);
                    transition.setToX(-newWidth.doubleValue());
                    transition.setCycleCount(TranslateTransition.INDEFINITE);
                    transition.setInterpolator(javafx.animation.Interpolator.LINEAR);
                    transition.play();
                }
            });
        }
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
