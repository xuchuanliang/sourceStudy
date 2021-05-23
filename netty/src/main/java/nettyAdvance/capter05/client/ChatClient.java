package nettyAdvance.capter05.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import nettyAdvance.capter05.client.handler.*;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 聊天室：client端
 */
public class ChatClient {
    private static final Logger log = LoggerFactory.getLogger(ChatClient.class);

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup(5);
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        ChatResponseMessageHandler CHAT_RESPONSE_HANDLER = new ChatResponseMessageHandler();
        GroupCreateResponseMessageHandler GROUP_CREATE_RESPONSE_HANDLER = new GroupCreateResponseMessageHandler();
        GroupChatResponseMessageHandler GROUP_CHAT_RESPONSE_HANDLER = new GroupChatResponseMessageHandler();
        GroupMemberResponseMessageHandler GROUP_MEMBER_RESPONSE_HANDLER = new GroupMemberResponseMessageHandler();
        GroupJoinResponseMessageHandler GROUP_JOIN_RESPONSE_HANDLER = new GroupJoinResponseMessageHandler();
        try{
            ChannelFuture channelFuture = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
//                                    .addLast(LOGGING_HANDLER)
                                    .addLast(MESSAGE_CODEC)
                                    .addLast("client handler",new ClientMainHandler())
                                    .addLast(CHAT_RESPONSE_HANDLER)
                                    .addLast(GROUP_CREATE_RESPONSE_HANDLER)
                                    .addLast(GROUP_CHAT_RESPONSE_HANDLER)
                                    .addLast(GROUP_MEMBER_RESPONSE_HANDLER)
                                    .addLast(GROUP_JOIN_RESPONSE_HANDLER)
                            ;
                        }
                    }).connect(new InetSocketAddress("127.0.0.1", 8080));
            //同步等待建立连接完成
            channelFuture.sync();
            //同步等到连接关闭
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("连接建立失败",e);
        }finally {
            worker.shutdownGracefully();
        }
    }
}
