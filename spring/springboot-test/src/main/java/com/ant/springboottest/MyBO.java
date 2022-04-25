package com.ant.springboottest;

public class MyBO {
    private String id;

    public MyBO(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyBO{" +
                "id='" + id + '\'' +
                '}';
    }
}
