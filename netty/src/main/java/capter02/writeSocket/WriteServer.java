package capter02.writeSocket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 写事件的服务器端
 */
@Slf4j
public class WriteServer {
    public static void main(String[] args) throws IOException {
        //1.创建ServerSocketChannel,监听8080端口，设置为非阻塞模式
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //2.创建Selector，关联serverSocketChannel，并且监听建立连接事件
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //3.循环轮询事件
        while (true) {
            //阻塞监听是否有事件发生，如果没有事件当前线程阻塞，如果有事件，当前线程向下继续执行
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //获取发生的事件对应的selectionKey
                SelectionKey selectionKey = iterator.next();
                //及时从监听事件的selectedKey集合中移除掉当前处理中的selectionKey
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    //如果是接收连接事件，则说明肯定是ServerSocketChannel
                    SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                    //获取到的SocketChannel设置成非阻塞，注册到selector，关注可读和可写事件
                    socketChannel.configureBlocking(false);
                    SelectionKey socketSelectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    //模拟大量数据向客户端写入，但是操作系统写缓冲区满了之后，则需要等待当前写缓冲区的数据写出去后才能继续写入，则需要监听可写事件，才继续写入
                    //因为缓冲区满了，写的数据为0，会导致当前线程做无用功，不如等到可写的时候再写
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < 300000000; i++) {
                        stringBuilder.append("a");
                    }
                    ByteBuffer byteBuffer = Charset.defaultCharset().encode(stringBuilder.toString());
                    int write = socketChannel.write(byteBuffer);
                    log.debug("写出的数据量是：{}", write);
                    //如果一次没有写完，则等待下次可写时，继续写入，则需要监听写事件
                    if (byteBuffer.hasRemaining()) {
                        //没有写完，则监听写事件，将未写完的数据关联到当前selectionKey的附件中
                        socketSelectionKey.interestOps(socketSelectionKey.interestOps() + SelectionKey.OP_WRITE);
                        socketSelectionKey.attach(byteBuffer);
                    }
                } else if (selectionKey.isWritable()) {
                    //当时可写时，则再进行一次写入
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    log.debug("写出的数据量是,{}",((SocketChannel)selectionKey.channel()).write(buffer));
                    if(!buffer.hasRemaining()){
                        //如果已经写完了，那么即使取消监听的写事件，并且为了内存考虑，将当前selectionKey关联的附件对象删除
                        selectionKey.interestOps(selectionKey.interestOps()-SelectionKey.OP_WRITE);
                        selectionKey.attach(null);
                    }
                }
            }
        }
    }
}
