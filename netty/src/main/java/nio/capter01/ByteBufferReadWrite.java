package nio.capter01;

import util.ByteBufferUtil;

import java.nio.ByteBuffer;

public class ByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        ByteBuffer.allocateDirect(16);
        byteBuffer.put((byte) 0x61);//'a'
        ByteBufferUtil.debugAll(byteBuffer);
        byteBuffer.put(new byte[]{0x62,0x63,0x64});
        ByteBufferUtil.debugAll(byteBuffer);
        System.out.println(0x62);
        System.out.println((byte)0x62);
        System.out.println((char)0x62);

    }
}
