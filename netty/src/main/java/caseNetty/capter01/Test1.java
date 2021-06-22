package caseNetty.capter01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Test1 {
//    public static void main(String[] args) {
//        EventLoopGroup boss = new NioEventLoopGroup();
//        EventLoopGroup work = new NioEventLoopGroup();
//        try{
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(boss,work)
//                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG,100)
//                    .handler(new LoggingHandler())
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ChannelPipeline p = ch.pipeline();
//                            p.addLast(new LoggingHandler(LogLevel.INFO));
//                        }
//                    });
//            ChannelFuture channelFuture = b.bind(8080).sync();
//            channelFuture.channel().closeFuture().sync();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            System.err.println("--------程序以外退出----------");
//            boss.shutdownGracefully();
//            work.shutdownGracefully();
//        }
//    }


    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                        }
                    });
            ChannelFuture channelFuture = b.bind(8080).sync();
            channelFuture.channel().closeFuture().addListener(future -> {
                System.err.println("--------程序退出----------");
                boss.shutdownGracefully();
                work.shutdownGracefully();
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
