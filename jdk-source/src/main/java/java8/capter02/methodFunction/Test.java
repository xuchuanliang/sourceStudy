package java8.capter02.methodFunction;

import java.util.function.Function;

public class Test<T> {
    public static void main(String[] args) {
        new Test<>().t();
    }

    public void t(){
        test(new Function<User, Object>() {
            @Override
            public Object apply(User user) {
                return user.getId();
            }
        });

        test(user->user.getId());
        test(User::getId);
    }

    public void test(Function<User,Object> f){

        System.out.println();
    }
}
