package rabbitmq.routing;

import com.rabbitmq.client.*;
import rabbitmq.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class ConsumerGreen {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        try{
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(Constant.LOGS2_EXCHANGE,"direct",true,false,null);
            //创建一个临时性的queue
            String queue = channel.queueDeclare().getQueue();
            //绑定queue和exchange，只消费green
            channel.queueBind(queue,Constant.LOGS2_EXCHANGE,"green");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queue,true,deliverCallback,consumerTag -> {});
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
