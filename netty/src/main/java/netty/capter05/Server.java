package netty.capter05;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(1),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("InH1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //第一个channelInbound接收到的msg一般是ByteBuf类型，将它转成String类型
                                logger.debug("inh1");
                                ByteBuf byteBuf = (ByteBuf)msg;
                                String name = byteBuf.toString(CharsetUtil.UTF_8);
                                super.channelRead(ctx,name);
                            }
                        });
                        ch.pipeline().addLast("InH2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //第二个获取到的是String类型的名称，将它转成Student对象
                                logger.debug("inh2");
                                String name = (String)msg;
                                Student student = new Student(name);
                                super.channelRead(ctx,student);
                            }
                        });

                        ch.pipeline().addLast("InH3",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //第三个获取到的就是student对象，由于第三个就已经到了处理器的最后一个handler，所以不需要再super调用后一个处理器了
                                logger.debug("inh3");
                                Student student = (Student) msg;
                                logger.debug("student is {},class is {}",student,student.getClass());
                                //写出一个ByteBuf
//                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("hello".getBytes(StandardCharsets.UTF_8)));
//                                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("hello".getBytes(StandardCharsets.UTF_8)));
                                ctx.channel().writeAndFlush(ctx.alloc().buffer().writeBytes("hello".getBytes(StandardCharsets.UTF_8)));
                            }
                        });

                        ch.pipeline().addLast("outH1",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                logger.debug("outH1");
                                super.write(ctx, msg, promise);
                            }
                        });

                        ch.pipeline().addLast("outH2",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                logger.debug("outH2");
                                super.write(ctx, msg, promise);
                            }
                        });

                        ch.pipeline().addLast("outH3",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                logger.debug("outH3");
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                }).bind(8080);
    }

}

class Student{
    String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}