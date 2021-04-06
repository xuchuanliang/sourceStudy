package nio.capter02.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 阻塞模式的客户端
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        //1.创建客户端链接的channel
        SocketChannel socketChannel = SocketChannel.open();
        //2.设置要连接的服务器，并开始连接
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
        //3.发送数据
        socketChannel.write(StandardCharsets.UTF_8.encode("hello："));
        log.debug("发送数据成功");
        socketChannel.close();
    }
}
