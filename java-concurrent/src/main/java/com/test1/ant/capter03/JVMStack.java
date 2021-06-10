package com.test1.ant.capter03;

/**
 * java虚拟机栈
 */
public class JVMStack {
    public static void main(String[] args) {
        test1();
    }
    public static void test1(){
        test2();
    }
    public static void test2(){
        test3();
    }
    public static void test3(){
        System.out.println("test3");
    }
}
