package capter01;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用channel以及Bytebuffer方式从文件中读取数据案例
 * channel表示管道，ByteBuffer是内存中开辟的用来存放临时数据的内存区域
 *
 * ByteBuffer正确使用姿势：
 * 1.向buffer写入数据，例如调用channel.read(buffer)
 * 2.调用flip()切换至读模式
 * 3.从buffer读取数据，例如调用buffer.get()
 * 4.调用clear()或compact()切换至写模式
 * 5.重复1~4步骤
 */
@Slf4j
public class ByteBufferDemo {
    public static void main(String[] args) {
        //1.读取文件，对应的是FileChannel
        //获取FileChannel方式：输入输出流；RandomAccessFile
        try(FileChannel fileChannel = new FileInputStream("D:\\code\\code_idea\\sourceStudy\\netty\\data.txt").getChannel()){
            //准备缓冲区,开辟了10个字节的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            //从channel通道中读取数据，读到内存中开辟的缓冲区中
            int read;
            while ((read = fileChannel.read(byteBuffer))!=-1){
                log.debug("读取到的字节数：{}",read);
                //打印buffer的内容
                byteBuffer.flip();//切换至读模式
                while (byteBuffer.hasRemaining()){//是否还有剩余未读数据
                    byte b = byteBuffer.get();//从bytebuffer中获取1个字节
                    log.debug("实际字节是：{}",(char)b);
                }
                byteBuffer.clear();//由读模式切换到写模式
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
