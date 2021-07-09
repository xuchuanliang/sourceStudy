package com.ant.capter01;

/**
 *
 */
public class Demo1_22 {
    /*
      java的Class字节码文件包含三部分内容：类的基本信息、常量池、类方法定义；
      当程序运行时，JVM会将Class字节码文件中的常量池加载到内存中，放在名称为运行时常量池的一块内存中，
      此时像反编译代码中常量池中的a、b、ab等，都还只是常量池中的符号，还没有编程java中的字符串对象；
      当方法从上向下执行时，才会将符号变成字符串对象，
      例如执行到 0: ldc           #2这行代码时，将a符号变为"a"字符串对象，然后会像StringTable中查询是否有该字符串，
      如果没有就将该字符串添加到StringTable中，StringTable的数据结构本质上是一个哈希表
      执行到3: ldc           #3 这行代码，会初始化"b"字符串，并且将b字符串对象放入StringTable
      执行到6: ldc           #4 这行代码，会初始化"ab"字符串，并且将ab字符串对象放入StringTable


     * @param args
     */
    public static void main(String[] args) {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        String s4 = (s1 + s2).intern();
        String s5 = "a" + "b";
        System.out.println(s3 == s4);
    }
}
