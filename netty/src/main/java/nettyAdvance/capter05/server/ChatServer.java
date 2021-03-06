package nettyAdvance.capter05.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nettyAdvance.capter05.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 聊天室 服务器端
 */
public class ChatServer {
    private static final Logger log = LoggerFactory.getLogger(ChatServer.class);

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer()).bind(8080);
            //同步等待启动成功
            channelFuture.sync();
            //同步等待NioServerSocketChannel关闭成功
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("启动失败", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
