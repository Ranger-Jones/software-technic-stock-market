package com.example.stockcalc.functions;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageFunctions {
    public static void loadImageWithApiKey(String imageUrl, String apiKey, ImageView imageView) {
        try {
            // Verbindung zur URL herstellen
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // API-Key im Header setzen
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            // Response prüfen
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Bilddaten als Byte-Array lesen
                InputStream inputStream = connection.getInputStream();
                byte[] imageBytes = inputStream.readAllBytes();
                inputStream.close();

                // JavaFX Image aus Byte-Array erstellen
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                Image image = new Image(byteArrayInputStream);

                // Bild in ImageView setzen
                imageView.setImage(image);
            } else {
                System.err.println("Fehler beim Laden des Bildes: HTTP-Status " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
