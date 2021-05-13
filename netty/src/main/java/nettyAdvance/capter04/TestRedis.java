package nettyAdvance.capter04;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * 测试使用netty连接redis
 */
public class TestRedis {
    /**
     * redis 协议的特点：
     * 例如：set name xuchuanliang
     * *3 表示由三个命令组成
     * $3 表示后面的命令有三个字节
     * set
     * $4 表示后面的命令由4个字节
     * name
     * $12 表示后面的命令有12个字节
     * xuchuanliang
     * @param args
     */
    public static void main(String[] args) {
        //换行符
        final byte[] LINE = {13,10};
        final LoggingHandler loggingHandler = new LoggingHandler();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //当socket通道建立成功后，发送消息
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    buffer.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("name".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("$12".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    buffer.writeBytes("xuchuanliang".getBytes(StandardCharsets.UTF_8));
                                    buffer.writeBytes(LINE);
                                    ctx.writeAndFlush(buffer);
                                }
                            });
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            //获取redis响应命令
                            ByteBuf buf = (ByteBuf) msg;
                            System.out.println(buf.toString(CharsetUtil.UTF_8));
                        }
                    }).connect(new InetSocketAddress("192.168.109.139", 6379));
            channelFuture.sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
        }

    }
}
