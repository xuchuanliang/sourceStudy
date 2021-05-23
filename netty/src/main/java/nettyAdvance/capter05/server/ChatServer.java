package nettyAdvance.capter05.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import nettyAdvance.capter05.server.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 聊天室 服务器端
 */
public class ChatServer {
    private static final Logger log = LoggerFactory.getLogger(ChatServer.class);

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        LoginRequestMessageHandler LOGIN_REQUEST_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_REQUEST_HANDLER = new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler GROUP_CREATE_REQUEST_HANDLER = new GroupCreateRequestMessageHandler();
        GroupChatRequestMessageHandler GROUP_CHAT_REQUEST_HANDLER = new GroupChatRequestMessageHandler();
        GroupMembersRequestMessageHandler GROUP_MEMBERS_REQUEST_HANDLER = new GroupMembersRequestMessageHandler();
        GroupJoinRequestMessageHandler GROUP_JOIN_REQUEST_HANDLER = new GroupJoinRequestMessageHandler();
        GroupQuitRequestMessageHandler GROUP_QUIT_REQUEST_HANDLER = new GroupQuitRequestMessageHandler();
        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
//                                    .addLast(LOGGING_HANDLER)
                                    .addLast(MESSAGE_CODEC)
                                    //处理登陆请求，由于上方有解码器，到此handler应该就是解码后特定类型的message
                                    //所以使用能够处理特定类型的SimpleChannelInboundHandler处理器
                                    .addLast(LOGIN_REQUEST_HANDLER)
                                    .addLast(CHAT_REQUEST_HANDLER)
                                    .addLast(GROUP_CREATE_REQUEST_HANDLER)
                                    .addLast(GROUP_CHAT_REQUEST_HANDLER)
                                    .addLast(GROUP_MEMBERS_REQUEST_HANDLER)
                                    .addLast(GROUP_JOIN_REQUEST_HANDLER)
                                    .addLast(GROUP_QUIT_REQUEST_HANDLER)
                            ;
                        }
                    }).bind(8080);
            //同步等待启动成功
            channelFuture.sync();
            //同步等待NioServerSocketChannel关闭成功
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("启动失败", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
