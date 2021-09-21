package caseNetty.capter05.pile;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 客户端不断向服务器端发送消息，服务器端断点等待，模拟消息堆压
 */
public class Client {
    public static void main(String[] args) {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.channel(NioSocketChannel.class);
            b.group(work);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.channel().unsafe().outboundBuffer().totalPendingWriteBytes();
                            //设置写出数据时的高水位，大小是10M
//                            ctx.channel().config().setWriteBufferHighWaterMark(10 * 1024 * 1024);
                            new Thread(() -> {
                                try {
                                    TimeUnit.SECONDS.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("action");
                                while (true) {
                                    //只有当写缓冲器还未到高水位时，表示可写，才写出
//                                    if (ctx.channel().isWritable()) {
                                        final ByteBuf buf = Unpooled.wrappedBuffer("netty oom".getBytes(StandardCharsets.UTF_8));
                                        ctx.channel().write(buf);
//                                    } else {
//                                        System.err.println("the write queue is busy:" + ctx.channel().unsafe().outboundBuffer().nioBufferSize());
//                                    }
                                }
                            }).start();
                        }
                    });
                }
            });
            ChannelFuture channelFuture = b.connect("127.0.0.1", 8888);
            channelFuture.sync();
            channelFuture.channel().closeFuture().addListener(future -> work.shutdownGracefully());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
