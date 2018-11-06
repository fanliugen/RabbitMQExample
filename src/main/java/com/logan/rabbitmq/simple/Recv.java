package com.logan.rabbitmq.simple;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;




public class Recv
{
    public static final String QUEUE_NAME = "QUEUE_simple";

    public static void main(String [] args) throws IOException, TimeoutException
    {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body,"UTF-8");
                System.out.println("[x]Received ' " + message + " '");

            }
        };

        channel.basicConsume(QUEUE_NAME,true,consumer);



    }
}
