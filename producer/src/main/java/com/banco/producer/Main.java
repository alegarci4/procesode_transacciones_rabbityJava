package com.banco.producer;
import com.fasterxml.jackson.databind.ObjectMapper;

//esta clase llama al API, convierte el JSON en objetos Java, recorre todas las transacciones y envía cada transacción a RabbitMQ

public class Main {

    public static void main(String[] args) {

        try {

            ApiClient apiClient = new ApiClient();
            String json = apiClient.obtenerTransacciones();

            ObjectMapper mapper = new ObjectMapper();
            LoteTransacciones lote = mapper.readValue(json, LoteTransacciones.class);

            ProducerService producerService = new ProducerService();

            for (Transaccion t : lote.getTransacciones()) {

                producerService.enviarTransaccion(t);

            }

            System.out.println("Todas las transacciones fueron enviadas.");

        } catch (Exception e) {

            System.out.println("Error en el proceso: " + e.getMessage());

        }

    }
}