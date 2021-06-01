package java8.capter03;

import cn.hutool.core.lang.func.VoidFunc1;
import java8.capter02.Apple;

import java.util.ArrayList;
import java.util.Comparator;
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
    private static List<Apple> apples = new ArrayList<>();
    public static void main(String[] args) {
//        map(new ArrayList<>(),(String s)->s.hashCode());
//        Consumer<OriginalProcessFile> originalProcessFileConsumer = OriginalProcessFile::process;
//        List<String> l = new ArrayList<>();
    }

    public static void test1(){
        Consumer<OriginalProcessFile> consumer = OriginalProcessFile::process;
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

    public static void test2(){
        //1.匿名内部类
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        //2.将匿名内部类转成lambda表达式
        apples.sort(((o1, o2) -> o1.getWeight().compareTo(o2.getWeight())));

        //3.使用Comparator的静态方法产生一个Comparator对象
        Comparator<Apple> comparing = Comparator.comparing((Apple a) -> a.getWeight());
        apples.sort(comparing);

        //4.将上方合并成为一行
        apples.sort(Comparator.comparing((Apple a) -> a.getWeight()));

        //5.(Apple a) -> a.getWeight()可以直接使用方法引用的方式写
        apples.sort(Comparator.comparing(Apple::getWeight));
    }




}
