package com.ekino.technoshare.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        // Connection + Channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declare exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // Create the message and publish it
        for (String routedMessage : argv) {
            String routingKey = routedMessage.substring(0, routedMessage.indexOf('|'));
            String message = routedMessage.substring(routedMessage.indexOf('|') + 1, routedMessage.length());
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes()); // TO-EXPLAIN Routing key
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }

        channel.close();
        connection.close();
    }

}
