package com.ant.com.capter02;

import java.io.Serializable;

public class Friend implements Cloneable, Serializable {
    private String name;
    private String gender;


    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    protected Friend clone() throws CloneNotSupportedException {
        return (Friend) super.clone();
    }
}
