package com.ant.rabbitspringbootproduct;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RabbitmqSender {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(Person message){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println(correlationData.toString());
                if(ack){
                    System.out.println("发送成功");
                }else{
                    System.out.println("发送失败："+cause);
                }
            }
        });
        //指定业务唯一ID
        CorrelationData correlation = new CorrelationData();
        System.out.println(correlation.toString());
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("=============== post to do :"+message);
                return message;
            }
        };
        rabbitTemplate.convertAndSend("person","",JSON.toJSONString(message),messagePostProcessor,correlation);
    }
}
