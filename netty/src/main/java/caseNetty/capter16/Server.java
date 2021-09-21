package caseNetty.capter16;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 流量整形案例，服务器端
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class)
                .group(boss, worker)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelTrafficShapingHandler(1024 * 1024, 1024 * 1024, 1000));
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes(StandardCharsets.UTF_8));
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(2048 * 1024, delimiter));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new TrafficShapingServerHandler());
                    }
                });
        final ChannelFuture future = serverBootstrap.bind(8080).sync();
        future.channel().closeFuture().addListener((r)->{
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        });
    }

    private static class TrafficShapingServerHandler extends ChannelInboundHandlerAdapter {
        AtomicInteger counter = new AtomicInteger();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        public TrafficShapingServerHandler() {
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                System.out.println("服务器端收到的消息速度是：" + counter.getAndSet(0) + " byte/s");
            }, 0, 1000, TimeUnit.MILLISECONDS);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body = (String) msg;
            counter.addAndGet(body.getBytes(StandardCharsets.UTF_8).length);
            body += "$_";
            ByteBuf echo = Unpooled.copiedBuffer(body.getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(echo);
        }
    }
}
