package rabbitmq.workQueues;

public interface readMe {
    /*
      消费者:多个消费者，rabbitmq会依次将消息发送给消费者
      ack相关（保证消息不丢失）：
          为了确保rabbitmq不会丢失消息，提供了message acknowledgments机制，
          consumer发送给rabbitmq的acknowledgments的意思是告诉rabbitmq这条消息已经被接收、处理完成，并且rabbitmq可以自由的删除这个消息了。
          如果consumer挂掉了（channel关闭，connection关闭，TCP挂掉等），那么rabbitmq就不会收到ack，rabbitmq会将该消息重新放在队列中，如果有其他存活的consumer，就会将其发送给存活的consumer，这样就能保证消息不会丢失。
          默认acknowledgments是false，表示需要手动确认；如果设置ack为true，自动确认意味着rabbit客户端只要收到消息就会向mq服务器发送ack，如果此时客户端处理数据失败，则本条消息就会丢失，
          所以一般建议通过channel手动确认的方式保证消息处理完成后再进行手动确认。
          ack的响应必须是与消息接收的channel是同一个，如果接收消息的channel和响应ack的channel不是同一个，那么会报错。
          注意：
          ack手动确认模式下，当有大量的消息已经发送给consumer，但是没有获取到对应的ack，那么会导致rabbitmq内存占用急剧升高，因为不能释放消息的锁；
      消息持久化相关（message durability）：
        消息持久化包含两个部分：队列持久化和消息持久化
        队列持久化只需要在使用channel指定队列的时候，参数durable为true即可，需要在consumer和product中都执行队列持久化为true
        消息持久化需要在生产者生产消息时，指定props参数为MessageProperties.PERSISTENT_BASIC，表示消息要持久化
        但是主要消息持久化并不能保证生产的消息一定不会丢失，有可能会出现rabbitmq还没有将消息持久化到磁盘，还在缓存中就crash，导致丢失消息，因为rabbitmq不是收到消息就会强制写入磁盘，而是写到缓冲区；
        如果需要强烈保证消息不会丢失，可以考虑发布消息确认：https://www.rabbitmq.com/confirms.html
      公平调度
        假设一个生产者，两个消费者，如果恰好按照顺序奇数任务耗时，偶数任务不耗时，那么默认情况下rabbitmq是将消息依次发送给两个消费者，及消费者1只消费奇数，消费者只消费2，那么会导致消费奇数的消费者压力比较大；
        可以通过channel.basicQos(1);来设置只有在rabbitmq收到消费者的ack之后才会向该消费者发送消息，这是设置预处理消息数量。
     */
}
