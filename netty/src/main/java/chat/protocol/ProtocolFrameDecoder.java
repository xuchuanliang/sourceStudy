package chat.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/*
处理半包和粘包问题:
maxFrameLength:一条消息最大1024个字节；
lengthFieldOffset和lengthFieldLength:从第12个字节开始的4个字节表示消息体长度；
lengthAdjustment:表示长度的字节之后紧接着就是消息体，长度与消息体之间间隔0个字节；
initialBytesToStrip:表示获取完整的消息，需要截取的长度是0
*/
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
    //消息最大长度
    private static final int MAX_FRAME_LENGTH=1024;
    //表示长度字节位置的偏移量
    private static final int LENGTH_FIELD_OFFSET = 12;
    //表示长度的字节个数
    private static final int LENGTH_FIELD_LENGTH = 4;
    //长度之后是否需要跳过某些字节才是正文
    private static final int LENGTH_ADJUSTMENT = 0;
    //是否需要截取消息
    private static final int INITIAL_BYTES_TO_STRIP = 0;


    //因为协议一旦确定好，则协议的结构是不会轻易改变的
    public ProtocolFrameDecoder(){
        this(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
