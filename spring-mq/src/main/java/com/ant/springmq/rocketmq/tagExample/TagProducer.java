package com.ant.springmq.rocketmq.tagExample;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 使用可靠的同步模式  测试将消息生产到不同的group以及不同的tag中
 */
public class TagProducer {
    public static void main(String[] args) {
        try {
            //初始化一个生产者实例，并且指定所属的生产者组
            DefaultMQProducer producer = new DefaultMQProducer("ant_test");
            //指定nameserver地址和端口
            producer.setNamesrvAddr("localhost:9876");
            //启动生产者实例
            producer.start();
            //发送10万条消息，分别对5进行取模，标签为tag0-tag4，同时发送到topic为ant_1中
            for (int i = 0; i < 5; i++) {
                send(producer, "ant_top_" + i);
            }
//            send(producer, "_ant_top_");
            //关闭生产者实例
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void send(DefaultMQProducer producer, String topic) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        for (int i = 0; i < 100000; i++) {
            int tagSubFix = i % 5;
            //创建一个对应的消息，指定topic、tag、消息实体
            Message message = new Message(topic, "tag_" + tagSubFix, ("这是topic为" + topic + "以及tag后缀为" + tagSubFix + "的消息：" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息
            SendResult sendResult = producer.send(message);
            if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                //如果没发送成功，提示
                System.err.printf("注意**********************消息没有发送成功***************");
            }
        }
    }
}
