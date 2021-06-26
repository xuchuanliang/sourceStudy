package com.ant.capter01;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * dump堆内存查看内存占用情况
 */
public class HeapTest3 {
    public static void main(String[] args) throws InterruptedException {
        List<Student> list = new ArrayList<>(100);
        for(int i=0;i<100;i++){
            list.add(new Student());
        }
        System.out.println("end....");
        TimeUnit.MINUTES.sleep(30);
    }
}
