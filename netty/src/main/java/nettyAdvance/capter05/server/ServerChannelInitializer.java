package nettyAdvance.capter05.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import nettyAdvance.capter05.server.handler.*;

/**
 * ChannelInitializer初始化器
 */
public class ServerChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    /**
     * 注意，下方所有的handler均标记了Sharable，只处理消息，不维护局部变量，所以可以标记为静态属性，并且被共享使用
     */
    private static LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    private static MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
    private static LoginRequestMessageHandler LOGIN_REQUEST_HANDLER = new LoginRequestMessageHandler();
    private static ChatRequestMessageHandler CHAT_REQUEST_HANDLER = new ChatRequestMessageHandler();
    private static GroupCreateRequestMessageHandler GROUP_CREATE_REQUEST_HANDLER = new GroupCreateRequestMessageHandler();
    private static GroupChatRequestMessageHandler GROUP_CHAT_REQUEST_HANDLER = new GroupChatRequestMessageHandler();
    private static GroupMembersRequestMessageHandler GROUP_MEMBERS_REQUEST_HANDLER = new GroupMembersRequestMessageHandler();
    private static GroupJoinRequestMessageHandler GROUP_JOIN_REQUEST_HANDLER = new GroupJoinRequestMessageHandler();
    private static GroupQuitRequestMessageHandler GROUP_QUIT_REQUEST_HANDLER = new GroupQuitRequestMessageHandler();
    private static QuitHandler QUIT_HANDLER = new QuitHandler();
    private static ServerIdleHandler IDLE_HANDLER = new ServerIdleHandler();

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ProtocolFrameDecoder())
//                .addLast(LOGGING_HANDLER)
                .addLast(MESSAGE_CODEC)
                //空闲检测的处理器，具体使用方法和参数可以查看javadoc文档，分别表示检测某个channel多长时间未读取到数据/没向它写出数据/没有发生读写数据，然后会以事件的方式调用：ChannelDuplexHandler
                .addLast(new IdleStateHandler(ServerIdleHandler.READER_IDLE_TIME,ServerIdleHandler.WRITE_IDLE_TIME,ServerIdleHandler.ALL_IDLE_TIME))
                .addLast(IDLE_HANDLER)
                //处理登陆请求，由于上方有解码器，到此handler应该就是解码后特定类型的message
                //所以使用能够处理特定类型的SimpleChannelInboundHandler处理器
                .addLast(LOGIN_REQUEST_HANDLER)
                .addLast(CHAT_REQUEST_HANDLER)
                .addLast(GROUP_CREATE_REQUEST_HANDLER)
                .addLast(GROUP_CHAT_REQUEST_HANDLER)
                .addLast(GROUP_MEMBERS_REQUEST_HANDLER)
                .addLast(GROUP_JOIN_REQUEST_HANDLER)
                .addLast(GROUP_QUIT_REQUEST_HANDLER)
                .addLast(QUIT_HANDLER)
        ;
    }
}
