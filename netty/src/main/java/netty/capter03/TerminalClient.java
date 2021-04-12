package netty.capter03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * 控制台输入客户端，用户在控制台输入信息，发送给服务器端，服务器端打印
 * 当用户输入q之后，则退出客户端
 */
public class TerminalClient {
    private static final Logger log = LoggerFactory.getLogger(TerminalServer.class);
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                //处理读写事件的线程组
                .group(eventLoopGroup)
                //建立NioSocketChannel类型
                .channel(NioSocketChannel.class)
                //连接链接成功后会回调ChannelInitializer的initChannel方法，为这个链接对象初始化handler处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringEncoder())//发送的内容均使用字符串进行编码成为字节流发送
                                .addLast(new LoggingHandler())//打印日志更加详细
                                .addLast(new StringDecoder())//字符串解码器，接收到服务器端的消息从字节流解码成字符串
                                .addLast(new ChannelInboundHandlerAdapter(){//自定义的输入handler，获取服务器响应的消息
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        String s = (String) msg;
                                        log.error(s);
                                    }
                                })
                        ;
                    }
                }).connect(new InetSocketAddress("localhost", 8080));//连接本机的8080端口
        //同步直到连接建立成功，如果不使用这一步，获取到的channel很可能是一个还未成功建立的channel对象
        channelFuture.sync();
        //获取建立成功后的channel
        Channel channel = channelFuture.channel();
        //获取控制台输入，并写给服务器端
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入要表达的内容，输入q结束本次对话");
        while (true){
            String readLine = reader.readLine();
            if(!"q".equals(readLine)){
                channel.writeAndFlush(readLine);
            }else{
                System.out.println("byebye!");
                //channel关闭也是异步操作，等待关闭后进行资源释放
                ChannelFuture closeFuture = channel.close();
                closeFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        eventLoopGroup.shutdownGracefully();
                        System.out.println("关闭成功");
                    }
                });
                break;
            }
        }
    }
}
