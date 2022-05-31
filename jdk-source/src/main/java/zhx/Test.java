package zhx;

import cn.hutool.core.util.RandomUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        genLanguage();
//        intern();
//        System.out.println(option(null));
        testLock();
    }

    /**
     * 拼接生成语言
     */
    public static void genLanguage() {
        int minLength = 25;
        List<String> source = Arrays.asList("颜色非常耐看 给人视觉感受有显瘦的效果", "经典不过时的款式", "面料垂坠不易皱",
                "遮肉", "轻盈垂感好", "裙子纯顺，百褶有型，不会散颜色好搭不会显小肚子", "裙子很精致，褶皱也很匀称", "上身太美腻了",
                "衬衫对身材的包容度很好，完全不挑人", "衣服版型很好，面料也超级舒服穿着不透", "裙子简单大方又不失格调", "时尚好看还有这头的超能力",
                "颜色非常耐看 给人视觉感受有显瘦的效果", "大气上档次", "这个价位能买到这么好质量一身搭配 真是值了", "版型很不错 高高瘦瘦的",
                "气质女孩夏天必入款", "量身定作的一样", "走出去回头率很高", "经典不过时的款式", "版型好，做工也不错，穿上很好看也有质感，是大牌的感觉");
        for (int i = 0; i < 50; i++) {
            List<String> dest = new ArrayList<>(source);
            StringBuilder build = new StringBuilder();
            while (build.length() < minLength){
                int index = index(dest);
                build.append(dest.remove(index));
                build.append("，");
            }
//            build.substring(build.length()-1,build.length());
//            build.append("。");
            System.out.println(build.toString());
        }
    }

    private static int index(List<String> dest) {
        return RandomUtil.randomInt(0, dest.size());
    }

    private static void intern(){
        String s = new String("abc");
        String b = new String("abc");
        System.out.println(s == b);
        System.out.println(s.intern() == b.intern());
    }

    private static User option(Object user){
        return (User) Optional.ofNullable(user).orElse(null);
    }

    static class User{
        String id;
    }

    private static void testLock() throws InterruptedException {
        String a = new String("abc");
        String b = new String("abc");
        System.out.println(a==b);
        synchronized (new String(a)){
            System.out.println("a inner");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("a out");
        }
        synchronized (new String(b)){
            System.out.println("b inner");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("b out");
        }
    }
}
