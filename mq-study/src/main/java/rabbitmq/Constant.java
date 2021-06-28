package rabbitmq;

public interface Constant {
    //队列名称
    String QUEUE_NAME = "hello";

    String QUEUE_NAME_PRISISTENT = "hello2";

    //虚拟机地址
    String HOST = "192.168.109.139";

    //日志分发的exchange
    String LOGS_EXCHANGE = "logs";
}
