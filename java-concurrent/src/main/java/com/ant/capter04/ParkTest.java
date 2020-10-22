package com.ant.capter04;

import java.util.concurrent.locks.LockSupport;

public class ParkTest {
    public static void main(String[] args) {
        LockSupport.park();
    }
}
