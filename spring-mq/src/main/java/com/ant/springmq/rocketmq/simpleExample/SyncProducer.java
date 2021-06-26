package com.test1.ant.springmq.rocketmq.simpleExample;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * RocketMQ 生产者--同步方式
 * Reliable synchronous transmission is used in extensive scenes,
 * such as important notification messages, SMS notification, SMS marketing system, etc..
 * 可靠的同步传输适用于广泛的场景，例如重要的通知消息，短信通知，短信营销系统等
 */
public class SyncProducer {
    public static void main(String[] args) {
        try {
            //Instantiate with a producer group name.
            // 初始化一个生产者组名称
            DefaultMQProducer producer = new
                    DefaultMQProducer("rocket_producer_group_name");
            // Specify name server addresses.
            // 定义name server服务器地址以及ip
            producer.setNamesrvAddr("localhost:9876");
            //Launch the instance.  启动生产者实例
            producer.start();
            for (int i = 0; i < 100; i++) {
                //Create a message instance, specifying topic, tag and message body.
                // 创建一个消息实例，指定topic、tag、消息体
                Message msg = new Message("TopicTest1" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " +
                                i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                //Call send message to deliver message to one of brokers.
                //发送消息到其中一个一个broker
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            }
            //Shut down once the producer instance is not longer in use.
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
