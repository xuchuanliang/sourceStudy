package nio.capter02.selectorSocket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static util.ByteBufferUtil.debugAll;

/**
 * 使用多路复用的方式，创建服务端
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //1.创建Selector以及ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        //2.建立selector与ServerSocketChannel的关系（注册）
        //selectionKey就是将来事件发生后，通过selectionKey可以知道事件和哪个channel的事件；一旦channel建立成功，与之关联的selectionKey就是固定的
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, null);
        //事件一共分为四种：
        //accept：特指服务器端，在有连接请求时会触发
        //connection：特指客户端，在建立连接后触发
        //read：有可读事件时
        //write：有可写事件时
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key is {}", selectionKey);
        //死循环不断监听是否有事件发生
        while (true) {
            //3.select 方法，没有事件发生，线程会阻塞；有事件发生，线程才会恢复运行
            //select 在事件未处理时，它不会阻塞；也就是发生了事件要么处理，要么取消，不能置之不理，否则会发生死循环
            selector.select();
            //4.处理事件，selectedKey内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //处理key时，要及时从selectedKeys集合中删除，否则下次处理会出问题
                iterator.remove();
                log.debug("key is {}", key);
                if (key.isAcceptable()) {
                    //如果是建立连接的事件，则建立连接，建立的连接channel监听读事件的发生
                    ServerSocketChannel selectableChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = selectableChannel.accept();
                    socketChannel.configureBlocking(false);
                    //nio中每一个SocketChannel在注册到Selector中时，都可以关联一个附件对象
                    //由于ByteBuffer是不能够给多个Channel使用的，所以我们每一个SocketChannel都关联一个byteBuffer，用于这个channel的读写使用
                    ByteBuffer byteBuffer = ByteBuffer.allocate(8);
                    SelectionKey register = socketChannel.register(selector, 0, byteBuffer);
                    register.interestOps(SelectionKey.OP_READ);
                    log.debug("accept connection,register read event,{}", socketChannel);
                } else if (key.isReadable()) {
                    try {
                        //如果可读，则读取数据
                        //注意：如果客户端异常断开，会触发IO异常，需要及时将这个selectKey取消掉
                        //注意：如果客户端正常断开，也会触发一次read事件，需要及时将这个selectKey取消掉
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        //或者这个channel关联的byteBuffer
                        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        int l = socketChannel.read(byteBuffer);
                        if (l == -1) {
                            //如果客户端正常断开，则服务器端需要及时将这个selectionKey的监听取消，否则selection会认为这个事件一直没有被处理，不断的循环处理这个channel的事件
                            key.cancel();
                            log.debug("客户端正常断开");
                        } else {
                            //切换至读模式
                            split(byteBuffer);
                            //由于可能存在消息较长，而一次无法读完的情况，需要对byteBuffer进行动态扩容，以便能够容纳一整条消息
                            //判断消息是否容得下的判断标准是，在对byteBuffer进行拆分之后，position与limit之间大小
                            //如果在写模式下，position<limit，则说明有空间可以写，如果相等，则说明已经没有空间可以写了，则将byteBuffer进行扩容，并将当前的selectionKey关联到容量更大的附件
                            if(byteBuffer.position() == byteBuffer.limit()){
                                //扩容2倍
                                ByteBuffer newBuffer = ByteBuffer.allocate(byteBuffer.capacity()<<2);
                                //将原来的byteBuffer的内容拷贝到新的缓冲区中
                                //首先需要将原来的byteBuffer由写模式改成读模式
                                byteBuffer.flip();
                                newBuffer.put(byteBuffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        //如果发生异常，则需要及时将这个selectionKey的监听取消，否则selection会认为这个事件一直没有被处理，不断的循环处理这个channel的事件
                        log.debug("客户端异常断开");
                        key.cancel();
                    }
                }
            }
        }
    }

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
