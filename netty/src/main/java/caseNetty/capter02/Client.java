package caseNetty.capter02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class Client {
    public static void main(String[] args) {
        try {
            initClientPool(60000,"127.0.0.1",8080);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initClientPool(int poolSize, String host, int port) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().channel(NioSocketChannel.class)
                .group(eventExecutors)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                    }
                });
        for (int i = 0; i < poolSize; i++) {
            final ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("启动服务端" + i + "成功");
        }
    }

}
