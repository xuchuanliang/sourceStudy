package com.ant.capter02;

import com.ant.MemoryConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * JVM参数：
 * -Xms20m -Xmx20m -Xmn10m -XX:+UseSerialGC -XX:+PrintGCDetails -verbose:gc
 */
public class Demo2_6 {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        list.add(new byte[MemoryConstant._8MB]);
        list.add(new byte[MemoryConstant._8MB]);


    }
}
