package br.com.spedine.bookshelf.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestAPI {
    private static String ADDRESS = "https://www.googleapis.com/books/v1/volumes?q=";
    private static String API_KEY = System.getenv("BOOK_API_KEY");

    public static String getJsonData(String book_name) {
        String request_address = ADDRESS + book_name.replace(" ", "+") + "&key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(request_address))
                        .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
}
