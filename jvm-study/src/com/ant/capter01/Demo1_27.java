package com.ant.capter01;

import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * 使用Unsafe设置直接内存，释放直接内存，查看效果
 * 垃圾收集器无法管理直接内存，而是通过Unsafe进行管理
 */
public class Demo1_27 {
    private static int _1GB = 1024*1024*1024;
    public static void main(String[] args) {
        ByteBuffer.allocateDirect(_1GB);
        try {
            Unsafe unsafe = getUnsafe();
            //分配1GB直接内存，并获取起始地址
            long baseAddress = unsafe.allocateMemory(_1GB);
            //初始化这1gb内存
            unsafe.setMemory(baseAddress,_1GB, (byte) 0);
            System.in.read();
            //释放
            unsafe.freeMemory(baseAddress);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Unsafe getUnsafe(){
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe o = (Unsafe) theUnsafe.get(null);
            return o;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
