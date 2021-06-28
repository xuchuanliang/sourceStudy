package rabbitmq.publishSubscribe;

import com.rabbitmq.client.*;
import rabbitmq.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class Consumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        try{
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            //指定/创建exchange
            channel.exchangeDeclare(Constant.LOGS_EXCHANGE,"fanout");
            //创建一个非持久化，独占的，连接断开后自动删除的队列：用于实时日志的采集
            String queue = channel.queueDeclare().getQueue();
            //将当前临时的queue与日志发送的exchange绑定到一起，这样exchange就会将消息发送到这个queue
            channel.queueBind(queue,Constant.LOGS_EXCHANGE,"");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                String msg = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + msg + "'");
            };
            channel.basicConsume(queue,true,deliverCallback,consumerTag -> {});
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
