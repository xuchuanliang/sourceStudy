package nettyAdvance.capter05.client;

import chat.message.LoginRequestMessage;
import chat.message.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * 聊天室：client端
 */
public class ChatClient {
    private static final Logger log = LoggerFactory.getLogger(ChatClient.class);
    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup(5);
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        try{
            ChannelFuture channelFuture = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
                                    .addLast(LOGGING_HANDLER)
                                    .addLast(MESSAGE_CODEC)
                                    .addLast("client handler",new ChannelInboundHandlerAdapter(){
                                        //客户端在与服务器端建立完成会触发active事件，负责向服务器发送各种消息
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            //为了不阻塞netty的nio线程，创建新的线程负责阻塞从控制台读取消息，向服务器发送各种消息
                                            new Thread(()->{
                                                Scanner scanner = new Scanner(System.in);
                                                System.out.println("请输入用户名：");
                                                String username = scanner.nextLine();
                                                System.out.println("请输入密码：");
                                                String password = scanner.nextLine();
                                                //构造登陆请求对象
                                                Message message = new LoginRequestMessage(username,password);
                                                //发送登陆请求
                                                //此时会触发出栈操作，先通过MessageCodec对Message进行编码，然后通过LoggingHandler进行日志记录，最终从客户端写出到服务器端
                                                ctx.writeAndFlush(message);
                                                System.out.println("等待后续操作..");
                                                try {
                                                    System.in.read();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            },"system in")
                                                    .start();
                                        }
                                    })
                            ;
                        }
                    }).connect(new InetSocketAddress("127.0.0.1", 8080));
            //同步等待建立连接完成
            channelFuture.sync();
            //同步等到连接关闭
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("连接建立失败",e);
        }finally {
            worker.shutdownGracefully();
        }
    }
}
