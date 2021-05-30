package nettyAdvance.capter05.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import nettyAdvance.capter05.server.handler.RpcRequestMessageHandler;

public class RpcServer {
    public static void main(String[] args) {
        LoggingHandler LOGGING_HANDLER = new LoggingHandler();
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcRequestMessageHandler RPC_REQUEST_MESSAGE_HANDLER = new RpcRequestMessageHandler();

        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
//                                    .addLast(LOGGING_HANDLER)
                                    .addLast(MESSAGE_CODEC)
                                    .addLast(RPC_REQUEST_MESSAGE_HANDLER);
                        }
                    }).bind(8080);
            channelFuture.sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
