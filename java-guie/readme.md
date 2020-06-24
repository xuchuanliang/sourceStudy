# java Guide面经笔记，旨在记录面试相关内容

- 关于finally语句块：
>1.当try、catch、finally中均有return语句时，则finally中的return结果会覆盖try语句和catch语句中的return语句；如果finally语句中无
>2.return语句，则catch中的return结果会覆盖try中的返回结果

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
>ArrayList与Vector的关系与StringBuilder和StringBuffer的关系非常类似，Vector与StringBuffer都是通过在方法前面加上同步互斥锁synchronized来实现同步，但是这样导致锁的竞争比较激烈，性能比较低，Vector已经基本被淘汰。

- ArrayList的扩容机制--源码跟进分析
> 通过跟进源码我们可知：
>1.ArrayList底层存储元素是通过一个Object类型的数组存储：transient Object[] elementData;
>2.ArrayList初始化时，默认容量是10
>3.ArrayList每次扩容都是原数组长度的1.5倍：int newCapacity = oldCapacity + (oldCapacity >> 1);
>4.**当我们需要一次向ArrayList中增加大量元素时，使用ensureCapacity()方法预先对存储元素的数组(elementData)所需要使用的内存空间进行开辟，
>防止在添加元素的过程中不断内部调用grow()方法，对elementData进行1.5倍的循环扩容以及数组拷贝，效率提升明显**
```java
public class Test{
    /**
     * 测试ArrayList的ensureCapacity()方法
     * 该方法是在我们需要向ArrayList中add大量元素时，为了避免出现List内存对数组不断的进行扩容而导致内存和CPU资源的浪费
     * 使用该方法可以一次性在内存中开辟这么大空间的数组，节省大量的时间和系统资源
     * 下方增加1千万个元素，时间差距一倍：
     * 需要不断开辟空间，所使用的时间：2725
     * 一次性预先开辟好，所使用的时间：1135
     */
    public static void ensureCapacityTest(){
        List<Object> list = new ArrayList<>();
        //增加一千万条数据，在没有提前调用ensureCapacity方法之前，ArrayList要频繁的开辟空间，进行数组的拷贝工作，耗费比较多的CPU和内存资源，耗费时间
        int size = 10000000;
        long start = System.currentTimeMillis();
        for(int i=0;i<size;i++){
            list.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        list.clear();
        start = System.currentTimeMillis();
        ((ArrayList)list).ensureCapacity(size);
        for(int i=0;i< size;i++){
            list.add(i);
        }
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
```

- HashMap和Hashtable的区别
>1.HashMap与Hashtable的关系与ArrayList与Vector的关系类似，Hashtable是在内部的方法调用上都增加了互斥同步锁synchronized来实现同步，这样到导致性能较低，Hashtable与Vector一样都已经被淘汰。
>2.初始容量⼤⼩和每次扩充容量⼤⼩的不同 ： ①创建时如果不指定容量初始值， Hashtable 默认的初始⼤⼩为11，之后每次扩充，容量变为原来的2n+1。 HashMap 默认的初始化⼤⼩为16。之后
   每次扩充，容量变为原来的2倍。 ②创建时如果给定了容量初始值，那么 Hashtable 会直接使⽤你给定的⼤⼩，⽽ HashMap 会将其扩充为2的幂次⽅⼤⼩（HashMap 中的 tableSizeFor() ⽅
   法保证，下⾯给出了源代码）。也就是说 HashMap 总是使⽤2的幂作为哈希表的⼤⼩,后⾯会介绍到为什么是2的幂次⽅。
>3.底层数据结构： JDK1.8 以后的 HashMap 在解决哈希冲突时有了较⼤的变化，当链表⻓度⼤于阈值（默认为8）时，将链表转化为红⿊树，以减少搜索时间.Hashtable 没有这样的机制。

- HashMap与HashSet的区别：HashSet底层就是使用一个HashMap存储，基本上所有的方法都是直接调用HashMap的方法，看源码得知。

- HashSet如何检查重复：
>当你把对象加⼊ HashSet 时， HashSet会先计算对象的 hashcode 值来判断对象加⼊的位置，同时也会与其他加⼊的对象的hashcode值作⽐较，如果没有相符的hashcode， HashSet会假设对象没有重复出
现。但是如果发现有相同hashcode值的对象，这时会调⽤ equals（） ⽅法来检查hashcode相等的对象是否真的相同。如果两者相同， HashSet就不会让加⼊操作成功。
>hashCode()与equals()的相关规定：
>>1.如果两个对象相等，则hashcode⼀定也是相同的
>>2.两个对象相等,对两个equals⽅法返回true
>>3.两个对象有相同的hashcode值，它们也不⼀定是相等的
>>4.综上， equals⽅法被覆盖过，则hashCode⽅法也必须被覆盖
>>5.综上， equals⽅法被覆盖过，则hashCode⽅法也必须被覆盖
>>6.hashCode()的默认⾏为是对堆上的对象产⽣独特值。如果没有重写hashCode()，则该class的两个对象⽆论如何都不会相等（即使这两个对象指向相同的数据）。
>>==与equals的区别
>>1.==是判断两个变量或实例是不是指向同⼀个内存空间 equals是判断两个变量或实例所指向的内存空间的值是不是相同
>>2.--是指对内存地址进⾏⽐较 equals()是对字符串的内容进⾏⽐较
>>3.==指引⽤是否相同 equals()指的是值是否相同

