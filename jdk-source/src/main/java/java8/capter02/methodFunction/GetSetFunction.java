package java8.capter02.methodFunction;

/**
 * 模拟get+set方法，有接收参数
 * @param <T>
 */
@FunctionalInterface
public interface GetSetFunction <T,R>{
    R getSet(T t);
}
