package com.logan.rabbitmq.util;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils
{
    public static  Connection getConnection() throws IOException,TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("127.0.0.1");

        factory.setPort(5672);

        factory.setVirtualHost("/vhost_logan");

        factory.setUsername("flg");
        factory.setPassword("123");


        Connection connection = factory.newConnection();


        return connection;
    }
}
