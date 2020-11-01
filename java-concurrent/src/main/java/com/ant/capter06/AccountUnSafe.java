package com.ant.capter06;

/**
 * 非线程安全的账户类
 *
 */
public class AccountUnSafe extends AbstractAccount{
    private String name;
    private double balance;

    public AccountUnSafe(String name,double balance){
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void addBalance(double val) {
        balance += val;
    }

    @Override
    public void descBalance(double val) {
        balance -= val;
    }

    @Override
    public double getBalance() {
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
