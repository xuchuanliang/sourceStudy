package com.ant.capter02;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 弱引用，一般在垃圾回收时就会被清理
 * jvm参数：-Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class Demo2_5 {
    private static int _4mb = 4 * 1024 * 1024;

    public static void main(String[] args) {
//        weakReference();
        weakReferenceQueue();
    }

    private static void weakReference() {
        List<WeakReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            WeakReference<byte[]> weakReference = new WeakReference<>(new byte[_4mb]);
            list.add(weakReference);
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j).get()+",");
            }
            System.out.println();
        }
    }

    private static void weakReferenceQueue(){
        ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();
        List<WeakReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            WeakReference<byte[]> weakReference = new WeakReference<>(new byte[_4mb],referenceQueue);
            list.add(weakReference);
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j).get()+",");
            }
            System.out.println();
        }
        Reference<? extends byte[]> poll = referenceQueue.poll();
        while (poll != null){
            list.remove(poll);
            poll = referenceQueue.poll();
        }
        for (int j = 0; j < list.size(); j++) {
            System.out.print(list.get(j).get()+",");
        }
    }
}
