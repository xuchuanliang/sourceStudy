package com.deepJVM.capter02;

import java.util.HashSet;
import java.util.Set;

/**
 * java7之后常量池在堆内存，通过设置堆内存大小限制
 * -Xmx20m -Xms20m
 */
public class MetadataOOM {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        short i = 0;
        while (true){
            set.add(String.valueOf(i++).intern());
        }
    }
}
