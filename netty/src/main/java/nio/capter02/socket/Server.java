package nio.capter02.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static util.ByteBufferUtil.debugAll;

/**
 * 阻塞模式下的服务器端
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //1.创建一个ByteBuffer，用来缓冲数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        //2.创建一个ServerSocketChannel，服务器端的socket 通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //3.设置监听端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);//将ServerSocketChannel设置为非阻塞模式，默认是阻塞模式
        //4.创建一个集合，用于保存客户端建立的连接通道
        List<SocketChannel> socketChannelList = new ArrayList<>();
        //5.死循环，监听请求
        while (true) {
            //6.监听客户端建立的连接
            SocketChannel accept = serverSocketChannel.accept();//阻塞模式下，会阻塞至有新的连接建立；非阻塞模式下，若无新的连接建立，则返回空
            if(accept != null){
                log.debug("accept success...{}",accept);
                //7.连接建立成功后，将建立的通道添加到集合中
                socketChannelList.add(accept);
                accept.configureBlocking(false);//将SocketChannel设置为非阻塞模式，默认是阻塞模式
            }
            //8.循环所有的通道，接收数据
            for (int i = 0; i < socketChannelList.size(); i++) {
                //9.将通道中的数据读取到缓冲区中
                int read = socketChannelList.get(i).read(byteBuffer);//阻塞模式下，会阻塞至有新的数据能够读取；非阻塞模式下，若无新的数据能够读取，则返回0
                if(0!=read){
                    //10.切换缓冲区模式，将数据处理完成
                    byteBuffer.flip();
                    debugAll(byteBuffer);
                    //11.切换缓冲区模式，继续从通道中读取数据
                    byteBuffer.clear();
                    log.debug("after read....{}",socketChannelList.get(i));
                }
            }
        }

    }
}
