package com.deepJVM.capter02;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接内存溢出
 * -XX:MaxDirectMemorySize=10m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class DirectMemoryOOM {
    private static int _1MB = 1024*1024;

    public static void main(String[] args) throws IllegalAccessException {
        final Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        while (true){
            unsafe.allocateMemory(_1MB);
        }
    }
}
