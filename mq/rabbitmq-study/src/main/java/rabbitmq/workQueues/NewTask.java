package rabbitmq.workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class NewTask {
    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        //创建连接
        try(Connection connection = connectionFactory.newConnection()){
            //获取channel
            Channel channel = connection.createChannel();
            //指定发送的队列名称：
            // 队列名称；
            // 队列数据是否持久化；
            // 是否独占队列，如果是，则只属于本连接；
            // 是否自动删除，即当用完就删除；
            // 其他属性配置
            channel.queueDeclare(Constant.QUEUE_NAME_PRISISTENT,true,false,false,null);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                System.out.println("获取消息中。。。。");
                String s = scanner.nextLine();
                String message = String.join(".",s);
                //基本的发送消息方法
                channel.basicPublish("",Constant.QUEUE_NAME_PRISISTENT, MessageProperties.PERSISTENT_BASIC,message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
