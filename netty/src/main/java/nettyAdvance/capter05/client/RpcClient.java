package nettyAdvance.capter05.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import nettyAdvance.capter05.client.handler.RpcResponseMessageHandler;
import nettyAdvance.capter05.message.RpcRequestMessage;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcClient {
    private static final Logger log = LoggerFactory.getLogger(RpcClient.class);
    public static void main(String[] args) {
        LoggingHandler LOGGING_HANDLER = new LoggingHandler();
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcResponseMessageHandler RPC_RESPONSE_MESSAGE_HANDLER = new RpcResponseMessageHandler();
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
                                    .addLast(LOGGING_HANDLER)
                                    .addLast(MESSAGE_CODEC)
                                    .addLast(RPC_RESPONSE_MESSAGE_HANDLER);
                        }
                    }).connect("127.0.0.1", 8080);
            channelFuture.sync();
            ChannelFuture sayHello = channelFuture.channel().writeAndFlush(new RpcRequestMessage(1,
                    "nettyAdvance.capter05.server.service.HelloService",
                    "sayHello",
                    String.class,
                    new Class[]{String.class},
                    new Object[]{"徐传良"}));
            sayHello.addListener(promise->{
                if (!promise.isSuccess()) {
                    Throwable cause = promise.cause();
                    log.error("error",cause);
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
