package caseNetty.capter05.water;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler());
                ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        System.out.println("1");
                        super.write(ctx, msg, promise);
                    }
                });
                ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        System.out.println("2");
                        super.write(ctx, msg, promise);
                    }
                });
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        final ByteBuf buffer = ctx.alloc().buffer();
                        buffer.writeBytes(new byte[]{0,1,0,1});
                        ctx.writeAndFlush(buffer);
                        System.out.println("write end");
                    }
                });
            }
        });
        final ChannelFuture future = serverBootstrap.bind(8080).sync();
        future.channel().closeFuture().addListener(f->{
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        });
        System.out.println("end....");
    }
}
