package capter02.multipartThread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.ByteBufferUtil.debugAll;

/**
 * 模仿netty，多线程版
 * 基于前面的selector模型，发现当只有一个线程处理accept/read/write事件时，存在如下问题
 * 1.当read/write处理耗时比较长的资源时，会导致整个线程的效率
 * 2.在多核cpu的情况下，cpu利用效率不高
 * 所以采用多线程的方式，一个线程以及其对应的selector单独处理accept事件，用来建立连接
 * 多个线程以及其对应的每一个selector专门用来处理read/write事件，将CPU利用起来
 * 整体架构分为boss和work两个角色
 *
 * 注意：当向selector中注册channel与selector.select()获取事件两个方法不是同一个线程执行的时候，如果select()方法阻塞，则会导致注册方法也阻塞
 * 因为这中间有锁控制，所以注册操作的执行应当与select()方法的执行是统一线程执行
 */
@Slf4j
public class MultipartThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        //1.创建一个ServerSocketChannel，设置为非阻塞，绑定8080端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //2.创建一个专门用来监听accept事件的selector
        Selector acceptSelector = Selector.open();
        //3.将channel注册到selector上，监听accept事件
        serverSocketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT, null);
        //4.创建工作线程，处理读写事件，启动工作线程
        Worker worker = new Worker("worker-0");
        worker.start();
        while (true){
            //阻塞监听连接事件
            log.debug("before accept...");
            acceptSelector.select();
            log.debug("after accept...");
            //处理建立连接的所有事件
            Iterator<SelectionKey> iterator = acceptSelector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //将即将要处理的selectionKey从监听到的selectedKeys中移除出去
                iterator.remove();
                //建立连接，获取建立连接的SocketChannel
                SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                //设置为非阻塞
                socketChannel.configureBlocking(false);
                //将socketChannel注册到Worker线程的selector中
                log.debug("before register read event....");
                worker.register(socketChannel,SelectionKey.OP_READ,null);
                log.debug("after register read event....");
            }
        }
    }

    /**
     * 每个worker都是一个线程，考虑可扩展性，继承Runnable，而不是继承Thread
     */
    static class Worker implements Runnable {

        private Thread workThread;//工作线程
        private String name;//线程名称
        private Selector selector;//工作线程处理的对应selector上注册的channel的读写事件
        private volatile boolean started = false;//为了防止重复启动worker，标记当前worker是否已经启动
        private ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();//用来让work线程执行向selector注册channel逻辑的消息队列，在由boss线程向worker线程传输消息

        public Worker(String name) throws IOException {
            this.name = name;
            workThread = new Thread(this,name);
            selector = Selector.open();
        }

        /**
         * 启动work
         */
        public void start(){
            if(!started){
                workThread.start();
            }
        }

        /**
         * 由boss线程提交的向worker线程的中的selector注册channel操作
         * @param sc
         * @param ops
         * @param attach
         */
        public void register(SocketChannel sc,int ops,Object attach){
            tasks.add(()-> {
                try {
                    sc.register(selector,ops,attach);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            //添加完任务后，由于有可能worker线程阻塞在select()方法，需要唤醒
            selector.wakeup();
        }

        @Override
        public void run() {
            //工作线程开始运行
            while (true){
                try {
                    //阻塞直到有事件发生
                    log.debug("before read....");
                    selector.select();
                    //从队列中获取任务并执行
                    Runnable task = tasks.poll();
                    if(null!=task){
                        task.run();
                    }
                    log.debug("after read....");
                    //获取发生的事件：selectedKeys
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //获取一个发生的事件：selectionKey
                        SelectionKey selectionKey = iterator.next();
                        //在即将处理事件前，从已发生的事件中移除要处理的事件
                        iterator.remove();
                        //获取建立的连接channel
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        if(selectionKey.isReadable()){
                            try{
                                //如果可读，则处理读事件
                                ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                                int read = socketChannel.read(byteBuffer);
                                if(read == -1){
                                    //客户端正常关闭了连接，则需要及时将这个selectionKey的监听取消
                                    log.debug("正常断开");
                                    selectionKey.cancel();
                                }else{
                                    //打印读取到的内容
                                    debugAll(byteBuffer);
                                }
                            }catch (Exception e){
                                //如果发生异常，则需要及时将这个selectionKey的监听取消，否则selection会认为这个事件一直没有被处理，不断的循环处理这个channel的事件
                                log.debug("连接异常断开...");
                                selectionKey.cancel();
                            }

                        }else if(selectionKey.isWritable()){
                            //如果可写，则写一各内容
                            socketChannel.write(Charset.defaultCharset().encode("你好"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 开放selector，提供用户向selector中注册channel
         * @return
         */
        public Selector getSelector() {
            return selector;
        }
    }
}
