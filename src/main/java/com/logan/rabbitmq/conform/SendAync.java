package com.logan.rabbitmq.conform;

import com.logan.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class SendAync {
    private static final String QUEUE_NAME = "QUEUE_simple_confirm_aync";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.confirmSelect();

        //存放未确认的消息
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());


        channel.addConfirmListener(new ConfirmListener() {
            public void handleAck(long deliveryTag, boolean multiple) throws IOException
            {
                if(multiple)
                {
                    System.out.println("handleAck multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }
                else
                {
                    System.out.println("--handleAck multiple false--");
                    confirmSet.remove(deliveryTag);
                }
            }

            public void handleNack(long deliveryTag, boolean multiple) throws IOException
            {

                System.out.println("Nack, SeqNo: " + deliveryTag + ", multiple:"+multiple);
                if (multiple) {
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else
                    { confirmSet.remove(deliveryTag);
                }
            }
    });

        String msg = "Hello QUEUE!";
        while (true)
        {
            long nextSeqNo =channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(nextSeqNo);
        }


    }

}
