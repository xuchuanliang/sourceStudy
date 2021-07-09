package rabbitmq.publishSubscribe;

public interface readMe {
    /*
    第三章：publish/subscribe
    发布订阅模式，与前面的queue模式的最大区别是，queue的消息是一次消费，pub/sub模式是一个消息多次消费
    在rabbitmq中生产者不是直接发送消息到queue，而是发送消息到exchange，exchange一方面从生产者接收消息，另一方面是将消息push到queue中；
    exchange来决定是将消息发送到一个已知queue，还是发送到多个queue，还是丢弃这个消息。
    exchange包含多个类型：direct、topic、headers、fanout
    在发送消息时，如果不指定exchange，实际上rabbitmq默认有一个名称为""的exchange用来发送消息。
    如果需要某exchange发送消息到某个或某些queue，那么要有一个绑定过程，即将exchange与某一个/多个queue绑定到一起
    exchange要先创建，否则直接发送消息到一个没创建的exchange是不被允许的；
    如果没有queue绑定到exchange，那么生产者向该exchange发送的消息会丢失
     */
}
