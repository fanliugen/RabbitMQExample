package com.logan.rabbitmq.topic;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv2
{

    private final static String QUEUE_NAME ="test_queue_topic_2";
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] argv) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.update");
//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");

        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String msg = new String(body, "utf-8");
                System.out.println("[2]Recv msg:" + msg);

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
