package thread.capter04;

/**
 * 使用javap命令查看该类生成的class对应的字节码信息，可以看到
 * 实际上i++在jvm中执行是分为4步执行：
 *  getstatic：读取静态变量的值
 *  iconst_1：准备常量1
 *  iadd：自增
 *  putstatic：将修改后的值存回到静态变量中
 *
 *  在jmm中，执行自增或者自减操作实际上都需要java线程工作内存中的数据域主内存中的数据进行交换
 *  所以存在线程安全问题
 *
 */
public class Test2 {
    static int i = 1;
    public static void main(String[] args) {
        i++;
    }
}
