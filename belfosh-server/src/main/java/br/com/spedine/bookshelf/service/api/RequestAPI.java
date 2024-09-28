package br.com.spedine.bookshelf.service.api;

import br.com.spedine.bookshelf.infra.exception.ValidationException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestAPI {
    private static String ALL_BOOKS_ADDRESS = "https://www.googleapis.com/books/v1/volumes?q=";
    private static String ID_BOOK_ADDRESS = "https://www.googleapis.com/books/v1/volumes/";
    private static String API_KEY = System.getenv("BOOK_API_KEY");

    public static String getJsonData(String book_name) {
        String request_address;
        if (API_KEY == null) { // without api key
            request_address = ALL_BOOKS_ADDRESS + book_name.replace(" ", "+");
            return makeRequest(request_address);
        }
        request_address = ALL_BOOKS_ADDRESS + book_name.replace(" ", "+") + "&key=" + API_KEY;
        return makeRequest(request_address);
    }

    public static String getBookById(String book_id) {
        String request_address = ID_BOOK_ADDRESS + book_id;
        return makeRequest(request_address);
    }

    private static String makeRequest(String address) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(address))
                        .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new ValidationException("Google Books API request exception");
        }
    }
}