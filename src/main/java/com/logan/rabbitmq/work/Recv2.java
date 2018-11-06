package com.logan.rabbitmq.work;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv2
{
    public final static String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception
    {

        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        final Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body,"UTF-8");

                System.out.println("[2] Received '" + message+"'");

                try {
                    doWork(message);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] Done");
                }



            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }


    public static void doWork(String task) throws InterruptedException
    {
        Thread.sleep(1000);
    }
}
