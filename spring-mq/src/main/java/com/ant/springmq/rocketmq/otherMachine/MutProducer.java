package com.test1.ant.springmq.rocketmq.otherMachine;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 多生产者生产消息
 */
public class MutProducer {
    public static void main(String[] args) throws InterruptedException {
        //5个生产者发送消息
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //模拟多个生产者向同一个topic生产消息
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                return doProducer("test");
            });
        }
        TimeUnit.HOURS.sleep(1);
    }

    private static String doProducer(String topic) {
        try {
            System.out.println("开始发送消息");
            Random random = new Random();
            DefaultMQProducer producer = new DefaultMQProducer("report"+random.nextInt(1000));
            producer.setNamesrvAddr("192.168.109.130:9876");
            producer.setRetryTimesWhenSendFailed(1);
            producer.start();
            for (int i = 0; i < 100000; i++) {
                producer.send(new Message(topic, ("from producer1,message index is " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)));
                TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
            }
            System.out.println("发送消息成功");
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
