package com.banco.consumer;
import com.banco.consumer.rabbit.RabbitConsumer;

//esta clase inicia el Rabbitconsumer

public class Main {

    public static void main(String[] args) {

        try {

            RabbitConsumer consumer = new RabbitConsumer();

            consumer.iniciar();

        } catch (Exception e) {

            System.out.println("Error iniciando el consumer: " + e.getMessage());
        }
    }
}