package nettyAdvance.capter05.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import nettyAdvance.capter05.message.LoginRequestMessage;
import nettyAdvance.capter05.protocol.MessageCodec;
import nettyAdvance.capter05.protocol.MessageCodecSharable;

/**
 * 测试
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoggingHandler(),
                //帧解码器
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                new MessageCodecSharable());
        LoginRequestMessage message = new LoginRequestMessage("xcl", "123456");
        //encode
        embeddedChannel.writeOutbound(message);
        //decode
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buffer);
//        embeddedChannel.writeInbound(buffer);
        //模拟解码半包，当没有帧解码器的情况下会报错
        ByteBuf s0 = buffer.slice(0, 100);
        ByteBuf s1 = buffer.slice(100,buffer.readableBytes()-100);
        //引用计数加一
        buffer.retain();
        embeddedChannel.writeInbound(s0);
        embeddedChannel.writeInbound(s1);
    }
}
