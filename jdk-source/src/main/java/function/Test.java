package function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Test {
    public static void main(String[] args) {
        filter(new ArrayList<String>(),s -> s.isEmpty());
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> t){
        List<T> result = new ArrayList<>();
        for(T s:list){
            if(t.test(s)){
                result.add(s);
            }
        }
        return result;
    }
}
