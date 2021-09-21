package caseNetty.capter18;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 流量整形 客户端以每条10M/s的流量向服务器端发送
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(eventExecutors)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                final ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes(new byte[]{0,0,0,0});
                                ctx.channel().writeAndFlush(buffer);
                            }
                        });
                    }
                });
        final ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
        future.channel().closeFuture().addListener((f)->{
           eventExecutors.shutdownGracefully();
        });

    }
}
