package nettyAdvance.capter05.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import nettyAdvance.capter05.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 自定义协议解析器
 * 自定义协议要素如下：
 * 魔数：用来在第一时间判定是否是无效数据包
 * 版本号：可以支持协议升级
 * 序列化算法：消息正文到底采用哪种序列化方式和反序列化方式，可以由此扩展，例如：json，protobuf，hession，jdk
 * 指令类型：是登陆、注册、单聊、群聊。。。跟业务相关，也可以是消息类型或消息大小类
 * 请求序号：为了双工通信，提供异步能力
 * 正文长度
 * 消息正文
 */
//todo 2021年5月16日 14:38:15 必须与LengthFieldBasedFrameDecoder一起使用，保证接收到的消息是完整消息，才能保证在多个线程间是可共享的
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    private final static Logger log = LoggerFactory.getLogger(MessageCodecSharable.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        //1.四个字节的魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        //2.一个字节的版本号
        out.writeByte(1);
        //3.一个字节的序列化算法：暂定0表示jdk，1表示json
        out.writeByte(0);
        //4.一个字节的指令类型
        out.writeByte(msg.getMessageType());
        //5.四个字节的请求序号
        out.writeInt(msg.getSequenceId());
        //6.为了凑够2的次幂，增加一个填充位
        out.writeByte(0xff);
        //7.将消息使用jdk序列化方式转成字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] msgBytes = bos.toByteArray();
        //8.四个字节的内容长度
        out.writeInt(msgBytes.length);
        //9.写入正文
        out.writeBytes(msgBytes);
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //1.四个字节的魔数
        int magic = in.readInt();
        //2.一个字节的版本号
        byte version = in.readByte();
        //3.一个字节的序列化算法
        byte serializeType = in.readByte();
        //4.一个字节的指令类型
        byte commandType = in.readByte();
        //5.四个字节的请求序号
        int sequenceId = in.readInt();
        //6.一个字节的填充位
        in.readByte();
        //7.四个字节的消息内容长度
        int contentLength = in.readInt();
        //8.读取正文
        byte[] contentBytes = new byte[contentLength];
        in.readBytes(contentBytes);
        //根据序列化类型，进行反序列化，此处先使用jdk
        ByteArrayInputStream bis = new ByteArrayInputStream(contentBytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Message msg = (Message) ois.readObject();
        out.add(msg);
    }
}
