package com.test1.ant.capter01;

/**
 * 方法的局部变量是否是线程安全的
 */
public class Demo2 {
    public static void main(String[] args) {

    }

    /**
     * 线程安全，引用没有曝露到方法以外的地方
     */
    public static void method1(){
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        sb.append(2);
        System.out.println(sb.toString());
    }

    /**
     * 线程不安全，sb是通过外部传入，有可能被其他线程使用
     * 产生了局部变量逃离
     */
    public static void method2(StringBuilder sb){
        sb.append(1);
        sb.append(2);
        System.out.println(sb.toString());
    }

    /**
     * 线程不安全，因为sb的引用通过返回值暴露到外面，有可能被其他线程使用
     * 产生了局部变量逃离
     * @return
     */
    public static StringBuilder method3(){
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        sb.append(2);
        return sb;
    }
}
