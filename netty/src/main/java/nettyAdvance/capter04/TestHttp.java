package nettyAdvance.capter04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 测试http协议
 * 使用netty提供的http编解码器：HttpCodec
 */
public class TestHttp {
    private static Logger logger = LoggerFactory.getLogger(TestHttp.class);
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(2);
        try{
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LoggingHandler())
                                    .addLast(new HttpServerCodec())
                                    .addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                            //获取HttpRequest
                                            logger.info(msg.method().name());
                                            logger.info(msg.uri());
                                            byte[] bs = "<h1>hello xuchuanliang!</h1>".getBytes(StandardCharsets.UTF_8);
                                            HttpResponse response = new DefaultFullHttpResponse(msg.protocolVersion(),HttpResponseStatus.OK,ctx.alloc().buffer().writeBytes(bs));
                                            response.headers().addInt(HttpHeaderNames.CONTENT_LENGTH,bs.length);
                                            ctx.writeAndFlush(response);
                                        }
                                    })
                                    .addLast(new SimpleChannelInboundHandler<HttpContent>() {
                                        //获取HttpContent：post类型时有请求头
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {
                                            logger.debug("暂时跳过content");
                                        }
                                    });
                        }
                    }).bind(8080);
            channelFuture.sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
