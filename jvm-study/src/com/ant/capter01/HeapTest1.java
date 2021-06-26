package com.ant.capter01;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟堆内存溢出:java.lang.OutOfMemoryError: Java heap space
 * -Xmx10m，设置堆内存大小
 */
public class HeapTest1 {
    public static void main(String[] args) {
        int count = 1;
        try{
            List<String> l = new ArrayList<>();
            String s = "hello";
            for(;;){
                l.add(s);
                s = s+s;
                count++;
            }
        }catch (Throwable t){
            t.printStackTrace();
            System.out.println("=============="+count+"================");
        }
    }
}
