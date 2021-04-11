package netty.capter02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EventLoopServer {
    static final Logger log = LoggerFactory.getLogger(EventLoopServer.class);
    public static void main(String[] args) {
        /*
         netty中线程组可以分为boss以及worker线程组，boss线程组主要负责NioServerSocketChannel上的accept事件，当与客户端建立完连接后，会生成这个连接的NioSocketChannel通道
         worker线程组主要负责NioSocketChannel的读写事件处理，每一个EventLoop包含了一个线程和一个selector，负责管理多个已经建立的连接上（NioSocketChannel）的读写事件
         每一个channel上都有一个handler处理器链，叫做pipeline，每当一个连接建立成功后，默认会调用ChannelInitializer的initChannel方法，给新的连接（即NioSocketChannel）绑定
         多个handler处理器；
         当pipeline中存在耗时事件比较长的handler时，可以单独创建独立的EventLoopGroup绑定处理这个handler逻辑，为了防止影响或阻塞worker线程，因为worker要处理大量的channel，
         如果worker线程阻塞，则会由于某一个handler的耗时操作影响大量的其他channel的读写事件的处理。
         */
        EventLoopGroup diyEventLoopGroup = new DefaultEventLoopGroup(2);
        new ServerBootstrap()
                .group(new NioEventLoopGroup(1),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("firstChannelHandler",new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                String ms = ((ByteBuf) msg).toString(Charset.defaultCharset());
                                log.debug(ms);
                                //调用下一个处理器处理消息
                                ctx.fireChannelRead(msg);
                            }
                        });
                        //第二个处理器模拟耗时操作，为了不影响worker线程对其他channel读写事件的处理，使用新的EventLoopGroup处理第二个handler的事件处理
                        ch.pipeline().addLast(diyEventLoopGroup,"second",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
                                String ms = ((ByteBuf)msg).toString(Charset.defaultCharset());
                                log.debug("second处理,{}",ms);
                            }
                        });
                    }
                }).bind(8080);
    }
}

