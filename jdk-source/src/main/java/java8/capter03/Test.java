package java8.capter03;

import cn.hutool.core.lang.func.VoidFunc1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Predicate 用来判断
 * Consumer用来消费
 */
public class Test {
    public static void main(String[] args) {
        map(new ArrayList<>(),(String s)->s.hashCode());
        Consumer<OriginalProcessFile> originalProcessFileConsumer = OriginalProcessFile::process;

    }

    public static void test1(){
        //行为参数化，在以前的java中这是一个匿名类，java8中直接将参数作为一个值传递
        Predicate<String> p = (s)->s.isEmpty();
    }

    public static <T,R> List<R> map(List<T> list, Function<T,R> function){
        List<R> l = new ArrayList<>();
        for(T t:list){
            l.add(function.apply(t));
        }
        return l;
    }



}
