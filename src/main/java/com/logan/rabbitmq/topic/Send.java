package com.logan.rabbitmq.topic;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send
{
    public final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] argv)throws Exception
    {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(EXCHANGE_NAME,"topic");


        String message = "id = 1001商品删除了";
        channel.basicPublish(EXCHANGE_NAME,"item.delete",null,message.getBytes());

        System.out.println("[x] Sent '" + message+"'");


        channel.close();
        connection.close();

    }
}
