package java8.capter02;

import java.util.ArrayList;
import java.util.List;

public class AppleUtil {
    /**
     * 筛选绿色苹果
     * @param inventory
     * @return
     */
    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for(Apple a:inventory){
            if("green".equals(a.getColor())){
                result.add(a);
            }
        }
        return result;
    }

    /**
     * 筛选不同颜色的苹果
     * @param inventory
     * @return
     */
    public static List<Apple> filterApplesByColor(List<Apple> inventory,String color){
        List<Apple> result = new ArrayList<>();
        for(Apple a:inventory){
            if(color.equals(a.getColor())){
                result.add(a);
            }
        }
        return result;
    }

    /**
     * 筛选重于多少的苹果
     * @param inventory
     * @return
     */
    public static List<Apple> filterApplesByWeight(List<Apple> inventory,int weight){
        List<Apple> result = new ArrayList<>();
        for(Apple a:inventory){
            if(a.getWeight() > weight){
                result.add(a);
            }
        }
        return result;
    }

    /**
     * 使用接口【行为由方法转成值，即行为参数化】
     * @param apples
     * @param p
     * @return
     */
    public static List<Apple> filterApple(List<Apple>apples,ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple a:apples){
            if(p.test(a)){
                result.add(a);
            }
        }
        return result;
    }


}
