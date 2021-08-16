package com.ant.springmq.rocketmq.tagExample;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 测试从不同的topic以及不同的tag中获取数据
 */
public class TagConsumer {
    public static void main(String[] args) {
//        for (int i = 0; i < 5; i++){
//            consume("ant_top_" + i, "tag_" + i, "ant_consumer_group_" + i);
//        }
//        consume("_ant_top_","*","_ant_consumer_group_");
//        consume("ant_top_" + 0, "tag_" + 0, "ant_consumer_group_" + 0);
//        consume("ant_top_" + 1, "tag_" + 1, "ant_consumer_group_" + 1);
//        consume("ant_top_" + 2, "tag_" + 2, "ant_consumer_group_" + 2);
//        consume("ant_top_" + 3, "tag_" + 3, "ant_consumer_group_" + 3);
//        consume("ant_top_" + 4, "tag_" + 4, "ant_consumer_group_" + 4);

//        consume("ant_top_" + 0, "tag_" + 1, "ant_consumer_group_" + 1);
//        consume("ant_top_" + 0, "tag_" + 2, "ant_consumer_group_" + 2);
//        consume("ant_top_" + 0, "tag_" + 3, "ant_consumer_group_" + 3);
//        consume("ant_top_" + 0, "tag_" + 4, "ant_consumer_group_" + 4);

//        consume("ant_top_" + 1, "tag_" + 0, "ant_consumer_group_" + 0);
//        consume("ant_top_" + 1, "tag_" + 2, "ant_consumer_group_" + 2);
//        consume("ant_top_" + 1, "tag_" + 3, "ant_consumer_group_" + 3);
//        consume("ant_top_" + 1, "tag_" + 4, "ant_consumer_group_" + 4);

        consume("ant_top_" + 2, "*", "ant_consumer_group_" + 2);
        consume("ant_top_" + 3, "*", "ant_consumer_group_" + 3);
        consume("ant_top_" + 4, "*", "ant_consumer_group_" + 4);
    }

    private static void consume(String topic, String tag, String consumerGroupName) {
        try {
            //实例化一个消费者
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroupName);
            //设置name server的地址
            consumer.setNamesrvAddr("localhost:9876");
            //指定需要消费的topic以及tag
            consumer.subscribe(topic, tag);
            //注册回调函数，确定当有消息到达后回调函数
            consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
                msgs.forEach(m -> {
                    try {
                        System.out.println("接收到的消息是：" + new String(m.getBody(), RemotingHelper.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            System.out.println("consumer is start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
