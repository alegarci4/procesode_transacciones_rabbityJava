package com.banco.consumer.rabbit;
import com.banco.consumer.model.Transaccion;
import com.banco.consumer.service.TransaccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

//esta clase escucha a rabbit

public class RabbitConsumer {

    private static final String HOST = "localhost";

    private final ObjectMapper mapper = new ObjectMapper();
    private final TransaccionService service = new TransaccionService();

    public void iniciar() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        System.out.println("Consumer esperando transacciones...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String mensaje = new String(delivery.getBody(), StandardCharsets.UTF_8);

            try {

                Transaccion transaccion =
                        mapper.readValue(mensaje, Transaccion.class);
                
                
      //se agregan los nuevos atributos 
                transaccion.setNombre("Mariana Garcia");
                transaccion.setCarnet("0905-24-24315");

                
                boolean enviado = service.enviarTransaccion(transaccion);

                if (enviado) {

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                    System.out.println("ACK enviado para transacción: "
                            + transaccion.getIdTransaccion());

                } else {

                    System.out.println("POST falló, no se confirma mensaje");

                }

            } catch (Exception e) {

                System.out.println("Error procesando mensaje: " + e.getMessage());

            }
        };

        String[] bancos = {"BANRURAL", "BAC", "GYT", "BI"};

        for (String banco : bancos) {

            channel.basicConsume(banco, false, deliverCallback, consumerTag -> {});

            System.out.println("Escuchando cola: " + banco);
        }
    }
}