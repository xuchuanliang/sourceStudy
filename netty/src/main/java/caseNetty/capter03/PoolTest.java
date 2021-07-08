package caseNetty.capter03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * 基于池化和非池化的方式测试性能
 */
public class PoolTest {
    public static void main(String[] args) {
        pooled();

    }

    /**
     * 非池化
     */
    private static void unPooled() {
        long begin = System.currentTimeMillis();
        ByteBuf buf = null;
        int maxTimes = 100000000;
        for (int i = 0; i < maxTimes; i++) {
            buf = Unpooled.buffer(10 * 1024);
            buf.release();
        }
        System.out.println("time is :" + (System.currentTimeMillis() - begin));
    }

    public static void pooled(){
        long begin  =System.currentTimeMillis();
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(false);
        ByteBuf buf = null;
        int maxTimes = 100000000;
        for (int i = 0; i < maxTimes; i++) {
            buf = allocator.heapBuffer(10 * 1024);
            buf.release();
        }
        System.out.println("time is :" + (System.currentTimeMillis() - begin));
    }
}
