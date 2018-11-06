package com.logan.rabbitmq.PS;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException
    {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String message = "Hello PB";

        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("[x] sent '" + message + "'");

        channel.close();
        connection.close();

    }
}
