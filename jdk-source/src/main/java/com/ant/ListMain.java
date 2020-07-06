package com.ant;

import sun.misc.SharedSecrets;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListMain {
    public static void main(String[] args) {
    }
}

/**
 * 拷贝的ArrayList源码，用来分析
 *
 * @param <E>
 */
class MyArrayList<E> {
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 默认初始化数组容量：10
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 空数组（用于空实例），在实例化时如果传入的list长度为0，当调用new ArrayList(0)时，即传入的数组长度为0时，会默认 elementData=EMPTY_ELEMENTDATA
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 用于默认大小实例的共享空数组实例，在调用new ArrayList()时会默认elementData=DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * 之所以与EMPTY_ELEMENTDATA区分开，以知道在添加第一个元素时容量需要增加多少。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 保存ArrayList数据的数组
     */
    transient Object[] elementData;

    /**
     * ArrayList中元素的个数
     */
    private int size;

    /**
     * 带初始容量参数的构造函数（用户可以在创建ArrayList对象时自己指定集合的初始大小）
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            //如果传入的参数大于0，创建initialCapacity大小的数组
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            //如果传入的参数等于0，创建空数组
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            //其他情况，抛出异常
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    /**
     * 默认的无参构造函数
     * 初始化的时候默认容量是10，elementData是空数组，只有在添加第一个元素的时候才会真正的将elementData初始化成为一个容量为10的数组（相当于是延迟初始化）
     */
    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造一个包含指定集合的元素的列表，按照它们由集合的迭代器返回的顺序。
     *
     * @param c
     */
    public MyArrayList(Collection<? extends E> c) {
        //将传入的集合转成数组
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            //如果数组中元素长度不是0，判断数组类型是不是Object数组类型，如果不是则转成Object数组类型
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            //如果是一个空集合，则使用EMPTY_ELEMENTDATA
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * 裁剪ArrayList，主要是给程序员调用的，如果当前数组中元素个数小于整个数组的长度，则将数组缩减成与元素个数相同的数组，能够最小化该ArrayList占用的存储空间
     */
    public void trimToSize() {
        if (size < elementData.length) {
            elementData = (size == 0)
                    ? EMPTY_ELEMENTDATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 如有必要，增加此ArrayList实例的容量，以确保它至少能容纳元素的数量
     * ArrayList的扩容机制
     *
     * @param minCapacity
     */
    public void ensureCapacity(int minCapacity) {
        //默认最小的扩容数量，如果elementData不是DEFAULTCAPACITY_EMPTY_ELEMENTDATA，那么默认最小扩容数量是10
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                ? 0
                : DEFAULT_CAPACITY;
        //如果最小容量大于已有的最大容量
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    /**
     * 计算需要扩展的容量大小，如果elementData是空数组，则返回默认容量大小，即10，否则返回minCapacity
     *
     * @param elementData
     * @param minCapacity
     * @return
     */
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    /**
     * 判断当前数组element能否保存minCapacity数量的元素，如果不能则进行扩容
     *
     * @param minCapacity
     */
    private void ensureCapacityInternal(int minCapacity) {
        //为了方便解析，将该行代码解析成为两行
//        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
        //获取容量大小，即当elementData是默认的空数组时，则返回默认大小，即10
        //如果elementData默认不是空数组时，则返回minCapacity
        int capacity = calculateCapacity(elementData, minCapacity);
        //判断elementData能否满足所需最小的容量，如果不能满足则扩容
        ensureExplicitCapacity(capacity);
    }

    /**
     * 判断elementData能否满足所需最小的容量，如果不能满足则扩容
     *
     * @param minCapacity
     */
    private void ensureExplicitCapacity(int minCapacity) {
        // overflow-conscious code
        //如果所需最小容量(minCapacity)大于当前elementData数组的长度，则扩容
        if (minCapacity - elementData.length > 0)
            //扩容
            grow(minCapacity);
    }

    /**
     * 为了防止OOM异常，限制数组分配的最大长度
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 进行扩容操作
     *
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        //获取当前elementData数组的长度
        int oldCapacity = elementData.length;
        //默认新的数组长度是现在数组长度的1.5倍 (oldCapacity+0.5*oldCapacity=1.5oldCapacity)
        //位运算比直接newCapacity=1.5*oldCapacity要快很多
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            //如果扩容1.5倍仍然小于指定要扩容(minCapacity)的数量
            //则扩容至指定容量(minCapacity)的大小
            newCapacity = minCapacity;
        }

        //代码执行到这里，实际上最终要扩容的数量要么是现在elementData长度的1.5倍，要么是指定要扩容的长度(minCapacity)
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            //如果最终要扩容的数量大于内置的最大值，即MAX_ARRAY_SIZE=Integer.MAX_VALUE - 8
            //则默认将最终要扩大的容量改为Integer.MAX_VALUE
            newCapacity = hugeCapacity(minCapacity);
        //将elementData扩容至newCapacity大小的数组，并且将现在elementData中的元素拷贝到新的数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 大容量
     * 如果需要扩展的容量大于MAX_ARRAY_SIZE=Integer.MAX_VALUE-8，则返回MAX_ARRAY_SIZE，否则返回MAX_VALUE
     *
     * @param minCapacity
     * @return
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    /**
     * 新增一个元素
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        //判断当前数组容量是否够用，如果不够，则进行扩容
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }
}

/**
 * 拷贝LinkedList源码，用于分析
 * linkedList中的元素是使用一个Node对象存储的，实际上是一个双端链表
 * 继承与Deque，标识LinkedList可以当做双端队列使用
 *
 * @param <E>
 */
class MyLinkedList<E> {
    transient int size = 0;

    /**
     * 记录第一个节点的索引
     */
    transient MyLinkedList.Node<E> first;

    /**
     * 记录最后一个元素
     */
    transient MyLinkedList.Node<E> last;

    /**
     * 默认的构造方法
     */
    public MyLinkedList() {
    }

    /**
     * Links e as first element.
     */
    private void linkFirst(E e) {
        final MyLinkedList.Node<E> f = first;
        final MyLinkedList.Node<E> newNode = new MyLinkedList.Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    /**
     * 将e作为链表的最后一个元素
     */
    void linkLast(E e) {
        //记录当前链表的最后一个元素
        final MyLinkedList.Node<E> l = last;
        //创建一个新的Node节点，该节点的直接前驱是当前节点的最后一个元素，该节点的元素是当前参数中的元素，该节点的直接后继是空
        final MyLinkedList.Node<E> newNode = new MyLinkedList.Node<>(l, e, null);
        //更新当前链表的最后一个节点
        last = newNode;
        if (l == null)
            //如果第一个节点为空，则当前添加的是第一个元素，则更新first成员变量为当前节点
            first = newNode;
        else
            //如果第一个节点不为空，因为Node是一个双向链表，更新原节点到新节点的指针，即pre.next-->newNode
            l.next = newNode;
        size++;
    }

    private static class Node<E> {
        E item;
        MyLinkedList.Node<E> next;
        MyLinkedList.Node<E> prev;

        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}



