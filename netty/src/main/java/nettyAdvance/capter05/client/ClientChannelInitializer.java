package nettyAdvance.capter05.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import nettyAdvance.capter05.client.handler.*;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import nettyAdvance.capter05.server.ServerIdleHandler;

/**
 * 客户端channel处理器初始化器
 */
public class ClientChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    /**
     * 注意，下方所有的handler均标记了Sharable，只处理消息，不维护局部变量，所以可以标记为静态属性，并且被共享使用
     */
    LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
    ChatResponseMessageHandler CHAT_RESPONSE_HANDLER = new ChatResponseMessageHandler();
    GroupCreateResponseMessageHandler GROUP_CREATE_RESPONSE_HANDLER = new GroupCreateResponseMessageHandler();
    GroupChatResponseMessageHandler GROUP_CHAT_RESPONSE_HANDLER = new GroupChatResponseMessageHandler();
    GroupMemberResponseMessageHandler GROUP_MEMBER_RESPONSE_HANDLER = new GroupMemberResponseMessageHandler();
    GroupJoinResponseMessageHandler GROUP_JOIN_RESPONSE_HANDLER = new GroupJoinResponseMessageHandler();

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ProtocolFrameDecoder())
              .addLast(LOGGING_HANDLER)
                //空闲检测的处理器，具体使用方法和参数可以查看javadoc文档，分别表示检测某个channel多长时间未读取到数据/没向它写出数据/没有发生读写数据，然后会以事件的方式调用：ChannelDuplexHandler
                .addLast(new IdleStateHandler(ClientIdleHandler.READER_IDLE_TIME,ClientIdleHandler.WRITE_IDLE_TIME,ClientIdleHandler.ALL_IDLE_TIME))
                .addLast(MESSAGE_CODEC)
                .addLast("client handler",new ClientMainHandler())
                .addLast(new ClientIdleHandler())
                .addLast(CHAT_RESPONSE_HANDLER)
                .addLast(GROUP_CREATE_RESPONSE_HANDLER)
                .addLast(GROUP_CHAT_RESPONSE_HANDLER)
                .addLast(GROUP_MEMBER_RESPONSE_HANDLER)
                .addLast(GROUP_JOIN_RESPONSE_HANDLER)
        ;
    }
}
