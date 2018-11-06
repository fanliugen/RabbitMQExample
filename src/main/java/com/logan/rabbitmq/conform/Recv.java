package com.logan.rabbitmq.conform;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv
{
    private static final String QUEUE_NAME = "QUEUE_simple_confirm_aync";

    public static void main(String[] argv)throws IOException, TimeoutException
    {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body,"utf-8");
                System.out.println("Recf(confirm) msg: '" + message+"'");
            }
        };


        channel.basicConsume(QUEUE_NAME,true,consumer);
    }

}
