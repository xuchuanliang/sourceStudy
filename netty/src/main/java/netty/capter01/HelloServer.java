package netty.capter01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 使用netty创建的服务器端
 *
 * 按照java nio的概念理解netty流程：
 * 当使用NioServerSocketChannel接收到客户端的请求之后，就会有一个代表连接的channel对象：NioSocketChannel
 * 这时候netty对这个NioSocketChannel做了封装，然后调用ChannelInitializer的initChannel方法，
 * 向这个代表连接通道的Channel的属性中添加了一系列的处理器（handler），然后当使用selector检测到这个channel有事件发生，
 * 就会逐个调用这个channel处理器中的一个个的handler进行处理
 *
 *
 */
public class HelloServer {
    public static void main(String[] args) {
        //1.启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2.与nio中讲的BossEventLoop,WorkerEventLoop(selector,thread)概念类似，是一个不断循环处理时间的组，主要是为了将接收请求建立连接，与处理请求读写事件的操作使用不同的selector以及线程分开
                .group(new NioEventLoopGroup())
                //3.选择服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //4.boss负责处理连接，worker(child)负责处理读写，决定worker(child)能执行哪些操作(handler)
                .childHandler(
                        //5.channel代表和客户端进行数据读写的通道，Initializer 初始化，负责添加别的handler,
                        //在运行成功后就会调用ChannelInitializer进行初始化所有的childHandler，会根据泛型将handler应用到对应类型的channel上
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                //6.添加具体的handler
                                nioSocketChannel.pipeline().addLast(new StringDecoder()); //将ByteF=Buf转成字符串
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {//自定义的handler
                                    //读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        //打印上一步的StringDecoder转换好的字符串
                                        System.out.println(msg);
                                        ctx.close();
                                    }

                                    @Override
                                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelRegistered");
                                        super.channelRegistered(ctx);
                                    }

                                    @Override
                                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelUnregistered");
                                        super.channelUnregistered(ctx);
                                    }

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelActive");
                                        super.channelActive(ctx);
                                    }

                                    @Override
                                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelInactive");
                                        super.channelInactive(ctx);
                                    }

                                    @Override
                                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelReadComplete");
                                        super.channelReadComplete(ctx);
                                    }

                                    @Override
                                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                        System.out.println("userEventTriggered");
                                        super.userEventTriggered(ctx, evt);
                                    }

                                    @Override
                                    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("channelWritabilityChanged");
                                        super.channelWritabilityChanged(ctx);
                                    }

                                    @Override
                                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                        System.out.println("exceptionCaught");
                                        super.exceptionCaught(ctx, cause);
                                    }
                                });
                            }
                        })
                //7.绑定监听端口
                .bind(8080);
    }
}
