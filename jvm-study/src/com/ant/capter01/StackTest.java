package com.ant.capter01;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认情况下：17476
 * 调整到2m:36080
 * 调整到4m:217077
 *
 */
public class StackTest {
    private static AtomicInteger count = new AtomicInteger();
    public static void main(String[] args) {
        try{
            recursion();
        }catch (Throwable e){
            System.err.println("====================="+count.get()+"=====================");
        }
    }
    public static void recursion(){
        count.incrementAndGet();
        recursion();
    }
}
