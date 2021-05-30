package nettyAdvance.capter06;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 测试连接超时
 */
public class TestConnectionTimeout {
    public static void main(String[] args) {
//        try {
//            ChannelFuture channelFuture = new Bootstrap()
//                    .channel(NioSocketChannel.class)
//                    .group(new NioEventLoopGroup())
//                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 300)
//                    .handler(new ChannelInitializer<NioSocketChannel>() {
//                        @Override
//                        protected void initChannel(NioSocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new LoggingHandler());
//                        }
//                    })
//                    .connect("127.0.0.1", 8080);
//            channelFuture.sync();
//            channelFuture.channel().closeFuture().sync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println(int.class);
        System.out.println(Integer.class);
    }
}
