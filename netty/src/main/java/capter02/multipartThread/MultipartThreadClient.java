package capter02.multipartThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 多线程版客户端
 */
public class MultipartThreadClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        socketChannel.write(Charset.defaultCharset().encode("hi"));
        socketChannel.close();
    }
}
