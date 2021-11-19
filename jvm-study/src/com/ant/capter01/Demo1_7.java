package com.ant.capter01;

/**
 * -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc
 */
public class Demo1_7 {
    public static void main(String[] args) {
        try{
            for(int i=0;i<10000;i++){
//                String.valueOf(i).intern();
            }
        }finally {
            System.out.println(args.length);
        }
    }
}
