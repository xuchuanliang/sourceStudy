package rabbitmq.topic;

import com.rabbitmq.client.*;
import rabbitmq.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ConsumerDogMan {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        try{
            Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();
            //指定exchange
            channel.exchangeDeclare(Constant.LOGS2_EXCHANGE,"topic");
            //创建临时队列
            final String queue = channel.queueDeclare().getQueue();
            //绑定队列与exchange，并指定routing key 感兴趣dog.man.*
            channel.queueBind(queue,Constant.LOGS2_EXCHANGE,"dog.man.read");
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                String msg = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("recv："+msg);
            };
            channel.basicConsume(queue,true,deliverCallback,consumerTag -> {});
        }catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
