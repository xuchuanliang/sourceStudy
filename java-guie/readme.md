# java Guide面经笔记，旨在记录面试相关内容

- 关于finally语句块：
>1.当try、catch、finally中均有return语句时，则finally中的return结果会覆盖try语句和catch语句中的return语句；如果finally语句中无
>return语句，则catch中的return结果会覆盖try中的返回结果

- java对象中若有字段不想被序列化和反序列化，则可以使用transient修饰，transient只能修饰属性，不能修饰类和方法。

-  关于I/O流：
>1.按照流的方向分为输入流和输出流；2.按照操作单元分为字节流和字符流；3.按照流的角色分为节点流和处理流；<br>
>java中的I/O流都是由以下四个基类派生出来：<br>
>>InputStream/Reader：所有输入流的基类，前者是字节输入流，后者是字符输入流<br>
>>OutputStream/Writer：所有输出流的基类，前者是字节输入流，后者是字符输出流
>2.既然有了字节流，为什么还需要字符流？<br>
>>字符流是由 Java 虚拟机将字节转换得到的，问题就出在这个过程还算是⾮常耗时，并且，如果我们不知道编码类型就很容易出现乱码问题。
>>所以， I/O 流就⼲脆提供了⼀个直接操作字符的接⼝，⽅便我们平时对字符进⾏流操作。如果⾳频⽂件、图⽚等媒体⽂件⽤字节流⽐较好，
>>如果涉及到字符的话使⽤字符流⽐较好。

- 关于BIO、NIO、AIO
>BIO：同步阻塞I/O，数据的读取写⼊必须阻塞在⼀个线程内等待其完成。在活动连接数不是特别⾼（⼩于单机 1000）的情况下，这种模型是⽐较不错的，
>可以让每⼀个连接专注于⾃⼰的 I/O 并且编程模型简单，也不⽤过多考虑系统的过载、限流等问题。<br>
>NIO：同步非阻塞I/0，它⽀持⾯向缓冲的，基于通道的 I/O 操作⽅法。<br>
>AIO：异步非阻塞I/O

- 静态内部类与内部类
>静态内部类与非静态内部类之间存在一个最大的区别: 非静态内部类在编译完成之后会隐含地保存着一个引用，该引用是指向创建它的外围类，<br>
>但是静态内部类却没有。没有这个引用就意味着：1. 它的创建是不需要依赖外围类的创建。2. 它不能使用任何外围类的非static成员变量和方法。

- 静态代码块
>静态代码块定义在类中方法外, 静态代码块在非静态代码块之前执行(静态代码块—>非静态代码块—>构造方法)。 该类不管创建多少对象，静态代码块只执行一次.

- 深拷贝和浅拷贝
>浅拷贝：对基本数据类型进行值传递，对引用数据类型进行引用传递。<br>
>深拷贝：对基本数据类型进行值传递，对引用数据类型创建一个新的对象，并复制其内容。<br>
```java
//浅拷贝示例代码
public class Friend {
    private String name;
    private String gender;


    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
public class Entity implements Cloneable{
    private String name;
    private String gender;
    private Friend friend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", friend=" + friend +
                '}';
    }

    /**
     * 浅拷贝
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Entity clone() throws CloneNotSupportedException {
        return (Entity) super.clone();
    }
}
//测试类
public class Test{
    public static void cloneTest() throws CloneNotSupportedException {
        Friend girlFriend = new Friend();
        girlFriend.setGender("女");
        girlFriend.setName("小红");
        Entity entity = new Entity();
        entity.setGender("男");
        entity.setName("小明");
        entity.setFriend(girlFriend);
        Entity clone = entity.clone();
        //此处浅拷贝，修改后，clone对象中的friend对象也会被影响
        girlFriend.setName("小芬");
        girlFriend.setGender("人妖");
        System.out.println(clone);
    }
}
```
----
```java
//深拷贝
public class Friend implements Cloneable{
    private String name;
    private String gender;


    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    protected Friend clone() throws CloneNotSupportedException {
        return (Friend) super.clone();
    }
}
public class Entity implements Cloneable{
    private String name;
    private String gender;
    private Friend friend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", friend=" + friend +
                '}';
    }

    /**
     * 浅拷贝
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Entity clone() throws CloneNotSupportedException {
        Entity entity = (Entity) super.clone();
        entity.setFriend(friend.clone());
        return entity;
    }
}
public class Test{
    public static void cloneTest() throws CloneNotSupportedException {
        Friend girlFriend = new Friend();
        girlFriend.setGender("女");
        girlFriend.setName("小红");
        Entity entity = new Entity();
        entity.setGender("男");
        entity.setName("小明");
        entity.setFriend(girlFriend);
        Entity clone = entity.clone();
        //此处是深拷贝，修改girlFriend后，clone对象不会被影响
        girlFriend.setName("小芬");
        girlFriend.setGender("人妖");
        System.out.println(clone);
    }
}
```

- List，Set，Map三者的区别
>List：底层是数组，有序，允许重复的集合；<br>
>Set：底层是Hash，无序，不允许重复的集合；<br>
>Map：底层是Hash，使用键值对存储，key不允许重复，value允许重复；<br>

- LinkedList与ArrayList的区别
>1.**线程安全方面**：都是不同步的，线程不安全的；<br>
>2.**底层数据结构**：ArrayList底层使用Object数组存放元素，LinkedList底层使用双向链表数据结构。<br>
>3.**插入和删除是否受元素位置的影响**：根据底层数据结构我们推断出ArrayList查询快(O(1))，但是由于增删改都需要整体移动后面元素的位置，比较慢(O(n))；
>LinkedList查询由于需要从头部一个个向下查找，所以查询慢(O(n))，但是增删改只需要修改前后元素的索引，所以增删改快(O(1))；<br>
>4.是否支持随机访问：LinkedList 不⽀持⾼效的随机元素访问，⽽ ArrayList ⽀持。快速随机访问就是通过元素的序号快速获取元素对象(对应于 get(int index) ⽅法)。
>5.内存空间占用情况：ArrayList的空间浪费主要体现在List列表的结尾会预留一定的容量空间，而LinkedList的空间花费在每个元素都需要存储前后元素的索引。

- RandomAccess接口：是一个标识接口，与Serializable接口类似，标识该集合是否支持随机访问。

- 链表、双向链表和双向循环链表
>1.链表：集合中的每一个元素都存储下一个元素的指针，与数组相对应，链表的优点是增删改比较快，查询慢，不支持随机访问；<br>
>2.双向链表：集合中的每个元素都存储着上一个元素和下一个元素的指针，即每一个元素都有两个指针；缺点是当需要访问后半段元素时，需要耗费的时间比较长，因为要从一个元素逐个向后查找。    
>3.双向循环链表：在双向链表的基础上，将第一个元素的pre指针指向了最后一个元素，最后一个元素的next指向的第一个元素，这样的好处在于当需要访问后面的元素时，可以逆向查找，需要遍历的元素大大减少。

- ArrayList与Vector
>ArrayList与Vector的关系与StringBuilder和StringBuffer的关系非常类似，Vector与StringBuffer都是通过在方法前面加上同步互斥锁synchronized来实现同步，但是这样导致锁的竞争比较激烈，性能比较低。

- ArrayList的扩容机制--源码跟进分析
>ArrayList arrayList = new ArrayList();
>   -->this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
```java
public class Test{
    public void test(){
/*
    1：ArrayList arrayList = new ArrayList();
    2：
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
*/
        ArrayList arrayList = new ArrayList();
    }
}
```

2020年6月20日 17:34:41 51/317


