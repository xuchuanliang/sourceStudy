package com.ant.capter02;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试软连接：在内存不足时会对软连接引用对象进行垃圾回收
 * jvm参数：-Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class Demo2_3 {
    private static int _4mb = 4 * 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
//        noSoftReference();
//        softReference();
        softReferenceQueue();
    }

    private static void noSoftReference() {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new byte[_4mb]);
        }
    }

    public static void softReference() {
        List<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SoftReference<byte[]> softReference = new SoftReference<>(new byte[_4mb]);
            list.add(softReference);
            System.out.println(i);
            System.out.println(softReference.get());
        }
        //内存不足后，查看是否被回收
        for (int i = 0; i < 5; i++) {
            System.out.println(list.get(i).get());
        }
    }

    public static void softReferenceQueue() throws InterruptedException {
        //创建一个软引用队列，创建软引用的时候关联这个队列，那么当软引用对象的内容为空时，那么对应的软引用对象就会被添加到这个队列中
        //这样方便我们及时将空的软引用对象也及时清理掉
        ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();
        List<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SoftReference<byte[]> softReference = new SoftReference<>(new byte[_4mb], referenceQueue);
            list.add(softReference);
            System.out.println(i);
            System.out.println(softReference.get());
        }
        Reference<? extends byte[]> poll = referenceQueue.poll();
        while (poll != null) {
            //只要队列不为空，那么就清理无用的软引用
            System.out.println(poll);
            list.remove(poll);
            poll = referenceQueue.poll();
        }
        System.out.println("=======end=========");
        //内存不足后，查看是否被回收
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).get());
        }
    }
}
