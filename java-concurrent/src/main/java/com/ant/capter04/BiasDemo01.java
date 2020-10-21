package com.ant.capter04;

public class BiasDemo01 {
    private static Object lock = new Object();

    public static void main(String[] args) {
        BiasDemo01 biasDemo01 = new BiasDemo01();
        biasDemo01.method1();
    }
    public void method1(){
        synchronized (lock){
            method2();
        }
    }

    public void method2(){
        synchronized (lock){
            method3();
        }
    }

    public void method3(){
        synchronized (lock){
        }
    }
}
