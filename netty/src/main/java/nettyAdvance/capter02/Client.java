package nettyAdvance.capter02;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * 发送固定长度的消息，由固定长度解码器成功解析
 */
public class Client {
    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        try {
            ChannelFuture channelFuture = new Bootstrap().group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            //发送消息
                            Random random = new Random();
                            ByteBuf buffer = ch.alloc().buffer();
                            for (int i = 0; i < 10; i++) {
                                buffer.writeBytes(initByte((byte) random.nextInt(10 + 1), random.nextInt(10) + 1));
                            }
                            ctx.channel().writeAndFlush(buffer);
                            //发送完毕后，关闭channel，停止客户端
                            ctx.channel().close();
                        }
                    }).addLast(new LoggingHandler(LogLevel.DEBUG));

                }
            }).connect(new InetSocketAddress("localhost", 8080));
            //等待通道连接建立
            channelFuture.sync();
            System.out.println("连接创建成功");
            //等待通道关闭
            channelFuture.channel().closeFuture().sync();
            System.out.print("通道关闭成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static byte[] initByte(byte val, int len) {
        byte[] bytes = new byte[8];
        int sec = 8 - len;
        for (int i = 0; i < 8 && i < len; i++) {
            bytes[i] = val;
        }
        for(int i=0;i<sec;i++){
            bytes[len] = '_';
            len++;
        }
        for(int i=0;i<bytes.length;i++){
            System.out.print(bytes[i]);
        }
        return bytes;
    }
}
