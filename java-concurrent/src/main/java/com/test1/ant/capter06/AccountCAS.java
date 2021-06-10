package com.test1.ant.capter06;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用CAS实现的账户类
 */
@Slf4j
public class AccountCAS extends AbstractAccount {
    private String name;
    private AtomicDouble balance;

    public AccountCAS(String name, double balance) {
        this.name = name;
        this.balance = new AtomicDouble(balance);
    }

    @Override
    public void addBalance(double val) {
        balance.addAndGet(balance.addAndGet(val));
    }

    @Override
    public void descBalance(double val) {
        double old;
        double newVal;
        do {
            old = balance.get();
            newVal = old - val;
        } while (!balance.compareAndSet(old, newVal));
    }

    @Override
    public double getBalance() {
        return balance.get();
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
