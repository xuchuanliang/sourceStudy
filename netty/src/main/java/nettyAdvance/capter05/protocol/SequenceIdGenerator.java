package nettyAdvance.capter05.protocol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成唯一id
 */
public abstract class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();
    public static int nextId(){
        return id.getAndIncrement();
    }
}
