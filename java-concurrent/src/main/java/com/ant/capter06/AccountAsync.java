package com.ant.capter06;

/**
 * 同步锁实现的账户
 */
public class AccountAsync extends AbstractAccount{
    private String name;
    private double balance;

    public AccountAsync(String name,double balance){
        this.name = name;
        this.balance = balance;
    }

    @Override
    public synchronized void addBalance(double val) {
        balance += val;
    }

    @Override
    public synchronized void descBalance(double val) {
        balance -= val;
    }

    @Override
    public synchronized double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "AccountUnSafe{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
