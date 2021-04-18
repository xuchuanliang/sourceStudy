package chat.test;

import chat.message.LoginRequestMessage;
import chat.protocol.MessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 测试消息编解码器
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                /*
                处理半包和粘包问题:
                maxFrameLength:一条消息最大1024个字节；
                lengthFieldOffset和lengthFieldLength:从第12个字节开始的4个字节表示消息体长度；
                lengthAdjustment:表示长度的字节之后紧接着就是消息体，长度与消息体之间间隔0个字节；
                initialBytesToStrip:表示获取完整的消息，需要截取的长度是0
                 */
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                new MessageCodec());
        //1.测试编码
//        embeddedChannel.writeOutbound(new LoginRequestMessage("xuchuanliang","123456"));
        //2.测试解码
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
//        new MessageCodec().encode(null,new LoginRequestMessage("xuchuanliang","123456"),buf);
        //3.先测试将完整消息写进通道
//        embeddedChannel.writeInbound(buf);
        //4.测试分两次模拟半包写入
        ByteBuf buf1 = buf.slice(0, 100);
        ByteBuf buf2 = buf.slice(100, buf.readableBytes() - 100);
        //由于写入时会将buf进行release，所以此处我们手动将buf计数加1，使用完毕后再释放
        buf.retain();
        //当只进行单次发送的情况下，不会打印我们手动打印的日志，因为内容还没读完，还会等待后序内容
        embeddedChannel.writeInbound(buf1);
        embeddedChannel.writeInbound(buf2);
    }
}
