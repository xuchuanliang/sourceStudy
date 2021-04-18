package chat.server;

import chat.message.LoginRequestMessage;
import chat.message.LoginResponseMessage;
import chat.protocol.MessageCodec;
import chat.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ChatServer {


    private static void startup() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(4);
        //loggHandler有@Shared标记，表示可以被多个channel共享
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        /**
         * 我们自定义的MessageCodec是无状态的，继承了MessageToMessageCodec，可以是线程安全的，标记为@Shareable
         */
        MessageCodec messageCodec = new MessageCodec();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(loggingHandler)
                                    /*
                                    处理半包和粘包问题:
                                    maxFrameLength:一条消息最大1024个字节；
                                    lengthFieldOffset和lengthFieldLength:从第12个字节开始的4个字节表示消息体长度；
                                    lengthAdjustment:表示长度的字节之后紧接着就是消息体，长度与消息体之间间隔0个字节；
                                    initialBytesToStrip:表示获取完整的消息，需要截取的长度是0
                                     */
                                    .addLast(new ProtocolFrameDecoder())
                                    //我们自定义的MessageCodec继承了ByteToMessageCodec，ByteToMessageCodec的注释中说明了不能被共享，所以不是线程安全的
                                    .addLast(messageCodec)
                                    .addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                                            ctx.writeAndFlush(new LoginResponseMessage(true, "登陆成功"));
                                        }
                                    });
                        }
                    });
            //同步等到NioServerSocketChannel建立成功
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            System.out.print("==================");
            //同步监听NioServerSocketChannel关闭事件
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        startup();
    }
}
