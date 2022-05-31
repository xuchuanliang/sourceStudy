package com.ant.assignable;

public class Test {
    public static void main(String[] args) {
        System.out.println(A.class.isAssignableFrom(B.class));
        System.out.println(A.class.isAssignableFrom(C.class));
        System.out.println(B.class.isAssignableFrom(C.class));
        System.out.println(B.class.isAssignableFrom(A.class));
    }
}
interface A{

}
class B implements A{

}
class C extends B{}
