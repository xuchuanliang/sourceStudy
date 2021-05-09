package chat.protocol;

import chat.message.Message;
import chat.serialized.SerializeType;
import chat.serialized.SerializedHelp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Message 的编解码器,codec表示同时具有编码和解码功能
 * 自定义协议一般包括如下内容：
 * 1.魔数，用来在第一时间判定是否是无效数据包
 * 2.版本号，可以支持协议的升级
 * 3.序列化算法，消息正文到底采用哪种序列化反序列化方式，可以由此扩展，例如：json,protobuf,hessian,jdk
 * 4.指令类型，是登陆、注册、单聊、群聊...跟业务相关
 * 5.请求序号，为了双工通信，提供异步能力
 * 6.正文长度
 * 7.消息正文
 * <p>
 * 注意：必须和LengthFieldBasedFrameDecoder配合使用，确保接收到的ByteBuf是完成的，不需要自行维护状态，保证@Shareable的线程安全性
 */
public class MessageCodec extends ByteToMessageCodec<Message> {
    private static final Logger log = LoggerFactory.getLogger(MessageCodec.class);

    /**
     * 按照协议对message进行编码
     * ByteToMessageCodec的encode和decode方法不需要手动调用ctx向下写或者读，会被自动调到
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //1.魔数 4个字节 默认值是0xABCD
        out.writeBytes(new byte[]{0xA, 0xB, 0xC, 0xD});
        //2.版本号，可以用来支持协议升级，以及不同类型的协议,1个字节
        out.writeByte(1);
        //3.序列化算法，用来对正文进行序列化和反序列化，1个字节,此处使用jdk序列化
        out.writeByte(SerializeType.JDK);
        //4.指令类型，表示消息类型，业务相关，1个字节
        out.writeByte(msg.getMessageType());
        //5.请求序号，用来双工通信，提供异步能力，4个字节
        out.writeInt(msg.getSequenceId());
        //内容以前一共15个字节，不够2的整数倍，增加填充位，凑够2的整数倍 16个字节
        out.writeByte(0xff);
        //6.使用jdk序列化方式获取正文
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(msg);
        byte[] contents = byteArrayOutputStream.toByteArray();
        //7.写入内容长度,4个字节
        out.writeInt(contents.length);
        //8.写入正文
        out.writeBytes(contents);
    }

    /**
     * 按照协议对ByteBuf解码成Message
     * ByteToMessageCodec的encode和decode方法不需要手动调用ctx向下写或者读，会被自动调到
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //1.魔数 4个字节
        int magicNum = in.readInt();
        //2.版本号 1个字节
        byte version = in.readByte();
        //3.序列化算法 1个字节
        byte serializedType = in.readByte();
        //4.指令类型，1个字节，业务相关
        int messageType = in.readByte();
        //5.请求序号，4个字节
        int sequenceId = in.readInt();
        //对其填充
        in.readByte();
        //6.获取消息体长度,4个字节
        int contentLength = in.readInt();
        //7.写入正文
        byte[] content = new byte[contentLength];
        in.readBytes(content, 0, contentLength);
        //8.根据序列化类型进行反序列化
        Message message = SerializedHelp.deserialized(serializedType, content);
        log.debug("magicNum:{},version:{},serializedType:{},messageType:{},sequenceId:{}", magicNum, version, serializedType, messageType, sequenceId);
        log.debug("message is :{}", message);
    }
}
