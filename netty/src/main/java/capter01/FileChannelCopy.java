package capter01;

import cn.hutool.core.date.StopWatch;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

@Slf4j
public class FileChannelCopy {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("copy big data");
        copy("D:\\studysoft\\CentOS-7-x86_64-DVD-1708.iso","D:\\studysoft\\hahahahahha.iso");
        stopWatch.stop();
        log.error("一共使用的总时长是：{}",stopWatch.getTotalTimeSeconds());
    }

    public static void copy(String fromPath, String toPath) {
        try (FileChannel from = new FileInputStream(fromPath).getChannel();
             FileChannel to = new FileOutputStream(toPath).getChannel()) {
            //由于每次拷贝最多拷贝2g，所以多次拷贝
            //注意，java中int最大值是2的31次方，所以position需要用long，否则会出问题
            long position = 0;
            long size = from.size();
            while (size > 0){
                long transferd = from.transferTo(position, size, to);
                position += transferd;
                size -= transferd;
                log.error("本次一共传输的大小是：{},下一次的position以及size分别是：{}和{}",transferd,position,size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
