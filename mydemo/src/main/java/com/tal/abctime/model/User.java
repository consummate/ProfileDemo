package com.tal.abctime.model;

/**
 * Created by irene on 2018/4/16.
 */


public class User {

    private long id;
    private String name;
    private String gender;
    private String age;
    public User() {
    }
    @Override
    public String toString() {
        return "user:name="+name+", age="+age+", gender="+gender;
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }

}
