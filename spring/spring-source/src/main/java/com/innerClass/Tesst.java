package com.innerClass;

public class Tesst {
    private int p;
    class In{
        private int A;
    }

    public static void main(String[] args) {
        Class<?>[] classes = Tesst.class.getDeclaredClasses();
        System.out.println(classes.length);
    }
}
