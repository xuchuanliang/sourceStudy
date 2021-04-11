package netty.capter02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class EventLoopClient {
    private static final Logger log = LoggerFactory.getLogger(EventLoopClient.class);
    public static void main(String[] args) throws Exception {

        ChannelFuture cf = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG))
                                .addLast(new StringEncoder())
                        ;
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));
        //使用sync方法，同步阻塞等到建立完成连接后，获取有效的channel发送消息
//        log.info(cf.channel().toString());
//        cf.sync();
//        log.info(cf.channel().toString());
//        cf.channel().writeAndFlush("hello");
//        System.out.println("");
        //使用listener方法，异步非阻塞，监听建立完成连接后有EventLoop来回调使用有效的channel发送消息
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info(future.channel().toString());
                future.channel().writeAndFlush("hello");
            }
        });

    }
}
