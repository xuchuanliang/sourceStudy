package com.ant.capter01;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * -Xmx10m -XX:-UseGCOverheadLimit
 */
public class StringTableOOM {
    public static void main(String[] args) {
        int n = 0;
        try{
            List<String> l = new ArrayList<>();
            for(int i=0;i<200000;i++){
                //构造一个字符串，并且放入StringTable
                l.add(UUID.randomUUID().toString().intern());
                n++;
            }
        }finally {
            System.out.println("n is :"+n);
        }

    }
}
