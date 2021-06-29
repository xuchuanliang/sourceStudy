package rabbitmq.publishSubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 生产者
 */
public class Product {
    private static final AtomicLong ATOMIC_LONG = new AtomicLong();
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();
            //创建exchange以及指定其类型是fanout，该类型标识会发送消息到所有的queue
            channel.exchangeDeclare(Constant.LOGS_EXCHANGE,"fanout");
            for(int i=0;i<100000;i++){
                String message = "hello world"+ATOMIC_LONG.getAndIncrement();
                channel.basicPublish("logs","",null,message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
