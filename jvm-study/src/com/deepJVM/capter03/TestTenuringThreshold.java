package com.deepJVM.capter03;

import com.deepJVM.Constant;

/**
 * 对象年龄增加到15时会被添加到老年代
 * -Xmx20m -Xms20m -Xmn10m -XX:MaxTenuringThreshold=15 -XX:+PrintGCDetails
 * -XX:MaxTenuringThreshold=1 指定对象年龄达到多少才进入老年代
 * -XX:PretenureSizeThreshold=3145728  超过3m直接分配到老年代
 */
public class TestTenuringThreshold {
    public static void main(String[] args) {
        byte[] a1, a2, a3, a4;
        a1 = new byte[Constant._1MB / 4];
        a2 = new byte[4 * Constant._1MB];
        a3 = new byte[4 * Constant._1MB];
        a3 = null;
        a3 = new byte[2 * Constant._1MB];
    }
}
