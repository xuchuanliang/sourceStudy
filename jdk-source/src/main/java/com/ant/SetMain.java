package com.ant;

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;

public class SetMain {
    public static void main(String[] args) {
//        testTreeSet();
        System.out.println(testValid("(1+3)*6+(6*7"));
    }

    public static void testTreeSet(){
        TreeSet treeSet = new TreeSet();
        Random random = new Random();
        for(int i=0;i<100;i++){
            treeSet.add(random.nextInt(10000));
        }
        System.out.println(treeSet);
        System.out.println("\r\n");
        System.out.println("======================================");
        treeSet.forEach(t->{
            System.out.print(t);
            System.out.print(",");
        });
        System.out.println("\r\n");
        System.out.println("======================================");
        Iterator iterator = treeSet.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next());
            System.out.print(",");
        }
        System.out.println("\r\n");
        System.out.println("======================================");
        Iterator descendingIterator = treeSet.descendingIterator();
        while (descendingIterator.hasNext()){
            System.out.print(descendingIterator.next());
            System.out.print(",");
        }
    }

    public static void testStack(){
        Stack stack = new Stack();
    }

    /**
     * 判断左右括号是否完整
     * @param s
     * @return
     */
    public static boolean testValid(String s){
        if(null==s || s.length()<=0){
            return true;
        }
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for(char c:chars){
            if('(' == c){
                stack.push('(');
            }else if(')' == c){
                if(stack.empty()){
                    return false;
                }
                stack.pop();
            }
        }
        return stack.empty();
    }
}
