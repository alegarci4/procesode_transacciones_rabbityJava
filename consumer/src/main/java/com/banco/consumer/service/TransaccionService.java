package com.banco.consumer.service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.banco.consumer.model.Transaccion;
import com.fasterxml.jackson.databind.ObjectMapper;

//esta clase guarda las transacciones

public class TransaccionService {

    private static final String URL_POST = "https://7e0d9ogwzd.execute-api.us-east-1.amazonaws.com/default/guardarTransacciones";

    private HttpClient client;
    private ObjectMapper mapper;

    public TransaccionService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public boolean enviarTransaccion(Transaccion transaccion) {

        try {

            String json = mapper.writeValueAsString(transaccion);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_POST))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Respuesta API: " + response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Transacción enviada correctamente al API");
                return true;
            }

            System.out.println("Error en POST: " + response.body());
            return false;

        } catch (IOException | InterruptedException e) {

            System.out.println("Error enviando transacción: " + e.getMessage());
            return false;
        }
    }
}