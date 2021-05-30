package nettyAdvance.capter05.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import nettyAdvance.capter05.client.handler.RpcResponseMessageHandler;
import nettyAdvance.capter05.message.RpcRequestMessage;
import nettyAdvance.capter05.protocol.MessageCodecSharable;
import nettyAdvance.capter05.protocol.ProtocolFrameDecoder;
import nettyAdvance.capter05.protocol.SequenceIdGenerator;
import nettyAdvance.capter05.server.service.HelloService;

import java.lang.reflect.Proxy;

/**
 * Rpc客户端的管理器，主要职责是初始化Channel，因为Rpc调用最终都是通过Channel向服务器端发送请求
 * 但是客户端与服务器端的连接只有一个，所以符合单例模式
 */
public final class RpcClientManager {
    private static volatile Channel channel = null;
    private static final Object LOCK = new Object();

    /**
     * 弊端：
     * 1.直接将netty的channel暴露给使用者，不安全；
     * 2.使用这自己拼凑RpcRequestMessage，不友好
     * 目标：使用者直接调用接口的方法，就可以正常的向服务器端发送消息：1.代理类代理接口方法；2.代理类向服务器发送内容
     * @param args
     */
    public static void main(String[] args) {
        //尝试使用channel发送消息给服务器端
//        getChannel().writeAndFlush(new RpcRequestMessage(1,
//                "nettyAdvance.capter05.server.service.HelloService",
//                "sayHello",
//                String.class,
//                new Class[]{String.class},
//                new Object[]{"徐传良"}));
        HelloService helloService = getProxyInstance(HelloService.class);
        String hello = helloService.sayHello("徐传良1");
        System.out.println(hello);
        hello = helloService.sayHello("徐传良2");
        System.out.println(hello);
    }

    /**
     * 根据接口创建代理类，实现直接调用方法就可以向服务器端发送消息
     * @param inf
     * @param <T>
     * @return
     */
    public static  <T> T getProxyInstance(Class<T> inf){
        if(!inf.isInterface()){
            throw new RuntimeException("不是接口");
        }
        return (T) Proxy.newProxyInstance(inf.getClassLoader(), new Class[]{inf}, (proxy, method, args) -> {
           //1.构造RpcRequestMessage
            RpcRequestMessage rpcRequestMessage = new RpcRequestMessage(SequenceIdGenerator.nextId(),
                    inf.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args);
            System.out.println(method.getName()+"=======================================");
            //2.使用channel发送给服务器端
            getChannel().writeAndFlush(rpcRequestMessage);
            //3.将本次请求以及对应的Promise添加到集合中
            RpcResponseMessageHandler.PROMISES.put(rpcRequestMessage.getSequenceId(),new DefaultPromise<>(getChannel().eventLoop()));
            //4.同步返回结果
            //如何同步获取结果：使用sequenceId结合Promise来完成
            Promise<Object> promise = RpcResponseMessageHandler.PROMISES.get(rpcRequestMessage.getSequenceId());
            //同步等待
            promise.await();
            if (promise.isSuccess()) {
                return promise.getNow();
            }else{
//                抛出异常
                throw new RuntimeException(promise.cause());
            }
        });
    }


    public static Channel getChannel(){
        //由于存在多线程竞争获得channel行为，所以需要锁进行同步
        //双重检查锁
        if(channel!=null){
            return channel;
        }
        synchronized (LOCK){
            if(channel!=null){
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    /**
     * 初始化channel
     */
    private static void initChannel(){
        LoggingHandler LOGGING_HANDLER = new LoggingHandler();
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcResponseMessageHandler RPC_RESPONSE_MESSAGE_HANDLER = new RpcResponseMessageHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ProtocolFrameDecoder())
//                                .addLast(LOGGING_HANDLER)
                                .addLast(MESSAGE_CODEC)
                                .addLast(RPC_RESPONSE_MESSAGE_HANDLER);
                    }
                }).connect("127.0.0.1",8080);
        try {
            //同步等待连接建立成功
            bootstrap.sync();
            channel = bootstrap.channel();
            //异步监听channel关闭；这里之所以使用异步，是因为获取channel的线程不可能同步等待关闭，应该是直接就获取到channel使用
            bootstrap.channel().closeFuture().addListener(future -> {
                //当channel关闭后，关闭线程池
                group.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
