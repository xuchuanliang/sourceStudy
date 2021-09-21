package caseNetty.capter16;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
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
    private static AtomicInteger SEQ = new AtomicInteger(0);
    private static final byte[] ECHO_REQ = new byte[1024 * 1024];
    private static final String DELIMITER = "$_";
    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(eventExecutors)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelTrafficShapingHandler(1024*1024,1024*1024,1000));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                //当建立连接后
                                scheduledExecutorService.scheduleAtFixedRate(() -> {
                                    //每秒钟发送十条，1条1M
                                    ByteBuf buf = null;
                                    for (int i = 0; i < 10; i++) {
                                        buf = Unpooled.copiedBuffer(ECHO_REQ,DELIMITER.getBytes(StandardCharsets.UTF_8));
                                        SEQ.addAndGet(buf.readableBytes());
//                                        if(ctx.channel().isWritable()){
                                            ctx.write(buf);
//                                        }
                                    }
                                    ctx.flush();
                                    int counter = SEQ.getAndSet(0);
                                    System.out.println("客户端发送的速度是：" + counter + " bytes/s");
                                }, 0, 1000, TimeUnit.MILLISECONDS);
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
