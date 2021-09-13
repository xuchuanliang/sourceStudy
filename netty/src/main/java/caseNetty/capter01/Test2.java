package caseNetty.capter01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        client();
    }

    private static void holk() throws InterruptedException {
        //通过给Runtime添加钩子监听系统退出
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("ShutdownHook execute start....");
            System.out.println("Netty NioEventLoopGroup shutdownGracefully....");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ShutdownHook execute end....");
        }));
        TimeUnit.SECONDS.sleep(7);
        System.exit(0);

        //通过监听信号量来监听系统退出
        //1.获取系统对应的信号名称
        String signal = System.getProperties().getProperty("os.name").toLowerCase().startsWith("win") ? "INT" : "TERM";
        Signal sig = new Signal(signal);
        Signal.handle(sig, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                //针对不同的信号量做出不同的反应
            }
        });
    }

    public static void client() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler());
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        ByteBuf buffer = ctx.alloc().buffer();
                        buffer.writeBytes("hello".getBytes(StandardCharsets.UTF_8));
                        ctx.writeAndFlush(buffer);
                        ctx.channel().close().sync();
                    }
                });
            }
        });
        ChannelFuture future = bootstrap.connect("localhost", 8080).sync();;
        future.channel().closeFuture().sync();
    }
}
