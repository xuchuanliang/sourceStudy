package nettyAdvance.capter03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

/**
 * 测试LengthFieldDecoder
 */
public class LengthFieldDecoderTest {
    public static void main(String[] args) {
        /*
         LengthFieldBasedFrameDecoder参数说明
         maxFrameLength：表示消息的最大长度，如果超过这个长度还没有正常解析则抛异常
         lengthFieldOffset：表示从第几个字节开始表示消息长度
         lengthFieldLength：表示消息长度占多少字节
         lengthAdjustment：表示长度字节后需要跳过几个字节才是真正的内容
         initialBytesToStrip：表示解析出来的结果需要抛弃多少字节，例如头是4个字节，那么这个参数如果是4，表示前面4个字节会被截取掉，如果是0，则表示消息会原封不动的向下传输
         */
        EmbeddedChannel channel = new EmbeddedChannel(
//                new ChannelInboundHandlerAdapter(){
//                    @Override
//                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                        //判断头是不是我们自己的消息，根据消息头进行判断
//                        ByteBuf byteBuf = (ByteBuf) msg;
//                        if(byteBuf.readableBytes() > 8){
//                            if("CAFEBABY".equals(byteBuf.readBytes(8).toString(CharsetUtil.UTF_8))){
//                                //表示头是0xCAFEBABY，允许接收消息
//                                ctx.fireChannelRead(msg);
//                            }else{
//                                //表示不是我们的消息，则关闭channel
//                                System.out.print("读取到垃圾消息，关闭...");
//                                ctx.channel().close().sync();
//                            }
//                        }
//                    }
//                },
                new LengthFieldBasedFrameDecoder(1024,1,2,2,5),
                new LoggingHandler(LogLevel.DEBUG));

        //消息格式：CAFEBABY length(2字节) version(2字节) content
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        sendSuccess(buf,"hello java");
        sendSuccess(buf,"hello 徐传良");
        sendSuccess(buf,"hello person");
        channel.writeInbound(buf);
    }

    /**
     * 构建正确消息
     * @param buf
     * @param content
     */
    public static void sendSuccess(ByteBuf buf,String content){
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        short length = (short) bytes.length;
        short version = 1;
        buf.writeByte(0XC).writeShort(length).writeShort(version).writeBytes(bytes);
    }

}
