package nettyAdvance.capter05.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 协议级别的处理器，主要是为了解决协议的半包以及粘包问题
 * 根据固化的协议，将头的各种解析长度固定下来
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    private static final int MAX_FRAME_LENGTH = 1024;
    private static final int LENGTH_FIELD_OFFSET = 12;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public ProtocolFrameDecoder(){
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }
}
