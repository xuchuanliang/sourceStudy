package com.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        Simple simple = new Simple();
        simple.booleanList.add(true);
        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext evaluationContext = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        expressionParser.parseExpression("booleanList[0]").setValue(evaluationContext,simple,"false");
        System.out.println(simple.booleanList);
    }
}
class Simple{
    public List<Boolean> booleanList = new ArrayList<>();
}
class Demo{
    public List<String> list;
}