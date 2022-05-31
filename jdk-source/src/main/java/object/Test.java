package object;

public class Test {
    public static void main(String[] args) {
        new B();
    }
}
class A{
    public A(){
        System.out.println("a");
    }
}
class B extends A{
    public B(){
        System.out.println("b");
    }
}
