package rabbitmq.rpc;

public interface readMe {
    /*
    第六章：RPC
    Message properties四个常见的属性设置：
        deliveryMode：将消息标记为持久化或者非持久化等功能，也可以直接使用MessageProperties.xxx
        contentType：可以用于描述编码的MIME类型，例如使用JSON编码，可以设置ContentType是application/json
        replyTo：通常用于命名回调队列
        correlationId：有助于将RPC请求和响应关联起来
        使用PRC功能，建议为每一个请求创建一个RPC回调队列
        为了能够让请求和响应对应起来，使用correlationId设置一个唯一值，用于标记某一个请求并且能够根据这个值识别对应的响应


     */
}
