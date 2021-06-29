package rabbitmq.publisherConfirms;

public interface readMe {
    /*
    第七章：Publisher Confirms
    消息发送确认是channel级别的，如果需要消息确认，需要在每创建一个Channel的时候调用channel.confirmSelect()方法开发发送确认
    发布完消息后可以使用Channel#waitForConfirmsOrDie(long)方法同步等待发送结果
    但是开启发送确认，并同步等待消息发送成功是非常影响吞吐量
    可以使用channel.addConfirmListener()添加监听器的方式，异步等待消息发送成功

    异步确认消息发送成功的机制是，使用channel.getNextPublishSeqNo());获取将要发送消息的唯一标识，将这个表示与消息使用map在jvm里面关联起来，然后通过接收确认回调，如果发送成功删除，如果失败则打印或重发或其他操作
     */
}
