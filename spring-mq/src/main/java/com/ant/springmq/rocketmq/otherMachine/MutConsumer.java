package com.test1.ant.springmq.rocketmq.otherMachine;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 模拟多个消息者消费消费消息
 */
public class MutConsumer {
    public static void main(String[] args) {
        consumer();
    }

    private static void consumer(){
        try {
            Random random = new Random();
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("portal");
            consumer.setConsumeThreadMax(5);
            consumer.setConsumeThreadMin(5);
            consumer.setNamesrvAddr("192.168.109.130:9876");
            consumer.subscribe("test","*");
            consumer.setPullBatchSize(1);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.println("消息 长度是："+msgs.size());
                    for(MessageExt m:msgs){
                        try {
                            System.out.println("获取到的消息topic是；"+m.getTopic()+"，获取到的消息是："+new String(m.getBody(), RemotingHelper.DEFAULT_CHARSET));
                            TimeUnit.NANOSECONDS.sleep(random.nextInt(1000));
                        } catch (InterruptedException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            System.out.println("消费者启动成功");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
