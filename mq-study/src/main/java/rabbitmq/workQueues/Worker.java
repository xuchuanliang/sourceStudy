package rabbitmq.workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import rabbitmq.Constant;

import java.nio.charset.StandardCharsets;

/**
 * 消费者:多个消费者，rabbitmq会依次将消息发送给消费者
 * 为了确保rabbitmq不会丢失消息，提供了message acknowledgments机制，
 * consumer发送给rabbitmq的acknowledgments的意思是告诉rabbitmq这条消息已经被接收、处理完成，并且rabbitmq可以自由的删除这个消息了。
 * 如果consumer挂掉了（channel关闭，connection关闭，TCP挂掉等），那么rabbitmq就不会收到ack，rabbitmq会将该消息重新放在队列中，如果有其他存活的consumer，就会将其发送给存活的consumer，这样就能保证消息不会丢失。
 * 默认acknowledgments是false，表示需要手动确认；如果设置ack为true，自动确认意味着rabbit客户端只要收到消息就会向mq服务器发送ack，如果此时客户端处理数据失败，则本条消息就会丢失，
 * 所以一般建议通过channel手动确认的方式保证消息处理完成后再进行手动确认。
 * ack的响应必须是与消息接收的channel是同一个，如果接收消息的channel和响应ack的channel不是同一个，那么会报错。
 *
 *
 */
public class Worker {
    public static void main(String[] args){
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        //连接:注意，消费者严禁将connection创建在try()中，因为会导致main执行完成后，关闭connection，导致消费者线程关闭
        try{
            Connection connection = connectionFactory.newConnection();
            //channel
            Channel channel = connection.createChannel();
            //设置消息预处理数量是1个，表示一次只获取一个消息
            channel.basicQos(1);
            //指定接收消息队列
            channel.queueDeclare(Constant.QUEUE_NAME_PRISISTENT, true, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                String msg = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + msg + "'");
                try {
                    doWork(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                }
            };
            boolean autoAck = false;//开启自动ack或关闭自动ack
            channel.basicConsume(Constant.QUEUE_NAME_PRISISTENT, autoAck, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 模拟处理耗时任务
     * @param task
     * @throws InterruptedException
     */
    public static void doWork(String task) throws InterruptedException {
        for(char c:task.toCharArray()){
            if(c == '.'){
                Thread.sleep(1000);
            }
        }
    }
}
