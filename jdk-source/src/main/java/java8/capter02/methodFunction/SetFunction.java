package java8.capter02.methodFunction;

/**
 * 模拟set方法
 * @param <T>
 */
@FunctionalInterface
public interface SetFunction<T> {
    void set(T t);
}
