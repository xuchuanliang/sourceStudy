package nettyAdvance.capter01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 演示半包和粘包问题
 */
public class Server {
    private static Logger log = LoggerFactory.getLogger(Server.class);
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup(2);
        try{
            ChannelFuture bind = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_RCVBUF,1)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        }
                    }).bind(8080);
        log.debug("before sync:{}",bind.channel());
        //等待建立成功
        bind.sync();
        log.debug("after sync:{}",bind.channel());
        //等待channel关闭，一般情况下channel不会关闭，因为我们并没有手动关闭channel
        bind.channel().closeFuture().sync();
        }catch (Exception e){
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
