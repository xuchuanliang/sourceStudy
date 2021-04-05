package capter02.selectorSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        socketChannel.write(StandardCharsets.UTF_8.encode("012\n3456789abcdefghijklmn\n"));
        System.in.read();
    }
}
