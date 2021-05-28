package hbTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

public class Server {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeByte(0XCF);
        byteBuf.writeByte(0XCF);
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
//        EventLoopGroup boss = new NioEventLoopGroup();
//        EventLoopGroup work = new NioEventLoopGroup();
//        try{
//            new ServerBootstrap()
//            .group(boss,work)
//            .channel(NioServerSocketChannel.class)
//            .childHandler(new ChannelInitializer<NioSocketChannel>() {
//                @Override
//                protected void initChannel(NioSocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                        @Override
//                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            ByteBuf byteBuf = (ByteBuf) msg;
//                        }
//                    });
//                }
//            })
//            ;
//        }catch (Exception e){
//
//        }finally {
//            boss.shutdownGracefully();
//            work.shutdownGracefully();
//        }
    }
}
