package com.test1.ant.capter06;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显示锁的账户类
 */
public class AccountLock extends AbstractAccount {
    private String name;
    private double balance;
    private Lock lock;

    public AccountLock(String name,double balance){
        this.name = name;
        this.balance = balance;
        lock = new ReentrantLock();
    }

    @Override
    public void addBalance(double val) {
        lock.lock();
        try{
            balance += val;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void descBalance(double val) {
        lock.lock();
        try{
            balance -= val;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public double getBalance() {
        lock.lock();
        try{
            return balance;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "AccountUnSafe{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
