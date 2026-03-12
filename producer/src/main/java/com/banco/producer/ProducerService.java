package com.banco.producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//esta clase conecta con RabbitMQ

public class ProducerService {

    private static final String RABBITMQ_HOST = "localhost";

    public void enviarTransaccion(Transaccion transaccion) {

        try {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RABBITMQ_HOST);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String nombreCola = transaccion.getBancoDestino();

            channel.queueDeclare(nombreCola, true, false, false, null);

            ObjectMapper mapper = new ObjectMapper();
            String mensajeJson = mapper.writeValueAsString(transaccion);

            channel.basicPublish("", nombreCola, null, mensajeJson.getBytes());

            System.out.println("Transacción enviada a cola: " + nombreCola);

            channel.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error enviando mensaje: " + e.getMessage());
        }
    }
}