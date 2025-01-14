package com.example.stockcalc.controller;

import com.example.stockcalc.constants.FileNames;
import com.example.stockcalc.functions.LoadingFunctions;
import com.example.stockcalc.functions.SearchFunctions;
import com.example.stockcalc.functions.StartingData;
import com.example.stockcalc.model.TemporarySaving;
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
    private HBox tickerBar; // Leiste zur Anzeige von Ticker-Elementen

    @FXML
    private TextField searchField; // Textfeld für die Suche

    @FXML
    private ImageView logoImage; // Bild für das Logo

    @FXML
    private Label error; // Label zur Anzeige von Fehlernachrichten

    @FXML
    private BorderPane rootPane; // Hauptlayout der Anwendung

    @FXML
    private BorderPane errorContainer; // Container für Fehleranzeige

    @FXML
    private BorderPane homePane; // Container für die Hauptansicht

    // Diese Methode wird beim Initialisieren des Controllers aufgerufen
    @FXML
    public void initialize() {
        tickerBar.getChildren().clear(); // Leert die tickerBar zu Beginn

        LoadingFunctions.showLoadingScreen(rootPane); // Zeigt einen Ladebildschirm an

        new Thread(() -> {
            // Lädt die gespeicherten Daten im Hintergrund
            List<TemporarySaving> data = StartingData.load();

            Platform.runLater(() -> {
                try {
                    rootPane.setCenter(homePane); // Setzt das HomePane als Hauptinhalt

                    // Setzt das Logo-Bild
                    logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(FileNames.HOME_IMAGE)).toString()));

                    // Fügt für jedes Ticker-Element ein neues News-Element hinzu
                    for (TemporarySaving temporarySaving : data) {
                        FXMLLoader tickerLoader = new FXMLLoader(getClass().getResource(FileNames.TICKER_NEWS)); // Lädt das Ticker-News-Layout
                        Node valueContainer = tickerLoader.load(); // Lädt das Node-Element
                        TickerNewsController controller = tickerLoader.getController(); // Holt den Controller
                        controller.setValues(
                                temporarySaving.getStockData() // Setzt die Werte für das Ticker-Element
                        );
                        tickerBar.getChildren().add(valueContainer); // Fügt das Ticker-Element der tickerBar hinzu
                    }

                    // Berechnet die Breite der tickerBar, um die Animation korrekt zu starten
                    tickerBar.layout();

                    // Verzögert den Start der Animation, um sicherzustellen, dass die Breite korrekt berechnet wurde
                    Platform.runLater(this::startTickerAnimation);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Fehler beim Laden der Ansichten", e); // Fehlerbehandlung
                }
            });
        }).start();
    }

    // Diese Methode wird aufgerufen, wenn der Benutzer die Suche ausführt
    @FXML
    void handleSearch() {
        String query = searchField.getText(); // Holt den Text aus dem Suchfeld
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.DETAIL_SCREEN)); // Lädt das FXML für die Detailseite
        FXMLLoader fallback = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN)); // Lädt das Fallback für die Home-Seite
        SearchFunctions.search(query, error, rootPane, loader, fallback); // Führt die Suchfunktion aus
    }

    // Diese Methode startet die Animation für die tickerBar (laufende Ticker-Anzeige)
    private void startTickerAnimation() {
        double tickerWidth = tickerBar.getWidth(); // Holt die Breite der tickerBar
        double sceneWidth = tickerBar.getScene().getWidth();  // Holt die Breite der Szene

        // Debugging-Ausgaben, um Breitenwerte zu überprüfen
        System.out.println("Scene width: " + sceneWidth);
        System.out.println("TickerBar width: " + tickerWidth);

        if (tickerWidth > 0 && sceneWidth > 0) {
            // Erstelle eine TranslateTransition-Animation, die tickerBar von rechts nach links bewegt
            TranslateTransition transition = new TranslateTransition(Duration.seconds(10), tickerBar);
            transition.setFromX(sceneWidth); // Startposition am rechten Rand der Szene
            transition.setToX(-tickerWidth); // Endposition außerhalb der linken Seite der Szene
            transition.setCycleCount(TranslateTransition.INDEFINITE); // Animation läuft unendlich
            transition.setInterpolator(javafx.animation.Interpolator.LINEAR); // Gleichmäßige Bewegung
            transition.play(); // Starte die Animation
        } else {
            // Wenn die Breite der tickerBar noch nicht verfügbar ist, setze einen Listener
            tickerBar.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                if (newWidth.doubleValue() > 0) {
                    // Starte die Animation, sobald die Breite verfügbar ist
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

    // Diese Methode wird verwendet, um Fehler anzuzeigen
    public void setError(String error) {
        this.error.setText(error); // Setzt den Fehlertext
        this.errorContainer.setVisible(true); // Zeigt den Fehlercontainer an
    }

    // Diese Methode wird ausgeführt, wenn der Benutzer die Eingabetaste drückt
    @FXML
    public void onEnter(ActionEvent ae) {
        String newQuery = searchField.getText(); // Holt den Text aus dem Suchfeld
        if (newQuery != null && !newQuery.isEmpty()) { // Wenn das Suchfeld nicht leer ist
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.DETAIL_SCREEN)); // Lädt die Detailansicht
            FXMLLoader fallback = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN)); // Lädt das Fallback (Home-Seite)

            // Führt die Suche aus
            SearchFunctions.search(newQuery, new Label(), rootPane, loader, fallback);
        }
    }
}
