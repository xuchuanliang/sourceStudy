package com.ant.spi;

public class SpiTestImpl1 implements SpiTest{
    @Override
    public void init() {

    }

    public SpiTestImpl1() {
        System.out.println("=============================>初始化SPITestImpl1");
    }
}
