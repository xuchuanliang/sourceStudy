package nettyAdvance.capter01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Client {
    private static Logger log = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        try{
            ChannelFuture localhost = new Bootstrap().group(worker).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    for (int i = 0; i < 10; i++) {
                                        ctx.channel().writeAndFlush(ctx.alloc().buffer(8).writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7}));
                                    }
                                    log.debug("close=====");
                                    ctx.channel().close();
                                }
                            });
                        }
                    }).connect(new InetSocketAddress("localhost", 8080));
            log.debug("before sync:{}",localhost.channel());
            localhost.sync();
            log.debug("after sync:{}",localhost.channel());
            //等待通道关闭
            localhost.channel().closeFuture().sync();
            log.debug("close end====");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
