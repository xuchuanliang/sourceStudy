package worktime;

import cn.hutool.core.util.RandomUtil;

/**
 * 随机抽奖
 */
public class Random {
    private static final String[] NAMES = {"何澄钟", "黄立琼", "孙福鑫"};

    public static void main(String[] args) {
        System.out.println(NAMES[RandomUtil.randomInt(1, 300) % 3]);
    }
}
