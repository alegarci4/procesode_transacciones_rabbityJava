package com.banco.producer;

//esta clase se conecta al endpoint: get transacciones,Obtiene el JSON completo del lote y lo devuelve como String




import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private static final String API_URL = "https://hly784ig9d.execute-api.us-east-1.amazonaws.com/default/transacciones";

    public String obtenerTransacciones() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}