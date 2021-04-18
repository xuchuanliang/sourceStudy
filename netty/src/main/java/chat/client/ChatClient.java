package chat.client;

import chat.message.ChatResponseMessage;
import chat.message.LoginRequestMessage;
import chat.protocol.MessageCodec;
import chat.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Scanner;

public class ChatClient {
    private static void startup() {
        //loggHandler有@Shared标记，表示可以被多个channel共享
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        /**
         * 我们自定义的MessageCodec是无状态的，继承了MessageToMessageCodec，可以是线程安全的，标记为@Shareable
         */
        MessageCodec messageCodec = new MessageCodec();
        NioEventLoopGroup worker = new NioEventLoopGroup(2);
        //专门处理业务消息的线程
        DefaultEventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup(4,r -> {return new Thread(r, "systemInReader");});
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(loggingHandler)
                                    .addLast(new ProtocolFrameDecoder())
                                    .addLast(messageCodec)
                                    .addLast(defaultEventLoopGroup, new DealLoginHandler())
                                    .addLast(defaultEventLoopGroup,new LoginSuccessHandler());
                        }
                    }).connect("localhost", 8080);
            //等待连接建立成功
            channelFuture.sync();
            System.out.print("===连接建立成功");
            //阻塞等待连接通道关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        startup();
    }
}

/**
 * 处理业务登陆的处理器
 */
class DealLoginHandler extends ChannelInboundHandlerAdapter{
    /**
     * 当成功建立连接后登陆
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当连接建立成功后，不断读取控制台消息，发送给服务器端
        //为了防止阻塞处理channel的nio线程，此处需要使用新的线程处理
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名");
        String username = sc.nextLine();
        System.out.print("请输入密码");
        String pwd = sc.nextLine();
        //使用ctx写出message:此时会经过当前handler传递到messageCodec进行编码称为ByteBuf写到服务器
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, pwd);
        ctx.writeAndFlush(loginRequestMessage);
        System.out.println("等待中..");
        sc.nextLine();
    }
}

/**
 * 处理响应消息的处理器类
 */
class LoginSuccessHandler extends SimpleChannelInboundHandler<ChatResponseMessage>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
        if(msg.isSuccess()){
            System.out.print("登陆成功");
        }else{
            System.out.print("登陆失败");
        }
        System.out.print(msg);
    }
}
