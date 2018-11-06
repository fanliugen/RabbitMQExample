package com.logan.rabbitmq.work;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class Send
{
    public static final String QUEUE_NAME ="test_work_queue";

    public static void main(String[] args)throws Exception
    {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i = 0;i <50 ;i++)
        {
            String message = "." + i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("[1] Sent '" + message + "'");


            Thread.sleep(i*10);

        }

        channel.close();
        connection.close();


    }
}
