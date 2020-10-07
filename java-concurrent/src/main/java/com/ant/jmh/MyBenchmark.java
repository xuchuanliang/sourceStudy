package com.ant.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用JMH 尝试进行多线程测试
 * 累计计算1亿次，4个线程，每个线程计算25_000_000次
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class MyBenchmark {
    static int[] ARRAY = new int[100_000_000];
    static {
        Arrays.fill(ARRAY,1);
    }

    @Benchmark
    public int sum() throws ExecutionException, InterruptedException {
        FutureTask<Integer> t1 = new FutureTask<Integer>(()->{
            int sum = 0;
           for(int i=0;i<25_000_000;i++){
               sum = sum + ARRAY[0+i];
           }
           return sum;
        });
        FutureTask<Integer> t2 = new FutureTask<Integer>(()->{
            int sum = 0;
            for(int i=0;i<25_000_000;i++){
                sum = sum + ARRAY[25_000_000+i];
            }
            return sum;
        });
        FutureTask<Integer> t3 = new FutureTask<Integer>(()->{
            int sum = 0;
            for(int i=0;i<25_000_000;i++){
                sum = sum + ARRAY[50_000_000+i];
            }
            return sum;
        });
        FutureTask<Integer> t4 = new FutureTask<Integer>(()->{
            int sum = 0;
            for(int i=0;i<25_000_000;i++){
                sum = sum + ARRAY[75_000_000+i];
            }
            return sum;
        });
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        return t1.get()+t2.get()+t3.get()+t4.get();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(MyBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }
}