- jdk1.7与jdk1.8的HashMap，参考：[重新认识HashMap](https://zhuanlan.zhihu.com/p/21673805)
>JDK1.8 之前 HashMap 底层是 数组和链表 结合在⼀起使⽤也就是链表散列。 HashMap 通过 key 的hashCode 经过扰动函数处理过后得到 hash 值，然后通过 (n - 1) & hash 判断当前元素存放的位置 （这⾥的 n 指的是数组的⻓度），如果当前位置存在元素的话，就判断该元素与要存⼊的元素的 hash值以及 key 是否相同，如果相同的话，直接覆盖，不相同就通过拉链法解决冲突。
>所谓扰动函数指的就是 HashMap 的 hash ⽅法。使⽤ hash ⽅法也就是扰动函数是为了防⽌⼀些实现⽐较差的 hashCode() ⽅法 换句话说使⽤扰动函数之后可以减少碰撞。

- jdk1.8以后的HashMap
>jdk1.8之后HashMap的底层数据结构改成了数组+链表+红黑树的结构解决。当链表长度大于8（默认）时，链表调整成红黑树。之所以使用红黑树而不是使用二叉树，因为二叉树在某些情况下会退化成为一个链表。

- ConcurrentHashMap与Hashtable的区别？

>底层数据结构：JDK1.7的 ConcurrentHashMap 底层采⽤ 分段的数组+链表 实现， JDK1.8 采⽤的数据结构跟HashMap1.8的结构⼀样，数组+链表/红⿊⼆叉树。 Hashtable 和 JDK1.8 之前的HashMap 的底层数据结构类似都是采⽤ 数组+链表 的形式，数组是 HashMap 的主体，链表则是主要为了解决哈希冲突⽽存在的；
>
>实现线程安全的方式：① 在JDK1.7的时候， ConcurrentHashMap（分段锁） 对整个桶
>数组进⾏了分割分段(Segment)，每⼀把锁只锁容器其中⼀部分数据，多线程访问容器⾥不同数据段的数据，就不会存在锁竞争，提⾼并发访问率。 到了 JDK1.8 的时候已经摒弃了Segment的概念，⽽是直接⽤ Node 数组+链表+红⿊树的数据结构来实现，并发控制使⽤ synchronized 和CAS 来操作。（JDK1.6以后 对 synchronized锁做了很多优化） 整个看起来就像是优化过且线程安全的 HashMap，虽然在JDK1.8中还能看到 Segment 的数据结构，但是已经简化了属性，只是为了兼容旧版本； ② Hashtable(同⼀把锁) :使⽤ synchronized 来保证线程安全，效率⾮常低下。当⼀个线程访问同步⽅法时，其他线程也访问同步⽅法，可能会进⼊阻塞或轮询状态，如使⽤ put 添加元素，另⼀个线程不能使⽤ put 添加元素，也不能使⽤ get，竞争会越来越激烈效率越低。  



- ConcurrentHashMap线程安全的具体实现以及底层实现原理

  > jdk1.7的具体实现
  >
  > > jdk1.7中维护一个Segment分段锁数组，每一个Segment中维护者Entry键值对，相当于把一个大的HashMap拆分成多个小的HashMap，以此种方式降低锁的冲突。⾸先将数据分为⼀段⼀段的存储，然后给每⼀段数据配⼀把锁，当⼀个线程占⽤锁访问其中⼀个段数据时，其他段的数据也能被其他线程访问。  Segment 实现了 eentrantLock,所以 Segment 是⼀种可重⼊锁，扮演锁的⻆⾊。 HashEntry ⽤于存储键值对数据 。⼀个 ConcurrentHashMap ⾥包含⼀个 Segment 数组。 Segment 的结构和HashMap类似，是⼀种数组和链表结构，⼀个 Segment 包含⼀个 HashEntry 数组，每个 HashEntry 是⼀个链表结构的元素，每个Segment 守护着⼀个HashEntry数组⾥的元素，当对 HashEntry 数组的数据进⾏修改时，必须⾸先获得对应的 Segment的锁。  
  >
  > jdk1.8的具体实现
  >
  > > ConcurrentHashMap取消了Segment分段锁，采⽤CAS和synchronized来保证并发安全。数据结构跟HashMap1.8的结构类似，数组+链表/红⿊⼆叉树。 Java 8在链表⻓度超过⼀定阈值（8）时将链表（寻址时间复杂度为O(N)）转换为红⿊树（寻址时间复杂度为O(log(N))）synchronized只锁定当前链表或红⿊⼆叉树的⾸节点，这样只要hash不冲突，就不会产⽣并发，效率⼜提升N倍。  
2020年6月24日 17:48:12 58/317