package nettyAdvance.capter05.protocol;

import chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
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
//该类不能被共享，因为继承了ByteToMessageCodec，这个父类的主要作用是将buteBuf转成指定消息，所以默认为需要缓冲buteBuf直到获取到完整的消息
//但是此处我们针对实际业务逻辑直到，我们的消息缓冲已经被LengthFieldBasedFrameDecoder完成，这里获取到的消息就是一个完整的消息，所以我们此处可以更换实现，
//转成可被共享的MessageToMessageCodec父类，这样就不用每一个连接创建一个新的消息编解码对象
@Deprecated //MessageCodecSharable
public class MessageCodec extends ByteToMessageCodec<Message> {
    private static Logger log = LoggerFactory.getLogger(MessageCodec.class);
    /**
     * 编码：将msg按照自定义协议格式编码称为字节数组
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
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
    }

    /**
     * 解码：将byteBuf按照私有协议解码成为Message
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
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
        log.debug("{},{},{},{},{},{}",magic,version,serializeType,commandType,sequenceId,contentLength);
        log.debug("{}",msg);
        out.add(msg);
    }
}
