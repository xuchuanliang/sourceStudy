package rabbitmq.topic;

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
 * topic类型的product
 */
public class Product {
    private static AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        try (Connection connection = connectionFactory.newConnection()) {
            final Channel channel = connection.createChannel();
            //先删除exchange
            channel.exchangeDelete(Constant.LOGS2_EXCHANGE);
            //指定topic类型的exchange
            channel.exchangeDeclare(Constant.LOGS2_EXCHANGE, "topic");
            for (int i = 0; i < 10000; i++) {
                long s = atomicLong.getAndIncrement() % 3;
                String person;
                String man;
                String yellow;
                if (1 == s) {
                    person = "dog";
                } else if (2 == s) {
                    person = "cat";
                } else {
                    person = "pig";
                }
                if (1 == s) {
                    man = "man";
                }else if(2==s){
                    man = "woman";
                }else{
                    man = "unkown";
                }
                if (1 == s) {
                    yellow = "read";
                }else if(2==s){
                    yellow = "blank";
                }else{
                    yellow = "yellow";
                }
                channel.basicPublish(Constant.LOGS2_EXCHANGE, person+"."+man+"."+yellow, null, (person+"."+man+"."+yellow).getBytes(StandardCharsets.UTF_8));
                TimeUnit.MILLISECONDS.sleep(200);
            }
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
