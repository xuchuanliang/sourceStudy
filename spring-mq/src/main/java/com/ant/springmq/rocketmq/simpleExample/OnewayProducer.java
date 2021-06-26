package com.test1.ant.springmq.rocketmq.simpleExample;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * RocketMQ 生产者-单向模式
 * One-way transmission is used for cases requiring moderate reliability, such as log collection.
 * 单项模式一般使用在只需要适度可靠性的情况下，例如日志收集场景
 */
public class OnewayProducer {
    public static void main(String[] args) {
        try {
            //Instantiate with a producer group name.
            DefaultMQProducer producer = new DefaultMQProducer("rocket_producer_group_name");
            // Specify name server addresses.
            producer.setNamesrvAddr("localhost:9876");
            //Launch the instance.
            producer.start();
            for (int i = 0; i < 100; i++) {
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " +
                                i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                //Call send message to deliver message to one of brokers.
                producer.sendOneway(msg);
            }
            //Wait for sending to complete
            Thread.sleep(5000);
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
