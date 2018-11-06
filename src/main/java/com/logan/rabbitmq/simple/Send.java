package com.logan.rabbitmq.simple;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send
{
    private  static final String QUEUE_NAME = "QUEUE_simple";

    public static void main(String[] args)throws Exception
    {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel1 = connection.createChannel();

        boolean durable = false;
        boolean exclusive = false;
        boolean autoDelete = false;

        channel1.queueDeclare(QUEUE_NAME,durable,exclusive,autoDelete,null);

        String msg = "Hello Simple Queue!";

        channel1.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("------send ms:"+msg);


        channel1.close();
        connection.close();

    }
}
