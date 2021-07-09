package com.ant.capter02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 演示GC Root
 */
public class Demo2_2 {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        System.out.println(1);
        System.in.read();
        list = null;
        System.out.println(2);
        System.in.read();
    }
}
