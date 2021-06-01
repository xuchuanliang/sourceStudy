package java8.capter02.methodFunction;

/**
 * 模拟get方法：形参是空，有返回结果
 * @param <T>
 */
@FunctionalInterface
public interface GetFunction<T> {
    T get();
}
