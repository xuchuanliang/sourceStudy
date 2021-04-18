package netty.capter06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static netty.capter06.ByteBufTest.log;

/**
 * 切片，将一个ByteBuf切片成多个ByteBuf，只是逻辑上的切面，物理内存还是同一块，不会发生数据的拷贝
 */
public class SliceTest {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','i','j','k','l'});
        ByteBuf b1 = buf.slice(0, 8);
        ByteBuf b2 = buf.slice(5, 5);
        log(buf);
        log(b1);
        log(b2);
        b1.setByte(0,'h');
        log(b1);
        log(buf);
    }
}
