package rabbitmq.routing;

import com.rabbitmq.client.*;
import rabbitmq.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 生产者
 */
public class Product {
    private static AtomicLong atomicLong = new AtomicLong();
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        try(Connection connection = connectionFactory.newConnection()){
            Channel channel = connection.createChannel();
            channel.exchangeDelete(Constant.LOGS2_EXCHANGE);
            //指定生产者发送消息到的exchange，指定exchange类型
            channel.exchangeDeclare(Constant.LOGS2_EXCHANGE,"direct",true,false,null);
            //发送消息：根据序号使用不同的routing key
            for(int i=0;i<100000;i++){
                channel.basicPublish(Constant.LOGS2_EXCHANGE,getRouting(),MessageProperties.PERSISTENT_BASIC,("你好："+atomicLong.getAndIncrement()).getBytes(StandardCharsets.UTF_8));
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机获取routing key，规则是奇数
     * @return
     */
    public static String getRouting(){
        return atomicLong.get()%2==1 ? "green" : "yellow";
    }
}
