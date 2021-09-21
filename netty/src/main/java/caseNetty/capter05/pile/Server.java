package caseNetty.capter05.pile;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.TimeUnit;

/**
 * 模拟消息堆压服务端
 */
public class Server {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try{
            b.group(boss,work);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println(msg);
                            TimeUnit.MICROSECONDS.sleep(10);
                            //保证msg被正确释放，要么手动释放，要么交给netty释放
                            ctx.fireChannelRead(msg);
                        }
                    });
                }
            });
            //监听端口并同步等待
            ChannelFuture bind = b.bind(8888);
            bind.sync();
            bind.channel().closeFuture().addListener(future -> {
                boss.shutdownGracefully();
                work.shutdownGracefully();
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
