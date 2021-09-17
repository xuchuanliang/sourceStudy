package com.deepJVM.capter03;

import com.deepJVM.Constant;

/**
 * -Xmx20M -Xms20M -Xmn10M
 * old 10m
 * eden 8m
 * s0 1m
 * s1 1m
 */
public class TestAllocation {

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[Constant._1MB * 2];
        allocation2 = new byte[Constant._1MB * 2];
        allocation3 = new byte[Constant._1MB * 2];
        //eden 6M old 0M
        allocation4 = new byte[Constant._1MB * 4];
    }
}
