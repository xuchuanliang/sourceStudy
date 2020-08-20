package reflect.c1;

import lombok.val;

public class Test {
    public static void main(String[] args) {
        Class<?>[] declaredClasses = Child.class.getDeclaredClasses();
        Class<?> declaringClass = Child.class.getDeclaringClass();
        Class<?>[] classes = Child.class.getClasses();
        System.err.println("");
    }
}
