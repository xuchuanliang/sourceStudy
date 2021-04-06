package nio.capter01;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static util.ByteBufferUtil.debugAll;

/**
 * 处理半包和粘包问题
 * 假设 \n为每个包的结尾符号
 * */
public class HalfByteBuffer {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello World\nI am zhangsan\nHo".getBytes(StandardCharsets.UTF_8));
        split(source);
        source.put("w are you\nhahahah\n".getBytes(StandardCharsets.UTF_8));
        split(source);
    }

    /**
     * 模拟获取到的网络包是有粘包和半包情况，手动处理半包和拆包
     * 需要对byteBuffer中的limit、position、capacity有清晰的认识，以及对ByteBuffer中部分api是否有移动limit、position、capacity有认识
     * A buffer's capacity is the number of elements it contains. The capacity of a buffer is never negative and never changes.
     * A buffer's limit is the index of the first element that should not be read or written. A buffer's limit is never negative and is never greater than its capacity.
     * A buffer's position is the index of the next element to be read or written. A buffer's position is never negative and is never greater than its limit.
     * @param byteBuffer
     */
    public static void split(ByteBuffer byteBuffer){
        //先将bytebuffer调整为读模式
        byteBuffer.flip();
        //1.寻找每个完整包的分隔符
        for(int i=0;i<byteBuffer.limit();i++){
            if (byteBuffer.get(i)=='\n') {//注意：get(i)实际上不会移动position的值
                //2.读取到标识，计算本次包的长度
                int length = i+1-byteBuffer.position();
                //开辟一个新的byteBuffer，将本次的包存放在新的byteBuffer中
                ByteBuffer newByteBuffer = ByteBuffer.allocate(length);
                for(int j=0;j<length;j++){
                    newByteBuffer.put(byteBuffer.get());//注意：get()实际上会移动position的值
                }
                //打印出来结果
                debugAll(newByteBuffer);
            }
        }
        //本次读取完毕后，因为半包问题，byteBuffer中仍然可能存在半包的数据，所以使用compact()而不是clear()切换成为写模式，将剩余的未读取的数据移到前面
        byteBuffer.compact();
    }
}
