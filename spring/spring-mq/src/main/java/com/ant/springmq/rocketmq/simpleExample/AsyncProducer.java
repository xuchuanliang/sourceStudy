package com.ant.springmq.rocketmq.simpleExample;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * RocketMQ 生产者-异步模式
 */
public class AsyncProducer {
    public static void main(String[] args) {
        try {
            //Instantiate with a producer group name.
            //实例化一个生产者组名称
            DefaultMQProducer producer = new DefaultMQProducer("rocket_producer_group_name1");
            // Specify name server addresses.
            //指定name server地址和ip
            producer.setNamesrvAddr("localhost:9876");
            //Launch the instance.
            //启动生产者实例
            producer.start();
            //如果异步情况下发送失败的重复次数，有可能会导致重复消息，需要开发者来根据应用情况自定义
            producer.setRetryTimesWhenSendAsyncFailed(0);

            int messageCount = 100;
            final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
            for (int i = 0; i < messageCount; i++) {
                try {
                    final int index = i;
                    Message msg = new Message("TopicTest",
                            "TagB",
                            "OrderID188",
                            "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                    producer.send(msg, new SendCallback() {
                        @Override
                        public void onSuccess(SendResult sendResult) {
                            countDownLatch.countDown();
                            System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                        }

                        @Override
                        public void onException(Throwable e) {
                            countDownLatch.countDown();
                            System.out.printf("%-10d Exception %s %n", index, e);
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            countDownLatch.await(5, TimeUnit.SECONDS);
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
