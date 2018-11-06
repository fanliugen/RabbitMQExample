package com.logan.rabbitmq.conform;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;



public class Send1
{
    private static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] argv) throws Exception
    {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();


        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.confirmSelect();

        String msg = "Hello QUEUE!";
        channel.basicPublish("", QUEUE_NAME,null,msg.getBytes());

        if(!channel.waitForConfirms())
        {
            System.out.println("Send message failed");
        }
        else
        {
            System.out.println("send message ok...");
        }

        channel.close();
        connection.close();

    }
}
