package nio.capter02.writeSocket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 写入事件的客户端
 */
@Slf4j
public class WriteClient {
    public static void main(String[] args) throws IOException {
        //1.创建一个SokectChannel
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        //2.不断的读取数据
        long count = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024*124);
        while (-1!=socketChannel.read(buffer)){
            //只要没有关闭，则持续读
            //转成可写模式
            count += buffer.position();
            log.debug("一共读取：{}",count);
            buffer.clear();
        }
    }
}
