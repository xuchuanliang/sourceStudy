package com.ant.springboottest.spel.evaluation;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Test {
    private static final ExpressionParser expressionParser = new SpelExpressionParser();
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }
    public static void test1(){
        Expression expression = expressionParser.parseExpression("'Hello World'");
        System.out.println(expression.getValue());
    }

    public static void test2(){
        Expression expression = expressionParser.parseExpression("'hello world'.concat('!')");
        System.out.println(expression.getValue());
    }

    public static void test3(){
        Expression expression = expressionParser.parseExpression("'hello world'.bytes");
        byte[] bytes = (byte[]) expression.getValue();
        System.out.println(bytes.length);
    }

    public static void test4(){
        System.out.println(expressionParser.parseExpression("'hello world'.bytes.length").getValue());
    }

}
