package rabbitmq.helloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static rabbitmq.Constant.QUEUE_NAME;

/**
 * rabbitmq 生产者
 * connection是线程安全的
 * Channel是非线程安全的
 * 队列的创建是幂等的，也就是只有不存在的时候才会被创建
 */
public class Send {
    private static final int threadNum = 80;
    private final static CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        //创建连接：线程安全
        try(Connection connection = connectionFactory.newConnection()){
            for(int i=0;i<threadNum;i++){
                new Thread(()->{
                    try {
                        //获取channel，channel是完成大部分api工作的对象：线程不安全
                        Channel channel = connection.createChannel();
                        for(int j=0;j<100000;j++) {
                            //指定队列
                            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                            //构造消息
                            String message = "hello message" + UUID.randomUUID().toString();
                            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                            TimeUnit.NANOSECONDS.sleep(10);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                }).start();
            }
            countDownLatch.await();
            System.out.println("------------------------发送消息成功-----");
        }catch (TimeoutException | IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
