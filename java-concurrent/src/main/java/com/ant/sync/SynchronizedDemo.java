package com.ant.sync;

public class SynchronizedDemo {
    public synchronized void method(){
        System.out.println("----");
    }

    public void block(){
        synchronized (this){
            System.out.println("----");
        }
    }
}
