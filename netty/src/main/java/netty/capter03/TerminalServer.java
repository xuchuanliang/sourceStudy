package netty.capter03;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制台输入小程序服务器端
 */
public class TerminalServer {
    private static final Logger log = LoggerFactory.getLogger(TerminalServer.class);
    private static final EventLoopGroup PRINT_LOOP = new DefaultEventLoopGroup(2);
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(2);
        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    //boss线程以及worker线程分别处理accept事件以及读写事件
                    .group(boss, worker)
                    //处理NioServerSocketChannel，等待连接建立
                    .channel(NioServerSocketChannel.class)
                    //当建立完成连接后，产生表示连接的NioSocketChannel，为每一个channel初始化handler（处理器）
                    //当连接建立完成后会回调ChannelInitializer的initChannel方法，初始化handler
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("logHandler", new LoggingHandler())//日志打印handler，能够更详细的打印debug日志
                                    .addLast("StringDecoderHandler", new StringDecoder())//字符串解码器，将接收到的字节数组转成字符串
                                    //使用自定义的线程池处理打印事件，不影响这个EventLoop上的其他channel的读写事件的处理
                                    .addLast(PRINT_LOOP, "printHandler", new ChannelInboundHandlerAdapter() {//自定义的处理器，将字符串消息打印出来
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            //当发生读事件后，读取数据
                                            String s = (String) msg;
                                            log.error(s);
                                            //获取到消息后，将消息增加后缀再写回去
                                            ctx.channel().writeAndFlush(s + "--too");
                                        }
                                    })
                                    .addLast("StringEncodeHandler", new StringEncoder())//字符串编码器，在回复消息的时候，对字符串进行编码成字节流
                            ;
                        }
                    }).bind(8080);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            PRINT_LOOP.shutdownGracefully();
        }
    }
}
