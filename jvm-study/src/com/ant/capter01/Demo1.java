package com.test1.ant.capter01;

public class Demo1 {
    public static void main(String[] args) {
        byte[] bs = new byte[500];
        for(int i=0;i<500;i++){
            bs[i] = 1;
        }
        method1();
        System.out.print(1);
    }
    private static void method1(){
        byte[] bs = new byte[500];
        for(int i=0;i<500;i++){
            bs[i] = 1;
        }
        method2(1,2);
    }

    private static int method2(int a,int b){
        byte[] bs = new byte[500];
        for(int i=0;i<500;i++){
            bs[i] = 1;
        }
        int c = a + b;
        return c;
    }
}
