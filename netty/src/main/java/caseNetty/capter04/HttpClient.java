package caseNetty.capter04;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.DefaultPromise;
import nio.capter02.socket.Client;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

public class HttpClient {
    private Channel channel;
    private void connect(String host,int port) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        b.group(eventExecutors);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpClientCodec());
                ch.pipeline().addLast(new HttpObjectAggregator(Short.MAX_VALUE));
            }
        });
        ChannelFuture f = b.connect(host,port).sync();
        channel = f.channel();
    }

    private HttpResponse blockSend(FullHttpRequest request) throws ExecutionException, InterruptedException {
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
        DefaultPromise<HttpResponse> respPromise = new DefaultPromise<>(channel.eventLoop());
        channel.writeAndFlush(request);
        HttpResponse response = respPromise.get();
        if(response != null){
            System.out.println("body is "+new String(response.toString()));
        }
        return response;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        HttpClient httpClient = new HttpClient();
        httpClient.connect("127.0.0.1",18004);
        ByteBuf body = Unpooled.wrappedBuffer("Http message!".getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"Http://127.0.0.1/user?id=10&addr=nanjing",body);
        HttpResponse response = httpClient.blockSend(request);
    }
}
