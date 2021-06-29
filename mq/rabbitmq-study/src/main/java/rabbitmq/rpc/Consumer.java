package rabbitmq.rpc;

import com.rabbitmq.client.*;
import rabbitmq.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        try{
            Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();
            final String queue = channel.queueDeclare().getQueue();
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                    .contentType("application/json")
                    .build();
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
