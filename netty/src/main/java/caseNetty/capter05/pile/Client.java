package caseNetty.capter05.pile;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
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
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            while (true){
                                ByteBuf buffer = ctx.alloc().buffer(20 * 1024 * 1024);
                                buffer.writeBytes(new byte[20 * 1024 * 1024]);
                                ctx.write(buffer);
                                TimeUnit.MILLISECONDS.sleep(1);
                            }
                        }
                    });
                }
            });
            ChannelFuture channelFuture = b.connect("127.0.0.1", 8888);
            channelFuture.sync();
            channelFuture.channel().closeFuture().addListener(future -> work.shutdownGracefully());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
        }
    }
}
