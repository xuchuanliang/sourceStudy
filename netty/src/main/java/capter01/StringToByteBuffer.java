package capter01;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static util.ByteBufferUtil.debugAll;

/**
 * 常用的将字符串转成ByteBuffer的方式
 */
public class StringToByteBuffer {
    public static void main(String[] args) {
        //1.字符串直接转
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.put("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(byteBuffer);

        //2.Charset转
        ByteBuffer byteBuffer1 = Charset.forName("UTF-8").encode("hello");
        debugAll(byteBuffer1);
        ByteBuffer byteBuffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(byteBuffer2);

        //3.wrap
        ByteBuffer byteBuffer3 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(byteBuffer3);

        String s = StandardCharsets.UTF_8.decode(byteBuffer2).toString();
        System.out.println(s);
    }
}
