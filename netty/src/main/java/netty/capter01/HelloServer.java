package netty.capter01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 使用netty创建的服务器端
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
                                    }
                                });
                            }
                        })
                //7.绑定监听端口
                .bind(8080);
    }
}
