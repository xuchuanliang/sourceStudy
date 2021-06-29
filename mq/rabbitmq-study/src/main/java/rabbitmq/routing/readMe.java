package rabbitmq.routing;

public interface readMe {
    /*
    第四章：Routing
        routing 的作用是能够订阅感兴趣的消息，例如所有的日志中分了info/error/warn，可能队列1只感兴趣info类型的日志，队列2只感兴趣warn和error类型的日志
        channel.queueBind(queueName, EXCHANGE_NAME, "")的作用是将exchange与queue建立关系，表示这个queue对这个exchange的message感兴趣
        绑定的第三个参数就是binding key
        exchange的类型：direct、topic、headers、fanout
        为了提升灵活性，使用direct类型的exchange
        当生产者生产消息的时候可以为这个消息指定
        exchange类型：
        fanout类型表示exchange会将消息广播到所有绑定到该exchange的queue
        direct类型表示exchange会将消息根据发送的routing key将消息发送到绑定的queue时指定相同routing key的queue
        topic类型是direct的加强版，他的routing key可以使用点进行分级，比如物种.性别.肤色，那么发送者发送给exchange时可以指定类似于：人类.男.黄色，
        那么消费者在消费消息时，可以指定消费例如人类.*.*，或者人类.男.*的方式，将消息类型进一步细分，或者使用#号符号，详情看官网
        * (star) can substitute for exactly one word.
        # (hash) can substitute for zero or more words.
        Topic exchange是非常强大的，可以通过不同分级的routing key配合*以及#号，表现行为可以类似于fanout或者direct

     */
}
