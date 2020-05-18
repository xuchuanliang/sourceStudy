package com.ant.entity;

public class Company {
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "Company{" +
                "staff=" + staff +
                '}';
    }
}
