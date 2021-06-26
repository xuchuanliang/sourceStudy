package com.test1.ant.capter04;

/**
 * 使用synchronized 面向对象方式实现自增和自减线程安全
 */
public class Room {
    private int count = 0;

    public void increment(){
        synchronized (this){
            count++;
        }
    }

    public void decrement(){
        synchronized (this){
            count--;
        }
    }

    public int get(){
        synchronized (this){
            return count;
        }
    }
}
