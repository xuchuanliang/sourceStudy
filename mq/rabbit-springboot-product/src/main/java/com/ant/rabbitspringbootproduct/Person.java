package com.ant.rabbitspringbootproduct;

import java.util.UUID;

public class Person {
    private String id;
    private String name;
    private String gender;
    private String age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static Person randomPerson(){
        Person person = new Person();
        person.setAge("age"+ UUID.randomUUID().toString());
        person.setGender("gender"+UUID.randomUUID().toString());
        person.setId("id"+UUID.randomUUID().toString());
        person.setName("name"+UUID.randomUUID().toString());
        return person;
    }
}
